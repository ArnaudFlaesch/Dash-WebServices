package com.dash.domain.model.steamwidget

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

data class GameInfoResponse(
    val response: GameData = GameData()
)

data class GameData(
    @JsonProperty("game_count")
    val gameCount: Int = 0,
    @JsonProperty("games")
    val games: List<GameInfo> = emptyList()
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class GameInfo(
    @JsonProperty("appid")
    val appid: String = "",
    @JsonProperty("name")
    val name: String = "",
    @JsonProperty("img_icon_url")
    val imgIconUrl: String? = "",
    @JsonProperty("img_logo_url")
    val imgLogoUrl: String? = "",
    @JsonProperty("has_community_visible_stats")
    val hasCommunityVisibleStats: Boolean? = false,
    @JsonProperty("playtime_2weeks")
    val playtime2weeks: Int = 0,
    @JsonProperty("playtime_forever")
    val playtimeForever: Int? = 0,
    @JsonProperty("playtime_windows_forever")
    val playtimeWindowsForever: Int? = 0,
    @JsonProperty("playtime_mac_forever")
    val playtimeMacForever: Int? = 0,
    @JsonProperty("playtime_linux_forever")
    val playtimeLinuxForever: Int? = 0
)
