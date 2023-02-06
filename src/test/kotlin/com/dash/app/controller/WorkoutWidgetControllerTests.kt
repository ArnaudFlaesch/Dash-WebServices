package com.dash.app.controller

import com.common.utils.AbstractIT
import com.common.utils.IntegrationTestsUtils
import com.common.utils.IntegrationTestsUtils.createAuthenticationHeader
import com.dash.app.controller.requests.workoutWidget.AddWorkoutTypePayload
import com.dash.app.controller.requests.workoutWidget.CreateWorkoutSessionPayload
import com.dash.app.controller.requests.workoutWidget.UpdateWorkoutExercisePayload
import com.dash.domain.model.workoutwidget.*
import com.dash.infra.repository.WorkoutExerciseRepository
import com.dash.infra.repository.WorkoutSessionRepository
import com.dash.infra.repository.WorkoutTypeRepository
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.common.mapper.TypeRef
import io.restassured.http.ContentType
import io.restassured.parsing.Parser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WorkoutWidgetControllerTests : AbstractIT() {

    @LocalServerPort
    private val port: Int = 0

    private lateinit var jwtToken: String

    private val workoutWidgetEndpoint = "/workoutWidget"
    private val userId = 1

    @Autowired
    private lateinit var workoutTypeRepository: WorkoutTypeRepository

    @Autowired
    private lateinit var workoutExerciseRepository: WorkoutExerciseRepository

    @Autowired
    private lateinit var workoutSessionRepository: WorkoutSessionRepository

    @BeforeAll
    fun setup() {
        RestAssured.defaultParser = Parser.JSON
        jwtToken = IntegrationTestsUtils.authenticateAdmin(port).accessToken
    }

    @BeforeEach
    fun tearDown() {
        workoutExerciseRepository.deleteAll()
        workoutSessionRepository.deleteAll()
        workoutTypeRepository.deleteAll()
    }

    @Test
    fun createWorkoutSessionTest() {
        val newWorkoutType = "Abdos"
        val addWorkoutTypePayload = AddWorkoutTypePayload(newWorkoutType)

        val workoutType = given()
            .port(port)
            .header(createAuthenticationHeader(jwtToken))
            .contentType(ContentType.JSON)
            .body(addWorkoutTypePayload)
            .`when`()
            .post("$workoutWidgetEndpoint/addWorkoutType")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .log().all()
            .extract().`as`(WorkoutTypeDomain::class.java)

        assertEquals(newWorkoutType, workoutType.name)

        val workoutTypes = given()
            .port(port)
            .param("userId", userId)
            .header(createAuthenticationHeader(jwtToken))
            .`when`()
            .get("$workoutWidgetEndpoint/workoutTypes")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .log().all()
            .extract().`as`(object : TypeRef<List<WorkoutTypeDomain>>() {})

        assertEquals(1, workoutTypes.size)

        val workoutSessionDate = LocalDate.now()
        val createWorkoutSessionPayload = CreateWorkoutSessionPayload(workoutSessionDate)

        val workoutSession = given()
            .port(port)
            .header(createAuthenticationHeader(jwtToken))
            .contentType(ContentType.JSON)
            .body(createWorkoutSessionPayload)
            .`when`()
            .post("$workoutWidgetEndpoint/createWorkoutSession")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract().`as`(WorkoutSessionDomain::class.java)

        assertEquals(workoutSessionDate, workoutSession.workoutDate)

        val workoutSessions = given()
            .port(port)
            .param("userId", userId)
            .header(createAuthenticationHeader(jwtToken))
            .`when`()
            .param("dateIntervalStart", LocalDate.now().minusDays(1).format(DateTimeFormatter.ISO_DATE))
            .param("dateIntervalEnd", LocalDate.now().plusDays(1).format(DateTimeFormatter.ISO_DATE))
            .get("$workoutWidgetEndpoint/workoutSessions")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .log().all()
            .extract().`as`(object : TypeRef<List<WorkoutSessionDomain>>() {})

        assertEquals(1, workoutSessions.size)

        val workoutExercisePayload = UpdateWorkoutExercisePayload(workoutSession.id, workoutType.id, 5)
        val workoutExercise = given()
            .port(port)
            .header(createAuthenticationHeader(jwtToken))
            .contentType(ContentType.JSON)
            .body(workoutExercisePayload)
            .`when`()
            .post("$workoutWidgetEndpoint/updateWorkoutExercise")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract().`as`(WorkoutExerciseDomain::class.java)

        assertEquals(
            WorkoutExerciseDomain(workoutSession.id, workoutType.id, 5),
            workoutExercise
        )

        val workoutExercises = given()
            .port(port)
            .header(createAuthenticationHeader(jwtToken))
            .param("workoutSessionId", workoutSession.id)
            .`when`()
            .get("$workoutWidgetEndpoint/workoutExercises")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .log().all()
            .extract().`as`(object : TypeRef<List<WorkoutExerciseDomain>>() {})

        assertEquals(1, workoutExercises.size)

        val workoutStats = given()
            .port(port)
            .header(createAuthenticationHeader(jwtToken))
            .param("dateIntervalStart", LocalDate.now().minusDays(1).format(DateTimeFormatter.ISO_DATE))
            .param("dateIntervalEnd", LocalDate.now().plusDays(1).format(DateTimeFormatter.ISO_DATE))
            .`when`()
            .get("$workoutWidgetEndpoint/workoutStatsByPeriod")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .log().all()
            .extract().`as`(object : TypeRef<List<WorkoutStatsByIntervalDomain>>() {})

        assertEquals(1, workoutStats.size)
    }

    @Test
    fun shouldGetWorkoutStatsByYear() {
        val year = 2022
        val newWorkoutType = "Abdos"
        val addWorkoutTypePayload = AddWorkoutTypePayload(newWorkoutType)

        val workoutType = given()
            .port(port)
            .header(createAuthenticationHeader(jwtToken))
            .contentType(ContentType.JSON)
            .body(addWorkoutTypePayload)
            .`when`()
            .post("$workoutWidgetEndpoint/addWorkoutType")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .log().all()
            .extract().`as`(WorkoutTypeDomain::class.java)

        assertEquals(newWorkoutType, workoutType.name)

        val workoutSessionDate = LocalDate.of(year, 5, 1)
        val createWorkoutSessionPayload = CreateWorkoutSessionPayload(workoutSessionDate)

        val workoutSession = given()
            .port(port)
            .header(createAuthenticationHeader(jwtToken))
            .contentType(ContentType.JSON)
            .body(createWorkoutSessionPayload)
            .`when`()
            .post("$workoutWidgetEndpoint/createWorkoutSession")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract().`as`(WorkoutSessionDomain::class.java)

        assertEquals(workoutSessionDate, workoutSession.workoutDate)

        val workoutExercisePayload = UpdateWorkoutExercisePayload(workoutSession.id, workoutType.id, 5)
        val workoutExercise = given()
            .port(port)
            .header(createAuthenticationHeader(jwtToken))
            .contentType(ContentType.JSON)
            .body(workoutExercisePayload)
            .`when`()
            .post("$workoutWidgetEndpoint/updateWorkoutExercise")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract().`as`(WorkoutExerciseDomain::class.java)

        assertEquals(
            WorkoutExerciseDomain(workoutSession.id, workoutType.id, 5),
            workoutExercise
        )

        val workoutStats = given()
            .port(port)
            .header(createAuthenticationHeader(jwtToken))
            .param("dateIntervalStart", LocalDate.now().minusDays(1).format(DateTimeFormatter.ISO_DATE))
            .param("dateIntervalEnd", LocalDate.now().plusDays(1).format(DateTimeFormatter.ISO_DATE))
            .`when`()
            .get("$workoutWidgetEndpoint/workoutStatsByYear")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .log().all()
            .extract().`as`(object : TypeRef<List<WorkoutStatsByMonthDomain>>() {})

        assertEquals(1, workoutStats.size)
        assertEquals("Abdos", workoutStats[0].workoutTypeName)
        assertEquals(5, workoutStats[0].totalNumberOfReps)
    }
}
