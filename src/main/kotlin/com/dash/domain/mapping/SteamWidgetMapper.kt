package com.dash.domain.mapping

import com.dash.domain.model.steamWidget.*
import com.dash.infra.apimodel.steam.*
import org.springframework.stereotype.Component

@Component
class SteamWidgetMapper {

    fun playersDataResponseToDomain(playerDataResponse: PlayersDataApiResponse): List<PlayerDataDomain> =
        playerDataResponse.response.players.map(this::playerDataResponseToDomain)

    private fun playerDataResponseToDomain(playerData: PlayerDataApi): PlayerDataDomain =
        PlayerDataDomain(personaname = playerData.personaname, avatar = playerData.avatar, profileurl = playerData.avatar)

    fun gameDataResponseToDomain(gameInfoResponse: GameInfoResponse): GameDataDomain =
        GameDataDomain(gameCount = gameInfoResponse.response.gameCount, games = gameInfoResponse.response.games.map(this::gameDataApiToDomain))

    private fun gameDataApiToDomain(game: GameInfoApi): GameInfoDomain =
        GameInfoDomain(
            appid = game.appid,
            name = game.name,
            imgIconUrl = game.imgIconUrl,
            imgLogoUrl = game.imgLogoUrl,
            hasCommunityVisibleStats = game.hasCommunityVisibleStats,
            playtime2weeks = game.playtime2weeks,
            playtimeForever = game.playtimeForever,
            playtimeWindowsForever = game.playtimeWindowsForever,
            playtimeMacForever = game.playtimeMacForever,
            playtimeLinuxForever = game.playtimeLinuxForever
        )

    fun achievementsApiResponseToDomain(achievementDataResponse: AchievementDataResponse): AchievementDataDomain =
        AchievementDataDomain(
            playerstats = PlayerStatsDomain(
                achievements = achievementDataResponse.playerstats.achievements.map(this::achievementApiResponseToDomain)
            )
        )

    private fun achievementApiResponseToDomain(achievementDataResponse: AchievementResponse): AchievementDomain =
        AchievementDomain(
            apiname = achievementDataResponse.apiname,
            achieved = achievementDataResponse.achieved,
            unlocktime = achievementDataResponse.unlocktime
        )
}
