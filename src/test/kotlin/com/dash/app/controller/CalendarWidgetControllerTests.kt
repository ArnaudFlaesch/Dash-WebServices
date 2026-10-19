package com.dash.app.controller

import com.common.utils.Constants.UNAUTHORIZED_ERROR
import com.common.utils.IntegrationTestsUtils
import com.common.utils.IntegrationTestsUtils.createAuthenticationHeader
import com.dash.app.controller.requests.calendarwidget.CalendarUrlPayload
import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.parsing.Parser
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.restclient.test.autoconfigure.AutoConfigureMockRestServiceServer
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockRestServiceServer
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CalendarWidgetControllerTests {
    @LocalServerPort
    private val port: Int = 0

    @Autowired
    private lateinit var mockServer: MockRestServiceServer

    private lateinit var jwtToken: String

    private val calendarWidgetEndpoint = "/calendarWidget/"

    private val mockedCalendarDataResponse =
        """
        BEGIN:VCALENDAR
        PRODID:-//Google Inc//Google Calendar 70.9054//EN
        VERSION:2.0
        CALSCALE:GREGORIAN
        METHOD:PUBLISH
        X-WR-CALNAME:Jours fériés en France
        X-WR-TIMEZONE:UTC
        X-WR-CALDESC:Jours fériés et fêtes légales en France
        BEGIN:VEVENT
        DTSTART;VALUE=DATE:20221101
        DTEND;VALUE=DATE:20221102
        DTSTAMP:20220319T185542Z
        UID:20221101_2c3kflc4jarsvbht100d2j89fg@google.com
        CLASS:PUBLIC
        CREATED:20210826T084241Z
        DESCRIPTION:Jour férié
        LAST-MODIFIED:20210826T084241Z
        SEQUENCE:0
        STATUS:CONFIRMED
        SUMMARY:La Toussaint
        TRANSP:TRANSPARENT
        END:VEVENT
        BEGIN:VEVENT
        DTSTART;VALUE=DATE:20210523
        DTEND;VALUE=DATE:20210524
        DTSTAMP:20220319T185542Z
        UID:20210523_a09of6mjan8vo7va1up7e8rqds@google.com
        CLASS:PUBLIC
        CREATED:20210826T084239Z
        DESCRIPTION:Journée d''observance
        LAST-MODIFIED:20210826T084239Z
        SEQUENCE:0
        STATUS:CONFIRMED
        SUMMARY:Pentecôte
        TRANSP:TRANSPARENT
        END:VEVENT
        BEGIN:VEVENT
        DTSTART;VALUE=DATE:20221225
        DTEND;VALUE=DATE:20221226
        DTSTAMP:20220319T185542Z
        UID:20221225_5de512b1og8l97mmk19iatvbhk@google.com
        CLASS:PUBLIC
        CREATED:20210826T084239Z
        DESCRIPTION:Jour férié
        LAST-MODIFIED:20210826T084239Z
        SEQUENCE:0
        STATUS:CONFIRMED
        SUMMARY:Noël
        TRANSP:TRANSPARENT
        END:VEVENT
        BEGIN:VEVENT
        DTSTART;VALUE=DATE:20210508
        DTEND;VALUE=DATE:20210509
        DTSTAMP:20220319T185542Z
        UID:20210508_8bk7t632v4ejuo8qn09oo47hlo@google.com
        CLASS:PUBLIC
        CREATED:20210826T084233Z
        DESCRIPTION:Jour férié
        LAST-MODIFIED:20210826T084233Z
        SEQUENCE:0
        STATUS:CONFIRMED
        SUMMARY:Fête de la Victoire 1945
        TRANSP:TRANSPARENT
        END:VEVENT
        BEGIN:VEVENT
        DTSTAMP:20220319T185542Z
        UID:20210508_8bk7t632v4ejuo8qn09oo47hlo@google.com
        CLASS:PUBLIC
        CREATED:20210826T084233Z
        DESCRIPTION:Null
        LAST-MODIFIED:20210826T084233Z
        SEQUENCE:0
        STATUS:CONFIRMED
        SUMMARY:Empty event
        TRANSP:TRANSPARENT
        END:VEVENT
        BEGIN:VEVENT
        DTEND;VALUE=DATE:20210509
        DTSTAMP:20220319T185542Z
        UID:20210508_8bk7t632v4ejuo8qn09oo47hlo@google.com
        CLASS:PUBLIC
        CREATED:20210826T084233Z
        DESCRIPTION:Null
        LAST-MODIFIED:20210826T084233Z
        SEQUENCE:0
        STATUS:CONFIRMED
        SUMMARY:Empty event
        TRANSP:TRANSPARENT
        END:VEVENT
        BEGIN:VEVENT
        DTSTART;VALUE=DATE:20210509
        DTSTAMP:20220319T185542Z
        UID:20210508_8bk7t632v4ejuo8qn09oo47hlo@google.com
        CLASS:PUBLIC
        CREATED:20210826T084233Z
        DESCRIPTION:Null
        LAST-MODIFIED:20210826T084233Z
        SEQUENCE:0
        STATUS:CONFIRMED
        SUMMARY:Empty event
        TRANSP:TRANSPARENT
        END:VEVENT
        END:VCALENDAR
        """.trimIndent()

    @BeforeAll
    fun setup() {
        RestAssured.defaultParser = Parser.JSON
        jwtToken = IntegrationTestsUtils.authenticateAdminRole(port).accessToken
    }

    @Test
    fun testGetCalendarData() {
        val calendarUrl =
            "https://calendar.google.com/calendar/ical/fr.french%23holiday%40group.v.calendar.google.com/public/basic.ics"

        mockServer
            .expect(requestTo(calendarUrl))
            .andRespond(withSuccess(mockedCalendarDataResponse, MediaType.TEXT_PLAIN))

        val getCalendarDataResponse =
            Given {
                port(port)
                    .contentType(ContentType.JSON)
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .body(CalendarUrlPayload(calendarUrl))
                    .header(createAuthenticationHeader(jwtToken))
            } When {
                post(calendarWidgetEndpoint)
            } Then {
                statusCode(200)
                    .contentType(ContentType.JSON)
            } Extract {
                `as`(List::class.java)
            }

        assertEquals(getCalendarDataResponse.size, 4)
    }

    @Test
    fun testGetCalendarDataNullResponse() {
        val calendarUrl = "http://wrong_calendar_url.com"

        mockServer
            .expect(requestTo(calendarUrl))
            .andRespond(withSuccess())

        Given {
            port(port)
                .contentType(ContentType.JSON)
                .body(CalendarUrlPayload(calendarUrl))
                .header(createAuthenticationHeader(jwtToken))
        } When {
            post(calendarWidgetEndpoint)
        } Then {
            log()
                .all()
                .statusCode(404)
        }
    }

    @Test
    fun testEndpointNotAuthenticated() {
        Given {
            port(port)
        } When {
            post(calendarWidgetEndpoint)
        } Then {
            log()
                .all()
                .statusCode(401)
                .log()
                .all()
                .body("error", equalTo(UNAUTHORIZED_ERROR))
        }
    }
}
