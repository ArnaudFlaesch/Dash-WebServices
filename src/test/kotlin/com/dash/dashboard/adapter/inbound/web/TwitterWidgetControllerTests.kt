package com.dash.dashboard.adapter.inbound.web

import com.dash.dashboard.adapter.inbound.web.request.twitter.AddUserToFollowRequest
import com.dash.dashboard.domain.model.twitter.FollowedUser
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
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TwitterWidgetControllerTests {
    @LocalServerPort
    private val port: Int = 0

    private lateinit var jwtToken: String

    private val followedUsersEndpoint = "/twitter/followed-users"

    private val userId = 1

    @BeforeAll
    fun setup() {
        RestAssured.defaultParser = Parser.JSON
        jwtToken = IntegrationTestsUtils.authenticateAdminRole(port).accessToken
    }

    @Test
    fun getFollowedUsers() {
        val followedUsersEmptyList =
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
                    .contentType(ContentType.JSON)
            } When {
                get(followedUsersEndpoint)
            } Then {
                statusCode(HttpStatus.OK.value())
            } Extract {
                `as`(object : TypeRef<Page<FollowedUser>>() {})
            }

        assertEquals(0, followedUsersEmptyList.content.size)

        val userHandleToAdd = "Nono"
        val addUserToFollowRequest = AddUserToFollowRequest(userHandleToAdd)

        val addedUserToFollow =
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
                    .contentType(ContentType.JSON)
            } When {
                body(addUserToFollowRequest)
                    .post(followedUsersEndpoint)
            } Then {
                statusCode(HttpStatus.CREATED.value())
            } Extract {
                `as`(FollowedUser::class.java)
            }

        assertNotNull(addedUserToFollow.id)
        assertNotNull(addedUserToFollow.userId)
        assertEquals(userHandleToAdd, addedUserToFollow.userHandle)

        val followedUsers =
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
                    .contentType(ContentType.JSON)
            } When {
                get(followedUsersEndpoint)
            } Then {
                statusCode(HttpStatus.OK.value())
            } Extract {
                `as`(object : TypeRef<Page<FollowedUser>>() {})
            }

        assertEquals(1, followedUsers.content.size)
        assertEquals(userHandleToAdd, followedUsers.content[0].userHandle)

        Given {
            port(port)
                .header(createAuthenticationHeader(jwtToken))
                .contentType(ContentType.JSON)
        } When {
            delete("$followedUsersEndpoint/${addedUserToFollow.id}")
        } Then {
            log()
                .all()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .log()
                .all()
        }

        val followedUsersAfterDeletion =
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
                    .contentType(ContentType.JSON)
            } When {
                get(followedUsersEndpoint)
            } Then {
                statusCode(HttpStatus.OK.value())
            } Extract {
                `as`(object : TypeRef<Page<FollowedUser>>() {})
            }

        assertEquals(0, followedUsersAfterDeletion.content.size)
    }
}
