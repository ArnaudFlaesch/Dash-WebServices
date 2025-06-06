package com.dash.app.controller

import com.common.utils.IntegrationTestsUtils
import com.common.utils.IntegrationTestsUtils.createAuthenticationHeader
import com.common.utils.SqlData
import com.dash.app.controller.requests.tab.CreateTabPayload
import com.dash.app.controller.requests.tab.UpdateTabPayload
import com.dash.domain.model.TabDomain
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
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
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
                get("/tab/")
            } Then {
                statusCode(200)
            } Extract {
                `as`(object : TypeRef<List<TabDomain>>() {})
            }
        assertEquals(2, tabsDomain.size)
    }

    @Test
    fun testGetTabsWrongToken() {
        Given {
            port(port)
                .header(Header("Authorization", "wrong_token"))
        } When {
            get("/tab/")
        } Then {
            statusCode(401)
        }
    }

    @Test
    fun testAddUpdateDeleteTab() {
        val newTab = CreateTabPayload("LabelTest")

        val insertedTab: TabDomain =
            Given {
                port(port)
                    .contentType(ContentType.JSON)
                    .header(createAuthenticationHeader(jwtToken))
            } When {
                body(newTab)
                    .post("/tab/addTab")
            } Then {
                statusCode(200)
            } Extract {
                `as`(TabDomain::class.java)
            }

        assertNotNull(insertedTab.id)
        assertEquals(insertedTab.label, newTab.label)
        assertNotNull(insertedTab.userId)

        val tabList =
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
            } When {
                get("/tab/")
            } Then {
                statusCode(200)
            } Extract {
                `as`(object : TypeRef<List<TabDomain>>() {})
            }
        assertEquals(3, tabList.size)

        val updatedLabel = "Updated label"

        val updatedTab: TabDomain =
            Given {
                port(port)
                    .contentType(ContentType.JSON)
                    .header(createAuthenticationHeader(jwtToken))
            } When {
                body(
                    UpdateTabPayload(
                        id = insertedTab.id,
                        label = updatedLabel,
                        tabOrder = insertedTab.tabOrder
                    )
                ).post("/tab/updateTab")
            } Then {
                statusCode(200)
            } Extract {
                `as`(TabDomain::class.java)
            }

        assertNotNull(updatedTab.id)
        assertEquals(updatedLabel, updatedTab.label)

        val updatedTabsDomain: List<TabDomain> =
            Given {
                port(port)
                    .contentType(ContentType.JSON)
                    .header(createAuthenticationHeader(jwtToken))
            } When {
                body(listOf(updatedTab))
                    .post("/tab/updateTabs")
            } Then {
                statusCode(200)
            } Extract {
                `as`(object : TypeRef<List<TabDomain>>() {})
            }
        assertEquals(1, updatedTabsDomain.size)

        Given {
            port(port)
                .contentType(ContentType.JSON)
                .header(createAuthenticationHeader(jwtToken))
        } When {
            param("id", updatedTab.id)
                .delete("/tab/deleteTab")
        } Then {
            log()
                .all()
                .statusCode(200)
        }

        val updatedTabListDomain =
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
            } When {
                get("/tab/")
            } Then {
                statusCode(200)
            } Extract {
                `as`(object : TypeRef<List<TabDomain>>() {})
            }
        assertEquals(2, updatedTabListDomain.size)
    }
}
