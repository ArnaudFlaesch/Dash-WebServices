package com.dash.dashboard.application.service

import com.dash.dashboard.application.port.outbound.SteamPort
import com.dash.dashboard.domain.model.steam.GameInfo
import com.dash.shared.domain.model.Page
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.given
import org.mockito.kotlin.mock

class SteamWidgetServiceTest {
    private val steamPort: SteamPort = mock()
    private val steamWidgetService = SteamWidgetService(steamPort)

    private val steamUserId = "1337"

    @Test
    fun shouldPaginateResults() {
        val games = createGames(50)
        given(steamPort.getOwnedGames(steamUserId)).willReturn(games.shuffled())

        val actualFirstPage = steamWidgetService.getOwnedGames(steamUserId, "", 0)
        val expectedFirstPage =
            Page(
                totalElements = 50,
                last = false,
                totalPages = 2,
                size = 25,
                number = 0,
                content = games.subList(0, 25)
            )
        assertEquals(expectedFirstPage, actualFirstPage)

        val actualSecondPage = steamWidgetService.getOwnedGames(steamUserId, "", 1)
        val expectedSecondPage =
            Page(
                totalElements = 50,
                last = true,
                totalPages = 2,
                size = 25,
                number = 1,
                content = games.subList(25, 50)
            )
        assertEquals(expectedSecondPage, actualSecondPage)
    }

    @Test
    fun shouldCountPartiallyFilledLastPage() {
        val games = createGames(27)
        given(steamPort.getOwnedGames(steamUserId)).willReturn(games)

        val firstPage = steamWidgetService.getOwnedGames(steamUserId, "", 0)
        assertEquals(2, firstPage.totalPages)
        assertEquals(25, firstPage.size)
        assertEquals(false, firstPage.last)

        val lastPage = steamWidgetService.getOwnedGames(steamUserId, "", 1)
        assertEquals(games.subList(25, 27), lastPage.content)
        assertEquals(true, lastPage.last)
    }

    @Test
    fun shouldFilterGamesByNameIgnoringCase() {
        val games = listOf(game("1", "Portal 2"), game("2", "Half-Life"), game("3", "portal"))
        given(steamPort.getOwnedGames(steamUserId)).willReturn(games)

        val page = steamWidgetService.getOwnedGames(steamUserId, "PORTAL", 0)

        assertEquals(listOf("Portal 2", "portal"), page.content.map(GameInfo::name))
        assertEquals(2, page.totalElements)
    }

    /** Games sorted by name, as the service returns them. */
    private fun createGames(size: Int): List<GameInfo> =
        (0 until size).map { index -> game(index.toString(), "Call of Duty $index") }.sortedBy(GameInfo::name)

    private fun game(
        appId: String,
        name: String
    ): GameInfo = GameInfo(appId, name, "", "", false, 0, 0, 0, 0, 0)
}
