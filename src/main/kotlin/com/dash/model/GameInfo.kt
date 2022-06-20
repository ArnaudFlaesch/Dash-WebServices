package com.dash.model

data class GameInfo(
    val appid: String = "",
    val name: String = "",
    val img_icon_url: String = "",
    val img_logo_url: String = "",
    val has_community_visible_stats : Boolean = false,
    val playtime_2weeks: Int = 0,
    val playtime_forever: Int = 0,
    val playtime_windows_forever: Int = 0,
    val playtime_mac_forever: Int = 0,
    val playtime_linux_forever: Int = 0
)
