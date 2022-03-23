package com.dash.controller

import com.dash.controller.requests.CalendarUrlPayload
import com.dash.utils.Constants.UNAUTHORIZED_ERROR
import com.dash.utils.IntegrationTestsUtils
import com.dash.utils.TestEndpointsArguments
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import io.restassured.http.Header
import io.restassured.parsing.Parser
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.client.ExpectedCount
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.method
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withStatus
import org.springframework.web.client.RestTemplate
import java.net.URI
import java.util.stream.Stream

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CalendarWidgetControllerTests {

    @LocalServerPort
    private val port: Int = 0

    private lateinit var mockServer: MockRestServiceServer

    @Autowired
    private lateinit var restTemplate: RestTemplate

    private lateinit var jwtToken: String

    private val calendarWidgetEndpoint = "/calendarWidget/"

    private val mockedCalendarDataResponse = "BEGIN:VCALENDAR\n" +
        "PRODID:-//Google Inc//Google Calendar 70.9054//EN\n" +
        "VERSION:2.0\n" +
        "CALSCALE:GREGORIAN\n" +
        "METHOD:PUBLISH\n" +
        "X-WR-CALNAME:Jours fériés en France\n" +
        "X-WR-TIMEZONE:UTC\n" +
        "X-WR-CALDESC:Jours fériés et fêtes légales en France\n" +
        "BEGIN:VEVENT\n" +
        "DTSTART;VALUE=DATE:20221101\n" +
        "DTEND;VALUE=DATE:20221102\n" +
        "DTSTAMP:20220319T185542Z\n" +
        "UID:20221101_2c3kflc4jarsvbht100d2j89fg@google.com\n" +
        "CLASS:PUBLIC\n" +
        "CREATED:20210826T084241Z\n" +
        "DESCRIPTION:Jour férié\n" +
        "LAST-MODIFIED:20210826T084241Z\n" +
        "SEQUENCE:0\n" +
        "STATUS:CONFIRMED\n" +
        "SUMMARY:La Toussaint\n" +
        "TRANSP:TRANSPARENT\n" +
        "END:VEVENT\n" +
        "BEGIN:VEVENT\n" +
        "DTSTART;VALUE=DATE:20210523\n" +
        "DTEND;VALUE=DATE:20210524\n" +
        "DTSTAMP:20220319T185542Z\n" +
        "UID:20210523_a09of6mjan8vo7va1up7e8rqds@google.com\n" +
        "CLASS:PUBLIC\n" +
        "CREATED:20210826T084239Z\n" +
        "DESCRIPTION:Journée d''observance\n" +
        "LAST-MODIFIED:20210826T084239Z\n" +
        "SEQUENCE:0\n" +
        "STATUS:CONFIRMED\n" +
        "SUMMARY:Pentecôte\n" +
        "TRANSP:TRANSPARENT\n" +
        "END:VEVENT\n" +
        "BEGIN:VEVENT\n" +
        "DTSTART;VALUE=DATE:20221225\n" +
        "DTEND;VALUE=DATE:20221226\n" +
        "DTSTAMP:20220319T185542Z\n" +
        "UID:20221225_5de512b1og8l97mmk19iatvbhk@google.com\n" +
        "CLASS:PUBLIC\n" +
        "CREATED:20210826T084239Z\n" +
        "DESCRIPTION:Jour férié\n" +
        "LAST-MODIFIED:20210826T084239Z\n" +
        "SEQUENCE:0\n" +
        "STATUS:CONFIRMED\n" +
        "SUMMARY:Noël\n" +
        "TRANSP:TRANSPARENT\n" +
        "END:VEVENT\n" +
        "BEGIN:VEVENT\n" +
        "DTSTART;VALUE=DATE:20210508\n" +
        "DTEND;VALUE=DATE:20210509\n" +
        "DTSTAMP:20220319T185542Z\n" +
        "UID:20210508_8bk7t632v4ejuo8qn09oo47hlo@google.com\n" +
        "CLASS:PUBLIC\n" +
        "CREATED:20210826T084233Z\n" +
        "DESCRIPTION:Jour férié\n" +
        "LAST-MODIFIED:20210826T084233Z\n" +
        "SEQUENCE:0\n" +
        "STATUS:CONFIRMED\n" +
        "SUMMARY:Fête de la Victoire 1945\n" +
        "TRANSP:TRANSPARENT\n" +
        "END:VEVENT\n" +
        "END:VCALENDAR\n"

    @BeforeAll
    fun setup() {
        RestAssured.defaultParser = Parser.JSON
        jwtToken = IntegrationTestsUtils.authenticateAdmin(port).accessToken
        mockServer = MockRestServiceServer.createServer(restTemplate)
    }

    @BeforeEach
    fun resetMockServer() {
        mockServer.reset()
    }

    @Test
    fun testGetCalendarData() {
        val calendarUrl = "https://calendar.google.com/calendar/ical/fr.french%23holiday%40group.v.calendar.google.com/public/basic.ics"

        mockServer.expect(
            ExpectedCount.once(), requestTo(URI(calendarUrl))
        )
            .andExpect(method(HttpMethod.GET))
            .andRespond(
                withStatus(HttpStatus.OK)
                    .body(mockedCalendarDataResponse)
            )

        val getCalendarDataResponse = given()
            .port(port)
            .contentType(ContentType.JSON)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .body(CalendarUrlPayload(calendarUrl))
            .header(Header("Authorization", "Bearer $jwtToken"))
            .`when`()
            .post(calendarWidgetEndpoint)
            .then().log().all()
            .statusCode(200)
            .log().all()
            .extract()
            .jsonPath()

        assertEquals(7, getCalendarDataResponse.getList<Any>("properties").size)

        mockServer.verify()
    }

    @Test
    fun testGetCalendarDataNullResponse() {
        val calendarUrl = "http://wrong_calendar_url.com"

        mockServer.expect(
            ExpectedCount.once(), requestTo(URI(calendarUrl))
        )
            .andExpect(method(HttpMethod.GET))
            .andRespond(
                withStatus(HttpStatus.OK)
            )

        given()
            .port(port)
            .contentType(ContentType.JSON)
            .body(CalendarUrlPayload(calendarUrl))
            .header(Header("Authorization", "Bearer $jwtToken"))
            .`when`()
            .post(calendarWidgetEndpoint)
            .then().log().all()
            .statusCode(200)
            .body(equalTo(""))
            .log().all()

        mockServer.verify()
    }

    @ParameterizedTest
    @MethodSource("testGetCalendarDataErrorCodes")
    fun testGetCalendarDataErrorCodes(urlStatusCodeResponse: HttpStatus, expectedStatusCode: Int) {
        val url = "http://testwrongurl.com"

        mockServer.expect(
            ExpectedCount.once(), requestTo(URI(url))
        )
            .andExpect(method(HttpMethod.GET))
            .andRespond(
                withStatus(urlStatusCodeResponse)
            )

        given().port(port)
            .header(Header("Authorization", "Bearer $jwtToken"))
            .contentType(ContentType.JSON)
            .body(CalendarUrlPayload(url))
            .`when`()
            .post(calendarWidgetEndpoint)
            .then().log().all()
            .statusCode(expectedStatusCode)
            .log().all()
    }

    @Test
    fun testEndpointNotAuthenticated() {
        given().port(port)
            .`when`()
            .post(calendarWidgetEndpoint)
            .then().log().all()
            .statusCode(401)
            .log().all()
            .body("error", equalTo(UNAUTHORIZED_ERROR))
    }

    fun testGetCalendarDataErrorCodes(): Stream<Arguments> = TestEndpointsArguments.testForeignApiCodes()
}
