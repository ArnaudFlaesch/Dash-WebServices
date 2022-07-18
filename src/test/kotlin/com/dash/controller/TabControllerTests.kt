package com.dash.controller

import com.common.utils.AbstractIT
import com.common.utils.IntegrationTestsUtils
import com.dash.entity.Tab
import com.dash.repository.TabDataset
import io.restassured.RestAssured.defaultParser
import io.restassured.RestAssured.given
import io.restassured.common.mapper.TypeRef
import io.restassured.http.ContentType
import io.restassured.http.Header
import io.restassured.parsing.Parser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TabDataset
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TabControllerTests : AbstractIT() {

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
        val tabs = given().port(port)
            .header(Header("Authorization", "Bearer $jwtToken"))
            .`when`()
            .get("/tab/")
            .then().log().all()
            .statusCode(200)
            .log().all()
            .extract().`as`(object : TypeRef<List<Tab>>() {})
        assertEquals(1, tabs.size)
    }

    @Test
    fun testAddUpdateDeleteTab() {
        val newTab = Tab(0, "LabelTest", 1)

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

        val tabList = given().port(port)
            .header(Header("Authorization", "Bearer $jwtToken"))
            .`when`()
            .get("/tab/")
            .then().log().all()
            .statusCode(200)
            .log().all()
            .extract().`as`(object : TypeRef<List<Tab>>() {})
        assertEquals(2, tabList.size)

        val updatedLabel = "Updated label"
        
        val updatedTab: Tab = given().port(port)
            .contentType(ContentType.JSON)
            .header(Header("Authorization", "Bearer $jwtToken"))
            .`when`()
            .body(insertedTab.copy(label = updatedLabel))
            .post("/tab/updateTab/")
            .then().log().all()
            .statusCode(200)
            .extract().`as`(Tab::class.java)

        assertNotNull(updatedTab.id)
        assertEquals(updatedLabel, updatedTab.label)

        val updatedTabs: List<Tab> = given().port(port)
            .contentType(ContentType.JSON)
            .header(Header("Authorization", "Bearer $jwtToken"))
            .`when`()
            .body(listOf(updatedTab))
            .post("/tab/updateTabs/")
            .then().log().all()
            .statusCode(200)
            .extract().`as`(object : TypeRef<List<Tab>>() {})
        assertEquals(1, updatedTabs.size)

        given().port(port)
            .contentType(ContentType.JSON)
            .header(Header("Authorization", "Bearer $jwtToken"))
            .`when`().param("id", updatedTab.id)
            .delete("/tab/deleteTab/")
            .then().log().all()
            .statusCode(200)

        val updatedTabList = given().port(port)
            .header(Header("Authorization", "Bearer $jwtToken"))
            .`when`().get("/tab/")
            .then().log().all()
            .statusCode(200)
            .extract().`as`(object : TypeRef<List<Tab>>() {})
        assertEquals(1, updatedTabList.size)
    }
}
