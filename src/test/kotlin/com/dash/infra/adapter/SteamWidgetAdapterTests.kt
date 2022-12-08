package com.dash.infra.adapter

import com.dash.domain.model.steamwidget.GameDataDomain
import com.dash.infra.apimodel.steam.GameDataApi
import com.dash.infra.apimodel.steam.GameInfoApi
import com.dash.infra.apimodel.steam.GameInfoResponse
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
    fun should_paginate_results() {
        val steamUserId = "1337"
        val gamesListMock = createGameListFromApi(0, 50)
        val gameInfoResponseMock = GameInfoResponse(GameDataApi(50, gamesListMock))
        given(steamApiClient.getOwnedGames(steamUserId)).willReturn(gameInfoResponseMock)

        val actualFirstPage = steamWidgetAdapter.getOwnedGames(steamUserId, "", 0)
        val expectedFirstPage = GameDataDomain(
            gameCount = 50,
            games = gamesListMock.subList(0, 25).map(GameInfoApi::toDomain)
        )
        assertEquals(expectedFirstPage, actualFirstPage)

        val actualSecondPage = steamWidgetAdapter.getOwnedGames(steamUserId, "", 1)
        val expectedSecondPage = GameDataDomain(
            gameCount = 50,
            games = gamesListMock.subList(25, 50).map(GameInfoApi::toDomain)
        )
        assertEquals(expectedSecondPage, actualSecondPage)
    }


    private fun createGameListFromApi(startIndex: Int, size: Int): List<GameInfoApi> {
        return (startIndex until size).map { index ->
            GameInfoApi(appid = index.toString(), name = "Call of Duty $index")
        }.sortedBy(GameInfoApi::name)
    }
}
