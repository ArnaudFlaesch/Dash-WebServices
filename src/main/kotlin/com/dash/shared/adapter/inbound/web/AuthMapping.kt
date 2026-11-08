package com.dash.shared.adapter.inbound.web

import com.dash.shared.adapter.inbound.web.response.JwtResponse
import com.dash.shared.domain.model.UserSession

fun UserSession.toJwtResponse(): JwtResponse =
    JwtResponse(
        accessToken = accessToken,
        id = user.id,
        username = user.username,
        email = user.email,
        roles = user.roles
    )
