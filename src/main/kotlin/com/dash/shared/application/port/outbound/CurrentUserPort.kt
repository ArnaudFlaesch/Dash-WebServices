package com.dash.shared.application.port.outbound

/** Gives the user on whose behalf the current request is processed. */
interface CurrentUserPort {
    fun currentUserId(): Int

    fun currentUsername(): String
}
