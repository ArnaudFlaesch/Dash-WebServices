package com.dash.dashboard.adapter.outbound.rest.steam

import com.dash.dashboard.domain.model.steam.Achievement
import com.dash.dashboard.domain.model.steam.AchievementData
import com.dash.dashboard.domain.model.steam.GameInfo
import com.dash.dashboard.domain.model.steam.PlayerData
import com.dash.dashboard.domain.model.steam.PlayerStats
import com.fasterxml.jackson.annotation.JsonProperty

fun PlayersDataApiResponse.toDomain(): List<PlayerData> = this.response.players.map(PlayerDataApi::toDomain)

fun PlayerDataApi.toDomain(): PlayerData =
    PlayerData(
        personaname = this.personaname,
        avatar = this.avatar,
        profileurl = this.profileurl
    )

fun GameInfoApi.toDomain(): GameInfo =
    GameInfo(
        appid = this.appid,
        name = this.name,
        imgIconUrl = this.imgIconUrl,
        imgLogoUrl = this.imgLogoUrl,
        hasCommunityVisibleStats = this.hasCommunityVisibleStats,
        playtime2weeks = this.playtime2weeks,
        playtimeForever = this.playtimeForever,
        playtimeWindowsForever = this.playtimeWindowsForever,
        playtimeMacForever = this.playtimeMacForever,
        playtimeLinuxForever = this.playtimeLinuxForever
    )

fun AchievementDataResponse.toDomain(): AchievementData =
    AchievementData(
        playerstats =
            PlayerStats(
                achievements = this.playerstats.achievements.map(AchievementResponse::toDomain)
            )
    )

fun AchievementResponse.toDomain(): Achievement =
    Achievement(
        apiname = this.apiname,
        achieved = this.achieved,
        unlocktime = this.unlocktime
    )
