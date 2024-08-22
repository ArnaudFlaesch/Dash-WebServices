package com.common.utils

import com.common.app.controller.requests.LoginRequest
import com.common.app.security.response.JwtResponse
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import io.restassured.http.Header
import org.hamcrest.Matchers

object IntegrationTestsUtils {
    fun authenticateAdminRole(port: Int): JwtResponse = authenticate("admintest", "adminpassword", port)

    fun authenticateUserRole(port: Int): JwtResponse = authenticate("usertest", "userpassword", port)

    private fun authenticate(username: String, password: String, port: Int): JwtResponse =
        given()
            .port(port)
            .contentType(ContentType.JSON)
            .`when`()
            .body(LoginRequest(username, password))
            .post("/auth/login")
            .then()
            .log()
            .all()
            .statusCode(200)
            .log()
            .all()
            .body("$", Matchers.notNullValue())
            .extract()
            .`as`(JwtResponse::class.java)

    fun createAuthenticationHeader(jwtToken: String): Header = Header("Authorization", "Bearer $jwtToken")
}
