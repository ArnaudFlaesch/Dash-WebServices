package com.dash.app.controller

import com.common.utils.IntegrationTestsUtils
import com.common.utils.IntegrationTestsUtils.createAuthenticationHeader
import com.common.utils.SqlData
import com.dash.app.controller.requests.widget.CreateMiniWidgetPayload
import com.dash.domain.model.MiniWidgetDomain
import io.restassured.RestAssured.defaultParser
import io.restassured.common.mapper.TypeRef
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
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
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SqlData
class MiniWidgetControllerTests {
    @LocalServerPort
    private val port: Int = 0

    private lateinit var jwtToken: String

    companion object {
        const val MINI_WIDGET_ENDPOINT = "/miniWidget/"
    }

    @BeforeAll
    fun testUp() {
        defaultParser = Parser.JSON
        jwtToken = IntegrationTestsUtils.authenticateAdminRole(port).accessToken
    }

    @Test
    fun insertWidgetToDatabase() {
        val widget = CreateMiniWidgetPayload(1)

        val insertedMiniWidgetDomain: MiniWidgetDomain =
            Given {
                contentType(ContentType.JSON)
                    .header(createAuthenticationHeader(jwtToken))
                    .port(port)
                    .body(widget)
            } When {
                post("${MINI_WIDGET_ENDPOINT}addMiniWidget")
            } Then {
                statusCode(200)
            } Extract {
                `as`(MiniWidgetDomain::class.java)
            }

        assertNotNull(insertedMiniWidgetDomain.id)
        assertEquals(insertedMiniWidgetDomain.type, widget.type)

        val widgetDomainList =
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
            } When {
                get(MINI_WIDGET_ENDPOINT)
            } Then {
                statusCode(200)
            } Extract {
                `as`(object : TypeRef<List<MiniWidgetDomain>>() {})
            }
        assertEquals(1, widgetDomainList.size)

        val updatedMiniWidgetDomain: MiniWidgetDomain =
            Given {
                header(createAuthenticationHeader(jwtToken))
                    .contentType(ContentType.JSON)
                    .port(port)
                    .body(insertedMiniWidgetDomain.copy(data = "{}"))
            } When {
                patch("${MINI_WIDGET_ENDPOINT}updateWidgetData/${insertedMiniWidgetDomain.id}")
            } Then {
                statusCode(200)
            } Extract {
                `as`(MiniWidgetDomain::class.java)
            }

        assertNotNull(updatedMiniWidgetDomain.id)
        assertEquals("{}", updatedMiniWidgetDomain.data)
        assertNotNull(updatedMiniWidgetDomain.userId)

        val updatedWidgetListDomain =
            Given {
                header(createAuthenticationHeader(jwtToken))
                    .port(port)
            } When {
                get(MINI_WIDGET_ENDPOINT)
            } Then {
                statusCode(200)
            } Extract {
                `as`(object : TypeRef<List<MiniWidgetDomain>>() {})
            }
        assertEquals(1, updatedWidgetListDomain.size)

        Given {
            header(createAuthenticationHeader(jwtToken))
                .port(port)
        } When {
            param("widgetId", insertedMiniWidgetDomain.id)
                .delete("${MINI_WIDGET_ENDPOINT}deleteMiniWidget")
        } Then {
            log()
                .all()
                .statusCode(200)
                .log()
                .all()
        }

        val updatedMiniWidgetList =
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
            } When {
                get(MINI_WIDGET_ENDPOINT)
            } Then {
                statusCode(200)
            } Extract {
                `as`(object : TypeRef<List<MiniWidgetDomain>>() {})
            }
        assertEquals(0, updatedMiniWidgetList.size)
    }
}
