package com.dash.dashboard.adapter.inbound.web

import com.dash.dashboard.adapter.inbound.web.request.incident.IncidentWidgetRequest
import com.dash.dashboard.adapter.inbound.web.request.widget.CreateWidgetRequest
import com.dash.dashboard.adapter.inbound.web.request.widget.UpdateWidgetDataRequest
import com.dash.dashboard.domain.model.widget.Widget
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
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
                    `as`(object : TypeRef<List<Widget>>() {})
                }
            assertEquals(2, widgetDomainList.size)
        }

        @Test
        fun insertWidgetToDatabase() {
            val widget = CreateWidgetRequest(2, 1)

            val insertedWidgetDomain: Widget =
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
                    `as`(Widget::class.java)
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
                    `as`(object : TypeRef<List<Widget>>() {})
                }
            assertEquals(3, widgetDomainList.size)

            val updatedWidgetDomain: Widget =
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
                    `as`(Widget::class.java)
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
                    `as`(object : TypeRef<List<Widget>>() {})
                }
            assertEquals(2, updatedWidgetListDomain.size)
        }

        @Test
        fun testUpdateWidgetsOrder() {
            val firstWidget = CreateWidgetRequest(2, 1)
            val secondWidget = CreateWidgetRequest(3, 1)

            val firstInsertedWidgetDomain: Widget =
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
                    `as`(Widget::class.java)
                }

            val secondInsertedWidgetDomain: Widget =
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
                    `as`(Widget::class.java)
                }

            assertNotNull(firstInsertedWidgetDomain.id)
            assertEquals(firstInsertedWidgetDomain.type, firstWidget.type)

            assertNotNull(secondInsertedWidgetDomain.id)
            assertEquals(secondInsertedWidgetDomain.type, secondWidget.type)

            val updatedWidgetList: List<Widget> =
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
                    `as`(object : TypeRef<List<Widget>>() {})
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

        @Test
        fun shouldNotAddWidgetToAnotherUsersTab() {
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
                    .contentType(ContentType.JSON)
                    .body(CreateWidgetRequest(type = 1, tabId = 1))
            } When {
                post("${WIDGET_ENDPOINT}addWidget")
            } Then {
                statusCode(403)
            }
        }

        @Test
        fun shouldNotUpdateAnotherUsersWidgetData() {
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
                    .contentType(ContentType.JSON)
                    .body(UpdateWidgetDataRequest(mapOf("city" to "Lyon")))
            } When {
                patch("${WIDGET_ENDPOINT}updateWidgetData/1")
            } Then {
                statusCode(403)
            }
        }

        @Test
        fun shouldNotReorderAnotherUsersWidgets() {
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
                    .contentType(ContentType.JSON)
                    .body(listOf(Widget(id = 1, type = 1, data = null, widgetOrder = 5, tabId = 1)))
            } When {
                post("${WIDGET_ENDPOINT}updateWidgetsOrder")
            } Then {
                statusCode(403)
            }
        }
    }

    @Nested
    @DisplayName("Incident widget deletion tests")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class IncidentWidgetDeletion {
        private val incidentWidgetId = 55

        @BeforeAll
        fun testUp() {
            defaultParser = Parser.JSON
            jwtToken = IntegrationTestsUtils.authenticateAdminRole(port).accessToken
        }

        @Test
        fun shouldDeleteWidgetWithIncidentHistory() {
            // Ending a streak creates the incident and a streak, which both reference the widget
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
                    .contentType(ContentType.JSON)
                    .body(IncidentWidgetRequest(incidentWidgetId))
            } When {
                post("/incidentWidget/endStreak")
            } Then {
                statusCode(200)
            }

            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
                    .param("id", incidentWidgetId)
            } When {
                delete("${WIDGET_ENDPOINT}deleteWidget")
            } Then {
                statusCode(200)
            }
        }
    }
}
