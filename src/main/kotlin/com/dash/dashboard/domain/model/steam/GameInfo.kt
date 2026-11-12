package com.dash.dashboard.domain.model.steam

data class GameInfo(
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

/** The games whose name contains [search], ignoring case, sorted by name. */
fun List<GameInfo>.searchByName(search: String): List<GameInfo> =
    filter { gameInfo -> gameInfo.name.contains(search, ignoreCase = true) }.sortedBy(GameInfo::name)
