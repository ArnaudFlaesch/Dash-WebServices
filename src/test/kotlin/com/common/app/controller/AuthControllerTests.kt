package com.common.app.controller

import com.common.app.controller.requests.LoginRequest
import com.common.app.security.response.JwtResponse
import com.common.domain.model.RoleEnum
import com.common.utils.SqlData
import io.restassured.RestAssured.defaultParser
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.parsing.Parser
import org.hamcrest.Matchers
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    useMainMethod = SpringBootTest.UseMainMethod.ALWAYS
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SqlData
class AuthControllerTests {
    @LocalServerPort
    private val port: Int = 0

    companion object {
        const val AUTH_ENDPOINT = "/auth/login"
    }

    @BeforeAll
    fun testUp() {
        defaultParser = Parser.JSON
    }

    @Test
    fun testGetAdmin() {
        val jwtResponse =
            Given {
                port(port)
                    .contentType(ContentType.JSON)
            } When {
                body(LoginRequest("admintest", "adminpassword"))
                    .post(AUTH_ENDPOINT)
            } Then {
                statusCode(200)
            } Extract {
                `as`(JwtResponse::class.java)
            }
        assertEquals(RoleEnum.ROLE_ADMIN.roleName, jwtResponse.roles[0])
    }

    @Test
    fun testGetUser() {
        val jwtResponse =
            Given {
                port(port)
                    .contentType(ContentType.JSON)
            } When {
                body(LoginRequest("usertest", "userpassword"))
                    .post(AUTH_ENDPOINT)
            } Then {
                statusCode(200)
            } Extract {
                `as`(JwtResponse::class.java)
            }
        assertEquals(RoleEnum.ROLE_USER.roleName, jwtResponse.roles[0])
    }

    @Test
    fun testGetUserWrongUsername() {
        Given {
            port(port)
                .contentType(ContentType.JSON)
        } When {
            body(LoginRequest("wrongUsername", "wrongPassword"))
                .post(AUTH_ENDPOINT)
        } Then {
            log()
                .all()
                .statusCode(401)
                .log()
                .all()
                .body("error", Matchers.equalTo("Unauthorized"))
        }
    }
}
