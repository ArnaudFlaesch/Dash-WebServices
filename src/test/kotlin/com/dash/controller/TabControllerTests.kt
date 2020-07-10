package com.dash.controller

import com.dash.entity.Tab
import com.dash.repository.TabDataset
import com.dash.repository.TabRepository
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.hasItem
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TabDataset
@ExtendWith(SpringExtension::class)
class TabControllerTests(@Autowired val tabRepository: TabRepository) {

    @LocalServerPort
    private val port: Int = 0

    @Test
    fun kotlin_rest_assured_example() {
        given().port(port)
                .`when`()
                .get("/tab/")
                .then().log().all()
                .statusCode(200)
                .log().all()
                .body("size", equalTo(1))
    }

    @Test
    fun addUpdateDeleteTab() {
        val newTab = Tab(null, "LabelTest")

        val insertedTab = given().port(port)
                .contentType(ContentType.JSON)
                .`when`()
                .body(newTab)
                .post("/tab/addTab/")
                .then().log().all()
                .statusCode(200)

        given().port(port)
                .`when`()
                .get("/tab/")
                .then().log().all()
                .statusCode(200)
                .log().all()
                .body("size", equalTo(2))
    }
}