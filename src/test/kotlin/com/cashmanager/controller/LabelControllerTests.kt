package com.cashmanager.controller

import com.cashmanager.controller.requests.InsertLabelPayload
import com.cashmanager.entity.Label
import com.common.utils.AbstractIT
import com.common.utils.IntegrationTestsUtils
import com.dash.repository.TabDataset
import com.dash.repository.WidgetDataset
import io.restassured.RestAssured.defaultParser
import io.restassured.RestAssured.given
import io.restassured.common.mapper.TypeRef
import io.restassured.http.ContentType
import io.restassured.http.Header
import io.restassured.parsing.Parser
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.hamcrest.Matchers.containsInAnyOrder
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
@WidgetDataset
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LabelControllerTests : AbstractIT() {

    @LocalServerPort
    private val port: Int = 0

    private var jwtToken: String? = null

    private val LABEL_ENDPOINT = "/label/"

    @BeforeAll
    fun testUp() {
        defaultParser = Parser.JSON
        jwtToken = IntegrationTestsUtils.authenticateAdmin(port).accessToken
    }

    @Test
    fun testAllLabels() {
        val labels: List<Label> = given().port(port)
            .header(Header("Authorization", "Bearer $jwtToken"))
            .`when`().get("$LABEL_ENDPOINT")
            .then().log().all()
            .statusCode(200)
            .log().all()
            .body("size", Matchers.equalTo(2))
            .extract()
            .`as`(object : TypeRef<List<Label>>() {})
        assertEquals(2, labels.size)
        assertThat(labels.map(Label::label), containsInAnyOrder("Courses", "Restaurant"))
    }

    @Test
    fun labelCrudTests() {
        val labelToInsert = InsertLabelPayload(newLabel = "Vacances")
        val insertedLabel: Label = given()
            .port(port)
            .header(Header("Authorization", "Bearer $jwtToken"))
            .contentType(ContentType.JSON)
            .body(labelToInsert)
            .`when`().post("${LABEL_ENDPOINT}addLabel/")
            .then().log().all()
            .statusCode(200)
            .log().all()
            .extract()
            .`as`(Label::class.java)
        assertNotNull(insertedLabel.id)
        assertEquals(labelToInsert.newLabel, insertedLabel.label)

        val labelToUpdate = insertedLabel.copy(label = "Vacances d'été")
        val updatedLabel: Label = given()
            .port(port)
            .header(Header("Authorization", "Bearer $jwtToken"))
            .contentType(ContentType.JSON)
            .body(labelToUpdate)
            .`when`().patch("${LABEL_ENDPOINT}updateLabel/")
            .then().log().all()
            .statusCode(200)
            .log().all()
            .extract()
            .`as`(Label::class.java)
        assertNotNull(insertedLabel.id)
        assertEquals(labelToUpdate.label, updatedLabel.label)

        given()
            .port(port)
            .header(Header("Authorization", "Bearer $jwtToken"))
            .param("id", updatedLabel.id)
            .`when`().delete("${LABEL_ENDPOINT}deleteLabel/")
            .then().log().all()
            .statusCode(200)
            .log().all()
    }
}
