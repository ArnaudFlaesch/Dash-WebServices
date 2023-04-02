package com.dash.app.controller

import com.common.utils.AbstractIT
import com.common.utils.IntegrationTestsUtils
import com.common.utils.IntegrationTestsUtils.createAuthenticationHeader
import com.dash.app.controller.requests.notifications.MarkNotificationsAsReadPayload
import com.dash.app.controller.response.Page
import com.dash.domain.model.notification.NotificationDomain
import com.dash.domain.model.notification.NotificationType
import com.dash.domain.model.workoutwidget.*
import com.dash.infra.repository.NotificationRepository
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.common.mapper.TypeRef
import io.restassured.http.ContentType
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
class NotificationControllerTests : AbstractIT() {

    @LocalServerPort
    private val port: Int = 0

    private lateinit var jwtToken: String

    private val notificationsEndpoint = "/notifications"
    private val userId = 1

    @Autowired
    private lateinit var notificationRepository: NotificationRepository

    @BeforeAll
    fun setup() {
        notificationRepository.deleteAll()
        RestAssured.defaultParser = Parser.JSON
        jwtToken = IntegrationTestsUtils.authenticateAdminRole(port).accessToken
    }

    @Test
    fun should_get_notifications_and_marked_as_read() {
        val actualNotifications = given()
            .port(port)
            .header(createAuthenticationHeader(jwtToken))
            .contentType(ContentType.JSON)
            .`when`()
            .get("$notificationsEndpoint/")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .log().all()
            .extract().`as`(object : TypeRef<Page<NotificationDomain>>() {})

        // This one notification is the one created when the user logs in before starting the test
        assertEquals(1, actualNotifications.content.size)
        assertEquals(actualNotifications.content[0].message, "admintest : Connexion utilisateur.")
        assertEquals(actualNotifications.content[0].notificationType, NotificationType.WARN)
        assertEquals(actualNotifications.content[0].isRead, false)

        val markNotificationsAsReadPayload = MarkNotificationsAsReadPayload(listOf(actualNotifications.content[0].id))
        val updatedNotifications = given()
            .port(port)
            .header(createAuthenticationHeader(jwtToken))
            .contentType(ContentType.JSON)
            .body(markNotificationsAsReadPayload)
            .`when`()
            .put("$notificationsEndpoint/markNotificationAsRead")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .log().all()
            .extract().`as`(object : TypeRef<List<NotificationDomain>>() {})

        assertEquals(1, updatedNotifications.size)
        assertEquals("admintest : Connexion utilisateur.", updatedNotifications[0].message)
        assertEquals(NotificationType.WARN, updatedNotifications[0].notificationType)
        assertEquals(true, updatedNotifications[0].isRead)
    }
}
