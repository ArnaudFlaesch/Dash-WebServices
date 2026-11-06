package com.dash.infra.apimodel.strava

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import tools.jackson.databind.json.JsonMapper
import tools.jackson.module.kotlin.KotlinModule
import tools.jackson.module.kotlin.readValue
import java.time.LocalDate

/**
 * The real Strava API returns JSON null for many fields (athletes without a city, manual activities, ...),
 * which the Strava mock server data never contains.
 */
class StravaResponseDeserializationTests {
    private val jsonMapper = JsonMapper.builder().addModule(KotlinModule.Builder().build()).build()

    private val athleteWithNullsJson =
        """
        {
          "id": 134815,
          "username": null,
          "resource_state": 2,
          "firstname": "Jane",
          "lastname": "Doe",
          "bio": null,
          "city": null,
          "state": null,
          "country": null,
          "sex": null,
          "premium": false,
          "summit": false,
          "created_at": "2018-03-08T10:02:34Z",
          "updated_at": "2024-05-12T18:10:11Z",
          "badge_type_id": 0,
          "weight": null,
          "profile_medium": "avatar/athlete/medium.png",
          "profile": "avatar/athlete/large.png",
          "friend": null,
          "follower": null
        }
        """.trimIndent()

    @Test
    fun shouldDeserializeAthleteWithNullFields() {
        val athlete = jsonMapper.readValue<StravaAthleteResponse>(athleteWithNullsJson).toDomain()

        assertEquals(134815, athlete.id)
        assertEquals("Jane", athlete.firstname)
        assertEquals("", athlete.username)
        assertEquals("", athlete.city)
        assertEquals("", athlete.state)
        assertEquals("", athlete.country)
        assertEquals("", athlete.sex)
        assertEquals(LocalDate.of(2018, 3, 8), athlete.createdAt)
        assertEquals("avatar/athlete/large.png", athlete.profile)
    }

    @Test
    fun shouldDeserializeTokenResponseWithAthleteNullFields() {
        val json =
            """
            {
              "token_type": "Bearer",
              "expires_at": 1730826000,
              "expires_in": 21600,
              "refresh_token": "e5n567567",
              "access_token": "a4b945687g",
              "athlete": $athleteWithNullsJson
            }
            """.trimIndent()

        val token = jsonMapper.readValue<StravaTokenDataResponse>(json).toDomain()

        assertEquals("a4b945687g", token.accessToken)
        assertEquals("e5n567567", token.refreshToken)
        assertEquals("1730826000", token.expiresAt)
        assertEquals("Jane", token.athlete.firstname)
        assertEquals("", token.athlete.username)
    }

    @Test
    fun shouldDeserializeRefreshTokenResponseWithoutAthlete() {
        val json =
            """
            {
              "token_type": "Bearer",
              "access_token": "a9b723",
              "expires_at": 1568775134,
              "expires_in": 20566,
              "refresh_token": "b5c569"
            }
            """.trimIndent()

        val token = jsonMapper.readValue<StravaTokenDataResponse>(json).toDomain()

        assertEquals("a9b723", token.accessToken)
        assertEquals("b5c569", token.refreshToken)
    }

    @Test
    fun shouldDeserializeManualActivityWithNullFields() {
        val json =
            """
            [{
              "resource_state": 2,
              "athlete": { "id": 134815, "resource_state": 1 },
              "name": "Yoga",
              "distance": 0.0,
              "moving_time": 3600,
              "elapsed_time": 3600,
              "total_elevation_gain": 0,
              "type": "Yoga",
              "sport_type": "Yoga",
              "workout_type": null,
              "id": 12345678987654321,
              "start_date": "2024-11-04T17:00:00Z",
              "start_date_local": "2024-11-04T18:00:00Z",
              "timezone": "(GMT+01:00) Europe/Paris",
              "utc_offset": 3600.0,
              "location_city": null,
              "location_state": null,
              "location_country": null,
              "achievement_count": 0,
              "kudos_count": 0,
              "comment_count": 0,
              "athlete_count": 1,
              "photo_count": 0,
              "map": { "id": "a12345678987654321", "summary_polyline": null, "resource_state": 2 },
              "trainer": false,
              "commute": false,
              "manual": true,
              "private": false,
              "visibility": "everyone",
              "flagged": false,
              "gear_id": null,
              "start_latlng": [],
              "end_latlng": [],
              "average_speed": 0.0,
              "max_speed": 0,
              "has_heartrate": false,
              "heartrate_opt_out": false,
              "display_hide_heartrate_option": false,
              "upload_id": null,
              "upload_id_str": null,
              "external_id": null,
              "from_accepted_tag": false,
              "pr_count": 0,
              "total_photo_count": 0,
              "has_kudoed": false
            }]
            """.trimIndent()

        val activity = jsonMapper.readValue<List<StravaActivityResponse>>(json).single().toDomain()

        assertEquals("Yoga", activity.name)
        assertEquals(0, activity.workoutType)
        assertEquals(0L, activity.uploadId)
        assertEquals("", activity.uploadIdStr)
        assertNull(activity.externalId)
        assertNull(activity.locationCity)
        assertEquals("", activity.map.summaryPolyline)
        assertEquals(0, activity.startLatlng.size)
    }

    @Test
    fun shouldDeserializeActivityWithoutHeartRateNorElevation() {
        val json =
            """
            [{
              "resource_state": 2,
              "athlete": { "id": 134815, "resource_state": 1 },
              "name": "Evening Run",
              "distance": 5012.3,
              "moving_time": 1650,
              "elapsed_time": 1702,
              "total_elevation_gain": 0,
              "type": "Run",
              "sport_type": "Run",
              "workout_type": 0,
              "id": 154504250376823,
              "external_id": "garmin_push_12345",
              "upload_id": 987654321123,
              "upload_id_str": "987654321123",
              "start_date": "2024-11-04T17:00:00Z",
              "start_date_local": "2024-11-04T18:00:00Z",
              "timezone": "(GMT+01:00) Europe/Paris",
              "utc_offset": 3600,
              "start_latlng": [48.8566, 2.3522],
              "end_latlng": [48.8601, 2.3412],
              "location_city": null,
              "location_state": null,
              "location_country": "France",
              "achievement_count": 2,
              "kudos_count": 3,
              "comment_count": 0,
              "athlete_count": 1,
              "photo_count": 0,
              "map": { "id": "a154504250376823", "summary_polyline": "ki{eFvqfiVqAWQIGEEKAYJgBVqDJ{BHa@", "resource_state": 2 },
              "trainer": false,
              "commute": false,
              "manual": false,
              "private": false,
              "visibility": "followers_only",
              "flagged": false,
              "gear_id": null,
              "from_accepted_tag": false,
              "average_speed": 3.038,
              "max_speed": 4.2,
              "has_heartrate": false,
              "heartrate_opt_out": false,
              "display_hide_heartrate_option": false,
              "pr_count": 1,
              "total_photo_count": 0,
              "has_kudoed": false
            }]
            """.trimIndent()

        val activity = jsonMapper.readValue<List<StravaActivityResponse>>(json).single().toDomain()

        assertEquals("Evening Run", activity.name)
        assertEquals(5012.3f, activity.distance)
        assertEquals(0f, activity.elevHigh)
        assertEquals(0f, activity.elevLow)
        assertEquals(154504250376823L, activity.id)
        assertEquals(987654321123L, activity.uploadId)
        assertEquals("987654321123", activity.uploadIdStr)
        assertEquals(listOf(48.8566, 2.3522), activity.startLatlng)
        assertEquals("ki{eFvqfiVqAWQIGEEKAYJgBVqDJ{BHa@", activity.map.summaryPolyline)
    }

    @Test
    fun shouldDefaultTokenResponseWithAllNullFields() {
        val json =
            """
            {
              "access_token": null,
              "refresh_token": null,
              "expires_at": null,
              "athlete": null
            }
            """.trimIndent()

        val token = jsonMapper.readValue<StravaTokenDataResponse>(json).toDomain()

        assertEquals("", token.accessToken)
        assertEquals("", token.refreshToken)
        assertEquals("", token.expiresAt)
        assertEquals(0L, token.athlete.id)
        assertEquals("", token.athlete.firstname)
    }

    @Test
    fun shouldDefaultAthleteNameAndProfileNullFields() {
        val json =
            """
            {
              "id": 134815,
              "resource_state": 1,
              "firstname": null,
              "lastname": null,
              "profile_medium": null,
              "profile": null
            }
            """.trimIndent()

        val athlete = jsonMapper.readValue<StravaAthleteResponse>(json).toDomain()

        assertEquals("", athlete.firstname)
        assertEquals("", athlete.lastname)
        assertEquals("", athlete.profileMedium)
        assertEquals("", athlete.profile)
    }

    @Test
    fun shouldDefaultActivityNullFields() {
        val json =
            """
            [{
              "id": 154504250376823,
              "name": null,
              "type": null,
              "timezone": null,
              "start_latlng": null,
              "end_latlng": null,
              "visibility": null,
              "map": { "id": null, "summary_polyline": null, "resource_state": 1 }
            }]
            """.trimIndent()

        val activity = jsonMapper.readValue<List<StravaActivityResponse>>(json).single().toDomain()

        assertEquals("", activity.name)
        assertEquals("", activity.type)
        assertEquals("", activity.timezone)
        assertEquals(listOf(0.0, 0.0), activity.startLatlng)
        assertEquals(listOf(0.0, 0.0), activity.endLatlng)
        assertEquals("", activity.visibility)
        assertEquals("", activity.map.id)
    }
}
