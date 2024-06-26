package com.common.app.controller

import com.common.app.controller.requests.LoginRequest
import com.common.app.security.response.JwtResponse
import com.common.domain.model.RoleEnum
import com.common.utils.AbstractIT
import io.restassured.RestAssured.defaultParser
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import io.restassured.parsing.Parser
import org.hamcrest.Matchers
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthControllerTests : AbstractIT() {
    @LocalServerPort
    private val port: Int = 0

    @BeforeAll
    fun testUp() {
        defaultParser = Parser.JSON
    }

    @Test
    fun testGetAdmin() {
        val jwtResponse =
            given()
                .port(port)
                .contentType(ContentType.JSON)
                .`when`()
                .body(LoginRequest("admintest", "adminpassword"))
                .post("/auth/login")
                .then()
                .log()
                .all()
                .statusCode(200)
                .log()
                .all()
                .extract()
                .`as`(JwtResponse::class.java)
        assertEquals(RoleEnum.ROLE_ADMIN.roleName, jwtResponse.roles[0])
    }

    @Test
    fun testGetUser() {
        val jwtResponse =
            given()
                .port(port)
                .contentType(ContentType.JSON)
                .`when`()
                .body(LoginRequest("usertest", "userpassword"))
                .post("/auth/login")
                .then()
                .log()
                .all()
                .statusCode(200)
                .log()
                .all()
                .extract()
                .`as`(JwtResponse::class.java)
        assertEquals(RoleEnum.ROLE_USER.roleName, jwtResponse.roles[0])
    }

    @Test
    fun testGetUserWrongUsername() {
        given()
            .port(port)
            .contentType(ContentType.JSON)
            .`when`()
            .body(LoginRequest("wrongUsername", "wrongPassword"))
            .post("/auth/login")
            .then()
            .log()
            .all()
            .statusCode(401)
            .log()
            .all()
            .body("error", Matchers.equalTo("Unauthorized"))
    }
}
