package com.dash.controller

import io.restassured.RestAssured
import io.restassured.parsing.Parser
import org.hamcrest.Matchers.not
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
class ProxyControllerTests {

    @LocalServerPort
    private val port: Int = 0

    @Test
    fun testGetUrl() {
        RestAssured.defaultParser = Parser.XML

        RestAssured.given().port(port)
            .param("url", "http://thelastpictureshow.over-blog.com/rss")
            .`when`()
            .get("/proxy/")
            .then().log().all()
            .statusCode(200)
            .log().all()
            .body("$", not(equals(null)))
    }

    @Test
    fun testGetUrlError() {
        RestAssured.defaultParser = Parser.XML

        RestAssured.given().port(port)
            .param("url", "http://testwrongurl")
            .`when`()
            .get("/proxy/")
            .then().log().all()
            .statusCode(500)
            .log().all()
    }
}
