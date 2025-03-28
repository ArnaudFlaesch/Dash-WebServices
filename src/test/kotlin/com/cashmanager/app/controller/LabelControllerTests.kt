package com.cashmanager.app.controller

import com.cashmanager.app.controller.requests.InsertLabelPayload
import com.cashmanager.domain.model.LabelDomain
import com.cashmanager.utils.Constants.ADD_LABEL_ENDPOINT
import com.cashmanager.utils.Constants.DELETE_LABEL_ENDPOINT
import com.cashmanager.utils.Constants.LABEL_ENDPOINT
import com.cashmanager.utils.Constants.UPDATE_LABEL_ENDPOINT
import com.common.utils.Constants.UNAUTHORIZED_ERROR
import com.common.utils.IntegrationTestsUtils
import com.common.utils.IntegrationTestsUtils.createAuthenticationHeader
import com.common.utils.SqlData
import io.restassured.RestAssured.defaultParser
import io.restassured.common.mapper.TypeRef
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.parsing.Parser
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.hamcrest.Matchers.containsInAnyOrder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import java.util.stream.Stream

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SqlData
class LabelControllerTests {
    @LocalServerPort
    private val port: Int = 0

    private lateinit var jwtToken: String

    @BeforeAll
    fun testUp() {
        defaultParser = Parser.JSON
        jwtToken = IntegrationTestsUtils.authenticateAdminRole(port).accessToken
    }

    @Test
    fun testAllLabels() {
        val labels: List<LabelDomain> =
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
            } When {
                get(LABEL_ENDPOINT)
            } Then {
                statusCode(200)
            } Extract {
                `as`(object : TypeRef<List<LabelDomain>>() {})
            }
        assertEquals(2, labels.size)
        assertThat(labels.map(LabelDomain::label), containsInAnyOrder("Courses", "Restaurant"))
    }

    @Test
    fun labelCrudTests() {
        val labelToInsert = InsertLabelPayload(newLabel = "Vacances")
        val insertedLabel: LabelDomain =
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
                    .contentType(ContentType.JSON)
                    .body(labelToInsert)
            } When {
                post(ADD_LABEL_ENDPOINT)
            } Then {
                statusCode(200)
            } Extract {
                `as`(LabelDomain::class.java)
            }
        assertNotNull(insertedLabel.id)
        assertEquals(labelToInsert.newLabel, insertedLabel.label)

        val labelToUpdate = insertedLabel.copy(label = "Vacances d'été")
        val updatedLabel: LabelDomain =
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
                    .contentType(ContentType.JSON)
                    .body(labelToUpdate)
            } When {
                patch(UPDATE_LABEL_ENDPOINT)
            } Then {
                statusCode(200)
            } Extract {
                `as`(LabelDomain::class.java)
            }
        assertNotNull(insertedLabel.id)
        assertEquals(labelToUpdate.label, updatedLabel.label)

        Given {
            port(port)
                .header(createAuthenticationHeader(jwtToken))
                .param("labelId", updatedLabel.id)
        } When {
            delete(DELETE_LABEL_ENDPOINT)
        } Then {
            log()
                .all()
                .statusCode(200)
                .log()
                .all()
        }
    }

    @ParameterizedTest
    @MethodSource("testGetEndpointsNames")
    fun testEndpointsNotAuthenticatedError(endpointPath: String) {
        Given {
            port(port)
        } When {
            get(endpointPath)
        } Then {
            log()
                .all()
                .statusCode(401)
                .log()
                .all()
                .body("error", Matchers.equalTo(UNAUTHORIZED_ERROR))
        }
    }

    fun testGetEndpointsNames(): Stream<Arguments> =
        Stream.of(
            arguments(LABEL_ENDPOINT),
            arguments(ADD_LABEL_ENDPOINT),
            arguments(UPDATE_LABEL_ENDPOINT),
            arguments(DELETE_LABEL_ENDPOINT)
        )
}
