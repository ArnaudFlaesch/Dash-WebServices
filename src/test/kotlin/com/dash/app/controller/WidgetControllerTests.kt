package com.dash.app.controller

import com.common.utils.IntegrationTestsUtils
import com.common.utils.IntegrationTestsUtils.createAuthenticationHeader
import com.common.utils.SqlData
import com.dash.app.controller.requests.widget.CreateWidgetPayload
import com.dash.domain.model.WidgetDomain
import io.restassured.RestAssured.defaultParser
import io.restassured.common.mapper.TypeRef
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.parsing.Parser
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SqlData
class WidgetControllerTests {
    @LocalServerPort
    private val port: Int = 0

    private lateinit var jwtToken: String

    companion object {
        const val WIDGET_ENDPOINT = "/widget/"
    }

    @Nested
    @DisplayName("Widget normal cases tests")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class WidgetNormalCases {
        @BeforeAll
        fun testUp() {
            defaultParser = Parser.JSON
            jwtToken = IntegrationTestsUtils.authenticateAdminRole(port).accessToken
        }

        @Test
        fun testGetAllWidgetsByTabId() {
            val widgetDomainList =
                Given {
                    port(port)
                        .header(createAuthenticationHeader(jwtToken))
                        .param("tabId", 1)
                } When {
                    get(WIDGET_ENDPOINT)
                } Then {
                    log()
                        .all()
                        .statusCode(200)
                        .log()
                        .all()
                } Extract {
                    `as`(object : TypeRef<List<WidgetDomain>>() {})
                }
            assertEquals(2, widgetDomainList.size)
        }

        @Test
        fun insertWidgetToDatabase() {
            val widget = CreateWidgetPayload(2, 1)

            val insertedWidgetDomain: WidgetDomain =
                Given {
                    contentType(ContentType.JSON)
                        .header(createAuthenticationHeader(jwtToken))
                        .port(port)
                        .body(widget)
                } When {
                    post("${WIDGET_ENDPOINT}addWidget")
                } Then {
                    log()
                        .all()
                        .statusCode(200)
                } Extract {
                    `as`(WidgetDomain::class.java)
                }

            assertNotNull(insertedWidgetDomain.id)
            assertEquals(insertedWidgetDomain.type, widget.type)

            val widgetDomainList =
                Given {
                    port(port)
                        .header(createAuthenticationHeader(jwtToken))
                        .param("tabId", 1)
                } When {
                    get(WIDGET_ENDPOINT)
                } Then {
                    log()
                        .all()
                        .statusCode(200)
                        .log()
                        .all()
                } Extract {
                    `as`(object : TypeRef<List<WidgetDomain>>() {})
                }
            assertEquals(3, widgetDomainList.size)

            val updatedWidgetDomain: WidgetDomain =
                Given {
                    header(createAuthenticationHeader(jwtToken))
                        .contentType(ContentType.JSON)
                        .port(port)
                        .body(insertedWidgetDomain.copy(widgetOrder = 0, data = "{}"))
                } When {
                    patch("${WIDGET_ENDPOINT}updateWidgetData/${insertedWidgetDomain.id}")
                } Then {
                    log()
                        .all()
                        .statusCode(200)
                } Extract {
                    `as`(WidgetDomain::class.java)
                }

            assertEquals("{}", updatedWidgetDomain.data)

            Given {
                header(createAuthenticationHeader(jwtToken))
                    .contentType(ContentType.JSON)
                    .port(port)
                    .param("id", updatedWidgetDomain.id)
            } When {
                delete("${WIDGET_ENDPOINT}deleteWidget")
            } Then {
                statusCode(200)
            }

            val updatedWidgetListDomain =
                Given {
                    header(createAuthenticationHeader(jwtToken))
                        .port(port)
                        .param("tabId", 1)
                } When {
                    get(WIDGET_ENDPOINT)
                } Then {
                    log()
                        .all()
                        .statusCode(200)
                        .log()
                        .all()
                } Extract {
                    `as`(object : TypeRef<List<WidgetDomain>>() {})
                }
            assertEquals(2, updatedWidgetListDomain.size)
        }

        @Test
        fun testUpdateWidgetsOrder() {
            val firstWidget = CreateWidgetPayload(2, 1)
            val secondWidget = CreateWidgetPayload(3, 1)

            val firstInsertedWidgetDomain: WidgetDomain =
                Given {
                    header(createAuthenticationHeader(jwtToken))
                        .contentType(ContentType.JSON)
                        .port(port)
                        .body(firstWidget)
                } When {
                    post("${WIDGET_ENDPOINT}addWidget")
                } Then {
                    log()
                        .all()
                        .statusCode(200)
                } Extract {
                    `as`(WidgetDomain::class.java)
                }

            val secondInsertedWidgetDomain: WidgetDomain =
                Given {
                    header(createAuthenticationHeader(jwtToken))
                        .contentType(ContentType.JSON)
                        .port(port)
                        .body(secondWidget)
                } When {
                    post("${WIDGET_ENDPOINT}addWidget")
                } Then {
                    log()
                        .all()
                        .statusCode(200)
                } Extract {
                    `as`(WidgetDomain::class.java)
                }

            assertNotNull(firstInsertedWidgetDomain.id)
            assertEquals(firstInsertedWidgetDomain.type, firstWidget.type)

            assertNotNull(secondInsertedWidgetDomain.id)
            assertEquals(secondInsertedWidgetDomain.type, secondWidget.type)

            val updatedWidgetList: List<WidgetDomain> =
                Given {
                    header(createAuthenticationHeader(jwtToken))
                        .contentType(ContentType.JSON)
                        .port(port)
                        .body(
                            listOf(
                                firstInsertedWidgetDomain.copy(widgetOrder = 2),
                                secondInsertedWidgetDomain.copy(widgetOrder = 3)
                            )
                        )
                } When {
                    post("${WIDGET_ENDPOINT}updateWidgetsOrder")
                } Then {
                    log()
                        .all()
                        .statusCode(200)
                } Extract {
                    `as`(object : TypeRef<List<WidgetDomain>>() {})
                }

            assertEquals(2, updatedWidgetList.size)
            assertEquals(2, updatedWidgetList[0].widgetOrder)
            assertEquals(2, updatedWidgetList[0].type)
            assertEquals(3, updatedWidgetList[1].widgetOrder)
            assertEquals(3, updatedWidgetList[1].type)
        }
    }

    @Nested
    @DisplayName("Widget edge cases tests")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class WidgetEdgeCases {
        @BeforeAll
        fun testUp() {
            defaultParser = Parser.JSON
            jwtToken = IntegrationTestsUtils.authenticateUserRole(port).accessToken
        }

        @Test
        fun shouldNotDeleteWidgetWithoutAdminRights() {
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
                    .param("id", 1)
            } When {
                delete("${WIDGET_ENDPOINT}deleteWidget")
            } Then {
                statusCode(403)
            }
        }
    }
}
