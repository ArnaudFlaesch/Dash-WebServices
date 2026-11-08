package com.dash.dashboard.adapter.inbound.web

import com.dash.dashboard.adapter.inbound.web.request.workout.AddWorkoutTypeRequest
import com.dash.dashboard.adapter.inbound.web.request.workout.CreateWorkoutSessionRequest
import com.dash.dashboard.adapter.inbound.web.request.workout.UpdateWorkoutExerciseRequest
import com.dash.dashboard.adapter.outbound.persistence.workout.WorkoutExerciseRepository
import com.dash.dashboard.adapter.outbound.persistence.workout.WorkoutSessionRepository
import com.dash.dashboard.adapter.outbound.persistence.workout.WorkoutTypeRepository
import com.dash.dashboard.domain.model.workout.*
import com.dash.utils.IntegrationTestsUtils
import com.dash.utils.IntegrationTestsUtils.createAuthenticationHeader
import io.restassured.RestAssured
import io.restassured.common.mapper.TypeRef
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.parsing.Parser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WorkoutWidgetControllerTests {
    @LocalServerPort
    private val port: Int = 0

    private lateinit var jwtToken: String

    private val workoutWidgetEndpoint = "/workout"
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
        jwtToken = IntegrationTestsUtils.authenticateAdminRole(port).accessToken
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
        val addWorkoutTypeRequest = AddWorkoutTypeRequest(newWorkoutType)

        val workoutType =
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
                    .contentType(ContentType.JSON)
                    .body(addWorkoutTypeRequest)
            } When {
                post("$workoutWidgetEndpoint/types")
            } Then {
                statusCode(HttpStatus.CREATED.value())
            } Extract {
                `as`(WorkoutType::class.java)
            }

        assertEquals(newWorkoutType, workoutType.name)

        val workoutTypes =
            Given {
                port(port)
                    .param("userId", userId)
                    .header(createAuthenticationHeader(jwtToken))
            } When {
                get("$workoutWidgetEndpoint/types")
            } Then {
                statusCode(HttpStatus.OK.value())
            } Extract {
                `as`(object : TypeRef<List<WorkoutType>>() {})
            }

        assertEquals(1, workoutTypes.size)

        val workoutSessionDate = LocalDate.now()
        val createWorkoutSessionRequest = CreateWorkoutSessionRequest(workoutSessionDate)

        val workoutSession =
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
                    .contentType(ContentType.JSON)
                    .body(createWorkoutSessionRequest)
            } When {
                post("$workoutWidgetEndpoint/sessions")
            } Then {
                statusCode(HttpStatus.CREATED.value())
            } Extract {
                `as`(WorkoutSession::class.java)
            }

        assertEquals(workoutSessionDate, workoutSession.workoutDate)

        val workoutSessions =
            Given {
                port(port)
                    .param("userId", userId)
                    .header(createAuthenticationHeader(jwtToken))
            } When {
                param(
                    "dateIntervalStart",
                    LocalDate.now().minusDays(1).format(DateTimeFormatter.ISO_DATE)
                ).param(
                    "dateIntervalEnd",
                    LocalDate.now().plusDays(1).format(DateTimeFormatter.ISO_DATE)
                ).get("$workoutWidgetEndpoint/sessions")
            } Then {
                statusCode(HttpStatus.OK.value())
            } Extract {
                `as`(object : TypeRef<List<WorkoutSession>>() {})
            }

        assertEquals(1, workoutSessions.size)

        val workoutExerciseRequest = UpdateWorkoutExerciseRequest(5)
        val workoutExercise =
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
                    .contentType(ContentType.JSON)
                    .body(workoutExerciseRequest)
            } When {
                put("$workoutWidgetEndpoint/sessions/${workoutSession.id}/exercises/${workoutType.id}")
            } Then {
                statusCode(HttpStatus.OK.value())
            } Extract {
                `as`(WorkoutExercise::class.java)
            }

        assertEquals(
            WorkoutExercise(workoutSession.id, workoutType.id, 5),
            workoutExercise
        )

        val workoutExercises =
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
            } When {
                get("$workoutWidgetEndpoint/sessions/${workoutSession.id}/exercises")
            } Then {
                statusCode(HttpStatus.OK.value())
            } Extract {
                `as`(object : TypeRef<List<WorkoutExercise>>() {})
            }

        assertEquals(1, workoutExercises.size)

        val workoutStats =
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
                    .param(
                        "dateIntervalStart",
                        LocalDate.now().minusDays(1).format(DateTimeFormatter.ISO_DATE)
                    ).param(
                        "dateIntervalEnd",
                        LocalDate.now().plusDays(1).format(DateTimeFormatter.ISO_DATE)
                    )
            } When {
                get("$workoutWidgetEndpoint/stats/by-period")
            } Then {
                statusCode(HttpStatus.OK.value())
            } Extract {
                `as`(object : TypeRef<List<WorkoutStatsByInterval>>() {})
            }

        assertEquals(1, workoutStats.size)
    }

    @Test
    fun shouldGetWorkoutStatsByYear() {
        val year = 2022
        val newWorkoutType = "Abdos"
        val addWorkoutTypeRequest = AddWorkoutTypeRequest(newWorkoutType)

        val workoutType =
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
                    .contentType(ContentType.JSON)
                    .body(addWorkoutTypeRequest)
            } When {
                post("$workoutWidgetEndpoint/types")
            } Then {
                statusCode(HttpStatus.CREATED.value())
            } Extract {
                `as`(WorkoutType::class.java)
            }

        assertEquals(newWorkoutType, workoutType.name)

        val workoutSessionDate = LocalDate.of(year, 5, 1)
        val createWorkoutSessionRequest = CreateWorkoutSessionRequest(workoutSessionDate)

        val workoutSession =
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
                    .contentType(ContentType.JSON)
                    .body(createWorkoutSessionRequest)
            } When {
                post("$workoutWidgetEndpoint/sessions")
            } Then {
                statusCode(HttpStatus.CREATED.value())
            } Extract {
                `as`(WorkoutSession::class.java)
            }

        assertEquals(workoutSessionDate, workoutSession.workoutDate)

        val workoutExerciseRequest = UpdateWorkoutExerciseRequest(5)
        val workoutExercise =
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
                    .contentType(ContentType.JSON)
                    .body(workoutExerciseRequest)
            } When {
                put("$workoutWidgetEndpoint/sessions/${workoutSession.id}/exercises/${workoutType.id}")
            } Then {
                statusCode(HttpStatus.OK.value())
            } Extract {
                `as`(WorkoutExercise::class.java)
            }

        assertEquals(
            WorkoutExercise(workoutSession.id, workoutType.id, 5),
            workoutExercise
        )

        val workoutStats =
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
                    .param(
                        "dateIntervalStart",
                        workoutSessionDate.minusMonths(1).format(DateTimeFormatter.ISO_DATE)
                    ).param(
                        "dateIntervalEnd",
                        workoutSessionDate.plusMonths(1).format(DateTimeFormatter.ISO_DATE)
                    )
            } When {
                get("$workoutWidgetEndpoint/stats/by-month")
            } Then {
                statusCode(HttpStatus.OK.value())
            } Extract {
                `as`(object : TypeRef<List<WorkoutStatsByMonth>>() {})
            }

        assertEquals(1, workoutStats.size)
        assertEquals("Abdos", workoutStats[0].workoutTypeName)
        assertEquals(5, workoutStats[0].totalNumberOfReps)
    }
}
