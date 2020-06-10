package com.dash.controller

import com.dash.controller.WidgetController
import com.dash.entity.Tab
import com.dash.entity.Widget
import com.dash.repository.WidgetRepository
import groovy.json.JsonOutput.toJson
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import io.restassured.RestAssured.given
import org.hamcrest.Matchers.*
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WidgetControllerTests(@Autowired val widgetRepository: WidgetRepository) {

    @LocalServerPort
    private val port: Int = 0

    /*@Test
    fun kotlin_rest_assured_example() {
        val p = Widget(1,2, null, 1, null)
        val jsonWidget = JSONObject()
        jsonWidget.put("id", 1)
        jsonWidget.put("type", 2)
        widgetRepository.save(p)
        given().
                port(port)
                .param("tabId", 1).
                `when`().
            get("/widget/").
                then().
                log().all().
                statusCode(200).
                        log().all()
                //body("id", hasItem(p.id)).and()
                //.body("type", hasItem(p.type))
    }
    */
}