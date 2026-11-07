package com.dash.dashboard.adapter.outbound.rest.steam

import com.dash.dashboard.domain.model.steam.PlayerData
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given

@ExtendWith(MockitoExtension::class)
class SteamAdapterTests {
    @Mock
    private lateinit var steamApiClient: SteamApiClient

    @InjectMocks
    private lateinit var steamAdapter: SteamAdapter

    @Test
    fun shouldGetPlayerData() {
        val steamUserId = "1337"
        val playerDataResponse =
            PlayerDataApi().copy(personaname = "Nono", profileurl = "steam/nono", avatar = "profile.png")
        given(steamApiClient.getPlayerData(steamUserId)).willReturn(
            PlayersDataApiResponse(response = PlayersDataListResponse(players = listOf(playerDataResponse)))
        )

        val actual = steamAdapter.getPlayerData(steamUserId)

        assertEquals(
            listOf(PlayerData(personaname = "Nono", profileurl = "steam/nono", avatar = "profile.png")),
            actual
        )
    }

    @Test
    fun shouldMapOwnedGames() {
        val steamUserId = "1337"
        val games =
            listOf(
                GameInfoApi().copy(appid = "2", name = "Half-Life 2", playtimeForever = 120),
                GameInfoApi().copy(appid = "1", name = "Portal")
            )
        given(steamApiClient.getOwnedGames(steamUserId)).willReturn(GameInfoResponse(GameDataApi(2, games)))

        val actual = steamAdapter.getOwnedGames(steamUserId)

        assertEquals(games.map(GameInfoApi::toDomain), actual)
        assertEquals(120, actual[0].playtimeForever)
    }
}
