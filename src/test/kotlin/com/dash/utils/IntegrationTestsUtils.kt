package com.dash.utils

import com.dash.controller.requests.LoginRequest
import com.dash.security.response.JwtResponse
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.Matchers

object IntegrationTestsUtils {

    fun authenticateAdmin(port: Int): JwtResponse {
        return given()
            .port(port)
            .contentType(ContentType.JSON)
            .`when`()
            .body(LoginRequest("admintest", "adminpassword"))
            .post("/auth/login/")
            .then().log().all()
            .statusCode(200)
            .log().all()
            .body("$", Matchers.notNullValue())
            .extract().`as`(JwtResponse::class.java)
    }
}
