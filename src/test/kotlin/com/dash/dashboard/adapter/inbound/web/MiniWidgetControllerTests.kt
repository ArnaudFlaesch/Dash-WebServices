package com.dash.dashboard.adapter.inbound.web

import com.dash.dashboard.adapter.inbound.web.request.widget.CreateMiniWidgetRequest
import com.dash.dashboard.domain.model.widget.MiniWidget
import com.dash.utils.IntegrationTestsUtils
import com.dash.utils.IntegrationTestsUtils.createAuthenticationHeader
import com.dash.utils.SqlData
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
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
        val widget = CreateMiniWidgetRequest(1)

        val insertedMiniWidgetDomain: MiniWidget =
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
                `as`(MiniWidget::class.java)
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
                `as`(object : TypeRef<List<MiniWidget>>() {})
            }
        assertEquals(1, widgetDomainList.size)

        val updatedMiniWidgetDomain: MiniWidget =
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
                `as`(MiniWidget::class.java)
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
                `as`(object : TypeRef<List<MiniWidget>>() {})
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
                `as`(object : TypeRef<List<MiniWidget>>() {})
            }
        assertEquals(0, updatedMiniWidgetList.size)
    }
}
