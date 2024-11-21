package com.common.utils

import com.common.app.controller.requests.LoginRequest
import com.common.app.security.response.JwtResponse
import io.restassured.http.ContentType
import io.restassured.http.Header
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.Matchers

object IntegrationTestsUtils {
    fun authenticateAdminRole(port: Int): JwtResponse = authenticate("admintest", "adminpassword", port)

    fun authenticateUserRole(port: Int): JwtResponse = authenticate("usertest", "userpassword", port)

    private fun authenticate(
        username: String,
        password: String,
        port: Int
    ): JwtResponse =
        Given {
            port(port)
                .contentType(ContentType.JSON)
        } When {
            body(LoginRequest(username, password))
                .post("/auth/login")
        } Then {
            log()
                .all()
                .statusCode(200)
                .log()
                .all()
                .body("$", Matchers.notNullValue())
        } Extract {
            `as`(JwtResponse::class.java)
        }

    fun createAuthenticationHeader(jwtToken: String): Header = Header("Authorization", "Bearer $jwtToken")
}
