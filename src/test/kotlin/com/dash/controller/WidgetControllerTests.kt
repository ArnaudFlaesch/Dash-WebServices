package com.dash.controller

import com.dash.entity.Tab
import com.dash.entity.Widget
import com.dash.repository.TabDataset
import com.dash.repository.WidgetRepository
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TabDataset
@ExtendWith(SpringExtension::class)
class WidgetControllerTests(@Autowired val widgetRepository: WidgetRepository) {

    @LocalServerPort
    private val port: Int = 0

    @Test
    fun kotlin_rest_assured_example() {
        given().
                port(port)
                .param("tabId", 1).
                `when`().
            get("/widget/").
                then().
                log().all().
                statusCode(200).
                        log().all()
                .body("size", equalTo(0))
    }

    @Test
    fun insertWidgetToDatabase() {
        val tab = Tab(10)
        val widget = Widget(id = 3, type = 2, tab= tab)

        val updated = given().contentType(ContentType.JSON)
                .port(port)
                .body(widget).
                `when`().

                post("/widget/addWidget/").
                then().
                log().all().
                statusCode(200)

        given().
        port(port)
                .param("tabId", 10).
                `when`().
                get("/widget/").
                then().
                log().all().
                statusCode(200).
                log().all()
                .body("size", equalTo(1))
    }
}