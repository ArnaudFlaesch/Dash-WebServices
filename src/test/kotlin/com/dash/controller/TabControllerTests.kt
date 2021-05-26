package com.dash.controller

import com.dash.entity.Tab
import com.dash.repository.TabDataset
import com.dash.utils.IntegrationTestsUtils
import io.restassured.RestAssured.defaultParser
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import io.restassured.http.Header
import io.restassured.parsing.Parser
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TabDataset
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TabControllerTests {

    @LocalServerPort
    private val port: Int = 0

    private var jwtToken: String? = null

    @BeforeAll
    fun testUp() {
        defaultParser = Parser.JSON
        jwtToken = IntegrationTestsUtils.authenticateAdmin(port).accessToken
    }

    @Test
    fun testGetAllTabs() {

        given().port(port)
            .header(Header("Authorization", "Bearer $jwtToken"))
            .`when`()
            .get("/tab/")
            .then().log().all()
            .statusCode(200)
            .log().all()
            .body("size", equalTo(1))
    }

    @Test
    fun testAddUpdateDeleteTab() {
        val newTab = Tab(null, "LabelTest")

        val insertedTab: Tab = given().port(port)
            .contentType(ContentType.JSON)
            .header(Header("Authorization", "Bearer $jwtToken"))
            .`when`()
            .body(newTab)
            .post("/tab/addTab/")
            .then().log().all()
            .statusCode(200)
            .extract().`as`(Tab::class.java)

        assertNotNull(insertedTab.id)
        assertEquals(insertedTab.label, newTab.label)

        given().port(port)
            .header(Header("Authorization", "Bearer $jwtToken"))
            .`when`()
            .get("/tab/")
            .then().log().all()
            .statusCode(200)
            .log().all()
            .body("size", equalTo(2))

        insertedTab.label = "Updated label"

        val updatedTab: Tab = given().port(port)
            .contentType(ContentType.JSON)
            .header(Header("Authorization", "Bearer $jwtToken"))
            .`when`()
            .body(insertedTab)
            .post("/tab/updateTab/")
            .then().log().all()
            .statusCode(200)
            .extract().`as`(Tab::class.java)

        assertNotNull(updatedTab.id)
        assertEquals(updatedTab.label, insertedTab.label)

        val updatedTabs: List<Tab> = given().port(port)
            .contentType(ContentType.JSON)
            .header(Header("Authorization", "Bearer $jwtToken"))
            .`when`()
            .body(listOf(updatedTab))
            .post("/tab/updateTabs/")
            .then().log().all()
            .statusCode(200)
            .extract().jsonPath().getList("tabs", Tab::class.java)

        assertEquals(1, updatedTabs.size)

        given().port(port)
            .contentType(ContentType.JSON)
            .header(Header("Authorization", "Bearer $jwtToken"))
            .`when`()
            .param("id", updatedTab.id)
            .delete("/tab/deleteTab/")
            .then().log().all()
            .statusCode(200)

        given().port(port)
            .header(Header("Authorization", "Bearer $jwtToken"))
            .`when`()
            .get("/tab/")
            .then().log().all()
            .statusCode(200)
            .log().all()
            .body("size", equalTo(1))
    }
}
