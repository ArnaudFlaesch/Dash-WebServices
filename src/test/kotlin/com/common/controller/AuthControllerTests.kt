package com.dash.controller

import com.common.security.response.JwtResponse
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
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
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
        val jwtResponse = given()
            .port(port)
            .contentType(ContentType.JSON)
            .`when`()
            .body(LoginRequest("admintest", "adminpassword"))
            .post("/auth/login/")
            .then().log().all()
            .statusCode(200)
            .log().all()
            .extract().`as`(JwtResponse::class.java)
        assertEquals("ROLE_ADMIN", jwtResponse.roles[0])
    }

    @Test
    fun testGetUserWrongUsername() {
        given()
            .port(port)
            .contentType(ContentType.JSON)
            .`when`()
            .body(LoginRequest("wrongUsername", "wrongPassword"))
            .post("/auth/login/")
            .then().log().all()
            .statusCode(401)
            .log().all()
            .body("error", Matchers.equalTo("Unauthorized"))
    }
}
