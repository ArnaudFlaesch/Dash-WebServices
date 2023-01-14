package com.common.app.controller

import com.common.utils.AbstractIT
import io.restassured.RestAssured.defaultParser
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import io.restassured.parsing.Parser
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HealthControllerTests : AbstractIT() {

    @LocalServerPort
    private val port: Int = 0

    @BeforeAll
    fun testUp() {
        defaultParser = Parser.TEXT
    }

    @Test
    fun testGetHealthCheck() {
        given()
            .port(port)
            .`when`()
            .get("/healthCheck/status")
            .then().log().all()
            .statusCode(200)
            .contentType(ContentType.TEXT)
    }
}
