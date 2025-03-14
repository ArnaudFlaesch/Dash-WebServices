package com.dash.app.controller

import com.common.utils.IntegrationTestsUtils
import com.common.utils.IntegrationTestsUtils.createAuthenticationHeader
import com.dash.app.controller.requests.notifications.MarkNotificationsAsReadPayload
import com.dash.app.controller.response.Page
import com.dash.domain.model.notification.NotificationDomain
import com.dash.domain.model.notification.NotificationType
import com.dash.infra.repository.NotificationRepository
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
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
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
                get("$notificationsEndpoint/")
            } Then {
                statusCode(HttpStatus.OK.value())
            } Extract {
                `as`(object : TypeRef<Page<NotificationDomain>>() {})
            }

        // This one notification is the one created when the user logs in before starting the test
        assertEquals(1, actualNotifications.content.size)
        assertEquals(actualNotifications.content[0].message, "admintest : Connexion utilisateur.")
        assertEquals(actualNotifications.content[0].notificationType, NotificationType.WARN)
        assertEquals(actualNotifications.content[0].isRead, false)

        val markNotificationsAsReadPayload =
            MarkNotificationsAsReadPayload(listOf(actualNotifications.content[0].id))
        val updatedNotifications =
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
                    .contentType(ContentType.JSON)
                    .body(markNotificationsAsReadPayload)
            } When {
                put("$notificationsEndpoint/markNotificationAsRead")
            } Then {
                statusCode(HttpStatus.OK.value())
            } Extract {
                `as`(object : TypeRef<List<NotificationDomain>>() {})
            }

        assertEquals(1, updatedNotifications.size)
        assertEquals("admintest : Connexion utilisateur.", updatedNotifications[0].message)
        assertEquals(NotificationType.WARN, updatedNotifications[0].notificationType)
        assertEquals(true, updatedNotifications[0].isRead)
    }
}
