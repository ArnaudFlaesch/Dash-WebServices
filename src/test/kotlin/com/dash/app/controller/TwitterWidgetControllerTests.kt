package com.dash.app.controller

import com.common.utils.AbstractIT
import com.common.utils.IntegrationTestsUtils
import com.common.utils.IntegrationTestsUtils.createAuthenticationHeader
import com.dash.app.controller.requests.twitterWidget.AddUserToFollowPayload
import com.dash.app.controller.response.Page
import com.dash.domain.model.twitterwidget.FollowedUser
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.common.mapper.TypeRef
import io.restassured.http.ContentType
import io.restassured.parsing.Parser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TwitterWidgetControllerTests : AbstractIT() {
    @LocalServerPort
    private val port: Int = 0

    private lateinit var jwtToken: String

    private val twitterWidgetEndpoint = "/twitterWidget"

    private val userId = 1

    @BeforeAll
    fun setup() {
        RestAssured.defaultParser = Parser.JSON
        jwtToken = IntegrationTestsUtils.authenticateAdminRole(port).accessToken
    }

    @Test
    fun getFollowedUsers() {
        val followedUsersEmptyList =
            given()
                .port(port)
                .header(createAuthenticationHeader(jwtToken))
                .contentType(ContentType.JSON)
                .`when`()
                .get("$twitterWidgetEndpoint/followed")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.OK.value())
                .log()
                .all()
                .extract()
                .`as`(object : TypeRef<Page<FollowedUser>>() {})

        assertEquals(0, followedUsersEmptyList.content.size)

        val userHandleToAdd = "Nono"
        val addUserToFollowPayload = AddUserToFollowPayload(userHandleToAdd)

        val addedUserToFollow =
            given()
                .port(port)
                .header(createAuthenticationHeader(jwtToken))
                .contentType(ContentType.JSON)
                .`when`()
                .body(addUserToFollowPayload)
                .post("$twitterWidgetEndpoint/addFollowedUser")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.OK.value())
                .log()
                .all()
                .extract()
                .`as`(FollowedUser::class.java)

        assertNotNull(addedUserToFollow.id)
        assertNotNull(addedUserToFollow.userId)
        assertEquals(userHandleToAdd, addedUserToFollow.userHandle)

        val followedUsers =
            given()
                .port(port)
                .header(createAuthenticationHeader(jwtToken))
                .contentType(ContentType.JSON)
                .`when`()
                .get("$twitterWidgetEndpoint/followed")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.OK.value())
                .log()
                .all()
                .extract()
                .`as`(object : TypeRef<Page<FollowedUser>>() {})

        assertEquals(1, followedUsers.content.size)
        assertEquals(userHandleToAdd, followedUsers.content[0].userHandle)

        given()
            .port(port)
            .header(createAuthenticationHeader(jwtToken))
            .contentType(ContentType.JSON)
            .`when`()
            .param("followedUserId", addedUserToFollow.id)
            .delete("$twitterWidgetEndpoint/deleteFollowedUser")
            .then()
            .log()
            .all()
            .statusCode(HttpStatus.OK.value())
            .log()
            .all()

        val followedUsersAfterDeletion =
            given()
                .port(port)
                .header(createAuthenticationHeader(jwtToken))
                .contentType(ContentType.JSON)
                .`when`()
                .get("$twitterWidgetEndpoint/followed")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.OK.value())
                .log()
                .all()
                .extract()
                .`as`(object : TypeRef<Page<FollowedUser>>() {})

        assertEquals(0, followedUsersAfterDeletion.content.size)
    }
}
