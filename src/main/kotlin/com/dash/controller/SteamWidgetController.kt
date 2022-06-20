package com.dash.controller

import com.dash.model.GameInfoResponse
import com.dash.service.SteamWidgetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/steamWidget")
class SteamWidgetController {

    @Autowired
    private lateinit var steamWidgetService: SteamWidgetService

    @GetMapping("/playerData")
    fun getPlayerData(): String? {
        return steamWidgetService.getPlayerData()
    }

    @GetMapping("/ownedGames")
    fun getOwnedGames(
        @RequestParam(value = "search", defaultValue = "") search: String,
        @RequestParam(value = "pageNumber", defaultValue = "0") pageNumber: Int
    ): GameInfoResponse {
        return steamWidgetService.getOwnedGames(search, pageNumber)
    }

    @GetMapping("/achievementList")
    fun getAchievementList(@RequestParam(value = "appId") appId: String): String? {
        return steamWidgetService.getAchievementList(appId)
    }
}
