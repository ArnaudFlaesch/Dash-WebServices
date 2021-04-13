package com.dash.controller

import io.restassured.RestAssured.defaultParser
import io.restassured.RestAssured.given
import io.restassured.parsing.Parser
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
class WidgetTypeControllerTests {

    @LocalServerPort
    private val port: Int = 0

    @Test
    fun testGetAllWidgetTypes() {
        defaultParser = Parser.JSON

        given().port(port)
            .`when`()
            .get("/widgetTypes/")
            .then().log().all()
            .statusCode(200)
            .log().all()
            .body("size", equalTo(4))
    }
}
