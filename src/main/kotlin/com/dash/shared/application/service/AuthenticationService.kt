package com.dash.shared.application.service

import com.dash.shared.application.port.inbound.LoginUseCase
import com.dash.shared.application.port.outbound.AccessTokenPort
import com.dash.shared.application.port.outbound.EventPublisherPort
import com.dash.shared.application.port.outbound.UserAuthenticationPort
import com.dash.shared.domain.event.DashEvent
import com.dash.shared.domain.event.EventMessages
import com.dash.shared.domain.model.NotificationType
import com.dash.shared.domain.model.UserSession
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val userAuthenticationPort: UserAuthenticationPort,
    private val accessTokenPort: AccessTokenPort,
    private val eventPublisherPort: EventPublisherPort
) : LoginUseCase {
    override fun login(
        username: String,
        password: String
    ): UserSession =
        userAuthenticationPort
            .authenticate(username, password)
            .also { eventPublisherPort.publish(DashEvent(EventMessages.USER_LOGGED_IN, NotificationType.WARN)) }
            .let { user -> UserSession(accessTokenPort.generateToken(user.username), user) }
}
