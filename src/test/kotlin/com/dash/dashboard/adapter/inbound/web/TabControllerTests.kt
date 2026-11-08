package com.dash.dashboard.adapter.inbound.web

import com.dash.dashboard.adapter.inbound.web.request.tab.CreateTabRequest
import com.dash.dashboard.adapter.inbound.web.request.tab.UpdateTabRequest
import com.dash.dashboard.domain.model.tab.Tab
import com.dash.utils.IntegrationTestsUtils
import com.dash.utils.IntegrationTestsUtils.createAuthenticationHeader
import com.dash.utils.SqlData
import io.restassured.RestAssured.defaultParser
import io.restassured.common.mapper.TypeRef
import io.restassured.http.ContentType
import io.restassured.http.Header
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
class TabControllerTests {
    @LocalServerPort
    private val port: Int = 0

    private lateinit var jwtToken: String

    @BeforeAll
    fun testUp() {
        defaultParser = Parser.JSON
        jwtToken = IntegrationTestsUtils.authenticateAdminRole(port).accessToken
    }

    @Test
    fun testGetAllTabs() {
        val tabsDomain =
            Given {
                port(port)
                header(createAuthenticationHeader(jwtToken))
            } When {
                get("/tabs")
            } Then {
                statusCode(200)
            } Extract {
                `as`(object : TypeRef<List<Tab>>() {})
            }
        assertEquals(2, tabsDomain.size)
    }

    @Test
    fun testGetTabsWrongToken() {
        Given {
            port(port)
                .header(Header("Authorization", "wrong_token"))
        } When {
            get("/tabs")
        } Then {
            statusCode(401)
        }
    }

    @Test
    fun shouldNotUpdateAnotherUsersTabs() {
        val userJwtToken = IntegrationTestsUtils.authenticateUserRole(port).accessToken
        val adminTab = Tab(id = 1, label = "Stolen", tabOrder = 1, userId = 1)

        Given {
            port(port)
                .contentType(ContentType.JSON)
                .header(createAuthenticationHeader(userJwtToken))
                .body(UpdateTabRequest(adminTab.label, adminTab.tabOrder))
        } When {
            put("/tabs/${adminTab.id}")
        } Then {
            statusCode(403)
        }

        Given {
            port(port)
                .contentType(ContentType.JSON)
                .header(createAuthenticationHeader(userJwtToken))
                .body(listOf(adminTab))
        } When {
            patch("/tabs")
        } Then {
            statusCode(403)
        }
    }

    @Test
    fun testAddUpdateDeleteTab() {
        val newTab = CreateTabRequest("LabelTest")

        val insertedTab: Tab =
            Given {
                port(port)
                    .contentType(ContentType.JSON)
                    .header(createAuthenticationHeader(jwtToken))
            } When {
                body(newTab)
                    .post("/tabs")
            } Then {
                statusCode(201)
            } Extract {
                `as`(Tab::class.java)
            }

        assertNotNull(insertedTab.id)
        assertEquals(insertedTab.label, newTab.label)
        assertNotNull(insertedTab.userId)

        val tabList =
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
            } When {
                get("/tabs")
            } Then {
                statusCode(200)
            } Extract {
                `as`(object : TypeRef<List<Tab>>() {})
            }
        assertEquals(3, tabList.size)

        val updatedLabel = "Updated label"

        val updatedTab: Tab =
            Given {
                port(port)
                    .contentType(ContentType.JSON)
                    .header(createAuthenticationHeader(jwtToken))
            } When {
                body(
                    UpdateTabRequest(
                        label = updatedLabel,
                        tabOrder = insertedTab.tabOrder
                    )
                ).put("/tabs/${insertedTab.id}")
            } Then {
                statusCode(200)
            } Extract {
                `as`(Tab::class.java)
            }

        assertNotNull(updatedTab.id)
        assertEquals(updatedLabel, updatedTab.label)

        val updatedTabsDomain: List<Tab> =
            Given {
                port(port)
                    .contentType(ContentType.JSON)
                    .header(createAuthenticationHeader(jwtToken))
            } When {
                body(listOf(updatedTab))
                    .patch("/tabs")
            } Then {
                statusCode(200)
            } Extract {
                `as`(object : TypeRef<List<Tab>>() {})
            }
        assertEquals(1, updatedTabsDomain.size)

        Given {
            port(port)
                .contentType(ContentType.JSON)
                .header(createAuthenticationHeader(jwtToken))
        } When {
            delete("/tabs/${updatedTab.id}")
        } Then {
            log()
                .all()
                .statusCode(204)
        }

        val updatedTabListDomain =
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
            } When {
                get("/tabs")
            } Then {
                statusCode(200)
            } Extract {
                `as`(object : TypeRef<List<Tab>>() {})
            }
        assertEquals(2, updatedTabListDomain.size)
    }
}
