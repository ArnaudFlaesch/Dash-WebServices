package com.dash.infra.adapter

import com.dash.app.controller.response.Page
import com.dash.domain.model.steamwidget.PlayerDataDomain
import com.dash.infra.apimodel.steam.*
import com.dash.infra.rest.SteamApiClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given

@ExtendWith(MockitoExtension::class)
class SteamWidgetAdapterTests {
    @Mock
    private lateinit var steamApiClient: SteamApiClient

    @InjectMocks
    private lateinit var steamWidgetAdapter: SteamWidgetAdapter

    @Test
    fun shouldGetPlayerData() {
        val steamUserId = "1337"
        val playerDataResponse =
            PlayerDataApi().copy(personaname = "Nono", profileurl = "steam/nono", avatar = "profile.png")
        given(steamApiClient.getPlayerData(steamUserId)).willReturn(
            PlayersDataApiResponse(
                response =
                    PlayersDataListResponse(
                        players =
                            listOf(
                                playerDataResponse
                            )
                    )
            )
        )
        val actual = steamWidgetAdapter.getPlayerData(steamUserId)
        val expected =
            listOf(
                PlayerDataDomain(
                    personaname = "Nono",
                    profileurl = "steam/nono",
                    avatar = "profile.png"
                )
            )
        assertEquals(expected, actual)
    }

    @Test
    fun shouldPaginateResults() {
        val steamUserId = "1337"
        val gamesListMock = createGameListFromApi(0, 50)
        val gameInfoResponseMock = GameInfoResponse().copy(GameDataApi().copy(50, gamesListMock))
        given(steamApiClient.getOwnedGames(steamUserId)).willReturn(gameInfoResponseMock)

        val actualFirstPage = steamWidgetAdapter.getOwnedGames(steamUserId, "", 0)
        val expectedFirstPage =
            Page(
                totalElements = 50,
                last = false,
                totalPages = 2,
                size = 25,
                number = 0,
                content = gamesListMock.subList(0, 25).map(GameInfoApi::toDomain)
            )
        assertEquals(expectedFirstPage, actualFirstPage)

        val actualSecondPage = steamWidgetAdapter.getOwnedGames(steamUserId, "", 1)

        val expectedSecondPage =
            Page(
                totalElements = 50,
                last = true,
                totalPages = 2,
                size = 25,
                number = 1,
                content = gamesListMock.subList(25, 50).map(GameInfoApi::toDomain)
            )
        assertEquals(expectedSecondPage, actualSecondPage)
    }

    private fun createGameListFromApi(
        startIndex: Int,
        size: Int
    ): List<GameInfoApi> =
        (startIndex until size)
            .map { index -> GameInfoApi().copy(appid = index.toString(), name = "Call of Duty $index") }
            .sortedBy(GameInfoApi::name)
}
