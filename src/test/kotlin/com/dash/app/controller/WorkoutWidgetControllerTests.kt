package com.dash.app.controller

import com.common.utils.AbstractIT
import com.common.utils.IntegrationTestsUtils
import com.common.utils.IntegrationTestsUtils.createAuthenticationHeader
import com.dash.app.controller.requests.workoutWidget.AddWorkoutTypePayload
import com.dash.app.controller.requests.workoutWidget.CreateWorkoutSessionPayload
import com.dash.app.controller.requests.workoutWidget.UpdateWorkoutExercisePayload
import com.dash.domain.model.workoutwidget.WorkoutExerciseDomain
import com.dash.domain.model.workoutwidget.WorkoutSessionDomain
import com.dash.domain.model.workoutwidget.WorkoutTypeDomain
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.common.mapper.TypeRef
import io.restassured.http.ContentType
import io.restassured.parsing.Parser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WorkoutWidgetControllerTests : AbstractIT() {

    @LocalServerPort
    private val port: Int = 0

    private lateinit var jwtToken: String

    private val workoutWidgetEndpoint = "/workoutWidget"

    private val userId = 1

    @BeforeAll
    fun setup() {
        RestAssured.defaultParser = Parser.JSON
        jwtToken = IntegrationTestsUtils.authenticateAdmin(port).accessToken
    }

    @Test
    fun createWorkoutSessionTest() {
        val newWorkoutType = "Abdos"
        val addWorkoutTypePayload = AddWorkoutTypePayload(newWorkoutType, userId)

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
        val createWorkoutSessionPayload = CreateWorkoutSessionPayload(workoutSessionDate, userId)

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
        // @TODO
        // assertEquals(workoutSessionDate, workoutExercises[0].workoutSession?.workoutDate ?: fail())
        // assertEquals(newWorkoutType, workoutExercises[0].workoutType?.name ?: fail())
    }
}
