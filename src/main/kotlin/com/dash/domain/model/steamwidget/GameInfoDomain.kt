package com.dash.domain.model.steamwidget

data class GameInfoDomain(
    val appid: String,
    val name: String,
    val imgIconUrl: String,
    val imgLogoUrl: String,
    val hasCommunityVisibleStats: Boolean,
    val playtime2weeks: Int,
    val playtimeForever: Int,
    val playtimeWindowsForever: Int,
    val playtimeMacForever: Int,
    val playtimeLinuxForever: Int
)
