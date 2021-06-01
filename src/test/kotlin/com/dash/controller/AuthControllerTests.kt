package com.dash.controller

import com.dash.enums.RoleEnum
import com.dash.repository.TabDataset
import com.dash.security.payload.LoginRequest
import com.dash.security.response.JwtResponse
import io.restassured.RestAssured.defaultParser
import io.restassured.RestAssured.given
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
class AuthControllerTests {

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
            .contentType("application/json")
            .`when`()
            .body(LoginRequest("admintest", "adminpassword"))
            .post("/auth/login/")
            .then().log().all()
            .statusCode(200)
            .log().all()
            .body("$", Matchers.not(equals(null)))
            .extract().`as`(JwtResponse::class.java)
        assertEquals(RoleEnum.ROLE_ADMIN.toString(), jwtResponse.roles[0])
    }
}
