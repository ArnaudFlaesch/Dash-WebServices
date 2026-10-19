package com.dash.infra.apimodel.steam

import com.dash.domain.model.steamwidget.GameInfoDomain
import com.fasterxml.jackson.annotation.JsonProperty
import tools.jackson.databind.PropertyNamingStrategies
import tools.jackson.databind.annotation.JsonNaming

data class GameInfoResponse(
    val response: GameDataApi = GameDataApi()
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class GameDataApi(
    val gameCount: Int = 0,
    val games: List<GameInfoApi> = emptyList()
)

data class GameInfoApi(
    val appid: String = "",
    val name: String = "",
    @JsonProperty("img_icon_url")
    val imgIconUrl: String = "",
    @JsonProperty("img_logo_url")
    val imgLogoUrl: String = "",
    @JsonProperty("has_community_visible_stats")
    val hasCommunityVisibleStats: Boolean = false,
    @JsonProperty("playtime_2weeks")
    val playtime2weeks: Int = 0,
    @JsonProperty("playtime_forever")
    val playtimeForever: Int = 0,
    @JsonProperty("playtime_windows_forever")
    val playtimeWindowsForever: Int = 0,
    @JsonProperty("playtime_mac_forever")
    val playtimeMacForever: Int = 0,
    @JsonProperty("playtime_linux_forever")
    val playtimeLinuxForever: Int = 0
) {
    fun toDomain(): GameInfoDomain =
        GameInfoDomain(
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
}
