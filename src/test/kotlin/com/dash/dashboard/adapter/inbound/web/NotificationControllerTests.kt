package com.dash.dashboard.adapter.inbound.web

import com.dash.dashboard.adapter.inbound.web.request.notification.MarkNotificationsAsReadRequest
import com.dash.dashboard.adapter.outbound.persistence.notification.NotificationRepository
import com.dash.dashboard.domain.model.notification.Notification
import com.dash.shared.domain.model.NotificationType
import com.dash.shared.domain.model.Page
import com.dash.utils.IntegrationTestsUtils
import com.dash.utils.IntegrationTestsUtils.createAuthenticationHeader
import io.restassured.RestAssured
import io.restassured.common.mapper.TypeRef
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.parsing.Parser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NotificationControllerTests {
    @LocalServerPort
    private val port: Int = 0

    private lateinit var jwtToken: String

    private val notificationsEndpoint = "/notifications"

    @Autowired
    private lateinit var notificationRepository: NotificationRepository

    @BeforeAll
    fun setup() {
        notificationRepository.deleteAll()
        RestAssured.defaultParser = Parser.JSON
        jwtToken = IntegrationTestsUtils.authenticateAdminRole(port).accessToken
    }

    @Test
    fun shouldGetNotificationsAndMarkAsRead() {
        val actualNotifications =
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
                    .contentType(ContentType.JSON)
            } When {
                get(notificationsEndpoint)
            } Then {
                statusCode(HttpStatus.OK.value())
            } Extract {
                `as`(object : TypeRef<Page<Notification>>() {})
            }

        // This one notification is the one created when the user logs in before starting the test
        assertEquals(1, actualNotifications.content.size)
        assertEquals(actualNotifications.content[0].message, "admintest : Connexion utilisateur.")
        assertEquals(actualNotifications.content[0].notificationType, NotificationType.WARN)
        assertEquals(actualNotifications.content[0].isRead, false)

        val markNotificationsAsReadRequest =
            MarkNotificationsAsReadRequest(listOf(actualNotifications.content[0].id))
        val updatedNotifications =
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
                    .contentType(ContentType.JSON)
                    .body(markNotificationsAsReadRequest)
            } When {
                put("$notificationsEndpoint/read")
            } Then {
                statusCode(HttpStatus.OK.value())
            } Extract {
                `as`(object : TypeRef<List<Notification>>() {})
            }

        assertEquals(1, updatedNotifications.size)
        assertEquals("admintest : Connexion utilisateur.", updatedNotifications[0].message)
        assertEquals(NotificationType.WARN, updatedNotifications[0].notificationType)
        assertEquals(true, updatedNotifications[0].isRead)
    }
}
