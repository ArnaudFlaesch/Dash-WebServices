package com.dash.infra.api.response

object OpenWeatherApiResponse {

    val weatherApiResponse = """
        {
          "coord": {
            "lon": 2.3488,
            "lat": 48.8534
          },
          "weather": [
            {
              "id": 800,
              "main": "Clear",
              "description": "ciel dégagé",
              "icon": "01d"
            }
          ],
          "base": "stations",
          "main": {
            "temp": 23.5,
            "feels_like": 23.37,
            "temp_min": 22.82,
            "temp_max": 24.43,
            "pressure": 1016,
            "humidity": 56
          },
          "visibility": 10000,
          "wind": {
            "speed": 5.14,
            "deg": 150
          },
          "clouds": {
            "all": 0
          },
          "dt": 1667051402,
          "sys": {
            "type": 2,
            "id": 2041230,
            "country": "FR",
            "sunrise": 1667025137,
            "sunset": 1667061371
          },
          "timezone": 7200,
          "id": 2988507,
          "name": "Paris",
          "cod": 200
        }
    """.trimIndent()

    val forecastApiResponse = """
        {
          "cod": "200",
          "message": 0,
          "cnt": 40,
          "list": [
            {
              "dt": 1667055600,
              "main": {
                "temp": 23.48,
                "feels_like": 23.35,
                "temp_min": 23.48,
                "temp_max": 25.08,
                "pressure": 1016,
                "sea_level": 1016,
                "grnd_level": 1011,
                "humidity": 56,
                "temp_kf": -1.6
              },
              "weather": [
                {
                  "id": 800,
                  "main": "Clear",
                  "description": "ciel dégagé",
                  "icon": "01d"
                }
              ],
              "clouds": {
                "all": 0
              },
              "wind": {
                "speed": 3.54,
                "deg": 189,
                "gust": 9.39
              },
              "visibility": 10000,
              "pop": 0,
              "sys": {
                "pod": "d"
              },
              "dt_txt": "2022-10-29 15:00:00"
            },
            {
              "dt": 1667066400,
              "main": {
                "temp": 22.84,
                "feels_like": 22.51,
                "temp_min": 21.55,
                "temp_max": 22.84,
                "pressure": 1016,
                "sea_level": 1016,
                "grnd_level": 1011,
                "humidity": 51,
                "temp_kf": 1.29
              },
              "weather": [
                {
                  "id": 802,
                  "main": "Clouds",
                  "description": "partiellement nuageux",
                  "icon": "03n"
                }
              ],
              "clouds": {
                "all": 33
              },
              "wind": {
                "speed": 2.74,
                "deg": 176,
                "gust": 8.73
              },
              "visibility": 10000,
              "pop": 0,
              "sys": {
                "pod": "n"
              },
              "dt_txt": "2022-10-29 18:00:00"
            },
            {
              "dt": 1667077200,
              "main": {
                "temp": 21.07,
                "feels_like": 20.62,
                "temp_min": 19.87,
                "temp_max": 21.07,
                "pressure": 1017,
                "sea_level": 1017,
                "grnd_level": 1013,
                "humidity": 53,
                "temp_kf": 1.2
              },
              "weather": [
                {
                  "id": 803,
                  "main": "Clouds",
                  "description": "nuageux",
                  "icon": "04n"
                }
              ],
              "clouds": {
                "all": 64
              },
              "wind": {
                "speed": 2.1,
                "deg": 250,
                "gust": 5.75
              },
              "visibility": 10000,
              "pop": 0,
              "sys": {
                "pod": "n"
              },
              "dt_txt": "2022-10-29 21:00:00"
            },
            {
              "dt": 1667088000,
              "main": {
                "temp": 18.06,
                "feels_like": 17.7,
                "temp_min": 18.06,
                "temp_max": 18.06,
                "pressure": 1017,
                "sea_level": 1017,
                "grnd_level": 1013,
                "humidity": 68,
                "temp_kf": 0
              },
              "weather": [
                {
                  "id": 804,
                  "main": "Clouds",
                  "description": "couvert",
                  "icon": "04n"
                }
              ],
              "clouds": {
                "all": 94
              },
              "wind": {
                "speed": 1.21,
                "deg": 275,
                "gust": 1.71
              },
              "visibility": 10000,
              "pop": 0,
              "sys": {
                "pod": "n"
              },
              "dt_txt": "2022-10-30 00:00:00"
            },
            {
              "dt": 1667098800,
              "main": {
                "temp": 17.43,
                "feels_like": 17.11,
                "temp_min": 17.43,
                "temp_max": 17.43,
                "pressure": 1017,
                "sea_level": 1017,
                "grnd_level": 1012,
                "humidity": 72,
                "temp_kf": 0
              },
              "weather": [
                {
                  "id": 804,
                  "main": "Clouds",
                  "description": "couvert",
                  "icon": "04n"
                }
              ],
              "clouds": {
                "all": 96
              },
              "wind": {
                "speed": 1.44,
                "deg": 150,
                "gust": 1.81
              },
              "visibility": 10000,
              "pop": 0,
              "sys": {
                "pod": "n"
              },
              "dt_txt": "2022-10-30 03:00:00"
            },
            {
              "dt": 1667109600,
              "main": {
                "temp": 16.68,
                "feels_like": 16.34,
                "temp_min": 16.68,
                "temp_max": 16.68,
                "pressure": 1016,
                "sea_level": 1016,
                "grnd_level": 1011,
                "humidity": 74,
                "temp_kf": 0
              },
              "weather": [
                {
                  "id": 804,
                  "main": "Clouds",
                  "description": "couvert",
                  "icon": "04n"
                }
              ],
              "clouds": {
                "all": 98
              },
              "wind": {
                "speed": 1.9,
                "deg": 139,
                "gust": 4.44
              },
              "visibility": 10000,
              "pop": 0,
              "sys": {
                "pod": "n"
              },
              "dt_txt": "2022-10-30 06:00:00"
            },
            {
              "dt": 1667120400,
              "main": {
                "temp": 18.07,
                "feels_like": 17.42,
                "temp_min": 18.07,
                "temp_max": 18.07,
                "pressure": 1016,
                "sea_level": 1016,
                "grnd_level": 1012,
                "humidity": 57,
                "temp_kf": 0
              },
              "weather": [
                {
                  "id": 804,
                  "main": "Clouds",
                  "description": "couvert",
                  "icon": "04d"
                }
              ],
              "clouds": {
                "all": 100
              },
              "wind": {
                "speed": 3.49,
                "deg": 192,
                "gust": 9.8
              },
              "visibility": 10000,
              "pop": 0,
              "sys": {
                "pod": "d"
              },
              "dt_txt": "2022-10-30 09:00:00"
            },
            {
              "dt": 1667131200,
              "main": {
                "temp": 20.45,
                "feels_like": 19.88,
                "temp_min": 20.45,
                "temp_max": 20.45,
                "pressure": 1017,
                "sea_level": 1017,
                "grnd_level": 1012,
                "humidity": 51,
                "temp_kf": 0
              },
              "weather": [
                {
                  "id": 804,
                  "main": "Clouds",
                  "description": "couvert",
                  "icon": "04d"
                }
              ],
              "clouds": {
                "all": 100
              },
              "wind": {
                "speed": 5.18,
                "deg": 233,
                "gust": 8.28
              },
              "visibility": 10000,
              "pop": 0,
              "sys": {
                "pod": "d"
              },
              "dt_txt": "2022-10-30 12:00:00"
            },
            {
              "dt": 1667142000,
              "main": {
                "temp": 19.35,
                "feels_like": 18.88,
                "temp_min": 19.35,
                "temp_max": 19.35,
                "pressure": 1017,
                "sea_level": 1017,
                "grnd_level": 1013,
                "humidity": 59,
                "temp_kf": 0
              },
              "weather": [
                {
                  "id": 804,
                  "main": "Clouds",
                  "description": "couvert",
                  "icon": "04d"
                }
              ],
              "clouds": {
                "all": 100
              },
              "wind": {
                "speed": 3.26,
                "deg": 248,
                "gust": 5.47
              },
              "visibility": 10000,
              "pop": 0,
              "sys": {
                "pod": "d"
              },
              "dt_txt": "2022-10-30 15:00:00"
            },
            {
              "dt": 1667152800,
              "main": {
                "temp": 17.71,
                "feels_like": 17.02,
                "temp_min": 17.71,
                "temp_max": 17.71,
                "pressure": 1020,
                "sea_level": 1020,
                "grnd_level": 1015,
                "humidity": 57,
                "temp_kf": 0
              },
              "weather": [
                {
                  "id": 804,
                  "main": "Clouds",
                  "description": "couvert",
                  "icon": "04n"
                }
              ],
              "clouds": {
                "all": 100
              },
              "wind": {
                "speed": 2.92,
                "deg": 262,
                "gust": 5.9
              },
              "visibility": 10000,
              "pop": 0,
              "sys": {
                "pod": "n"
              },
              "dt_txt": "2022-10-30 18:00:00"
            },
            {
              "dt": 1667163600,
              "main": {
                "temp": 16.25,
                "feels_like": 15.47,
                "temp_min": 16.25,
                "temp_max": 16.25,
                "pressure": 1021,
                "sea_level": 1021,
                "grnd_level": 1016,
                "humidity": 59,
                "temp_kf": 0
              },
              "weather": [
                {
                  "id": 804,
                  "main": "Clouds",
                  "description": "couvert",
                  "icon": "04n"
                }
              ],
              "clouds": {
                "all": 100
              },
              "wind": {
                "speed": 2.5,
                "deg": 259,
                "gust": 5.28
              },
              "visibility": 10000,
              "pop": 0,
              "sys": {
                "pod": "n"
              },
              "dt_txt": "2022-10-30 21:00:00"
            },
            {
              "dt": 1667174400,
              "main": {
                "temp": 15.01,
                "feels_like": 14.24,
                "temp_min": 15.01,
                "temp_max": 15.01,
                "pressure": 1021,
                "sea_level": 1021,
                "grnd_level": 1016,
                "humidity": 64,
                "temp_kf": 0
              },
              "weather": [
                {
                  "id": 804,
                  "main": "Clouds",
                  "description": "couvert",
                  "icon": "04n"
                }
              ],
              "clouds": {
                "all": 96
              },
              "wind": {
                "speed": 0.88,
                "deg": 210,
                "gust": 1.62
              },
              "visibility": 10000,
              "pop": 0,
              "sys": {
                "pod": "n"
              },
              "dt_txt": "2022-10-31 00:00:00"
            },
            {
              "dt": 1667185200,
              "main": {
                "temp": 15.01,
                "feels_like": 14.19,
                "temp_min": 15.01,
                "temp_max": 15.01,
                "pressure": 1020,
                "sea_level": 1020,
                "grnd_level": 1015,
                "humidity": 62,
                "temp_kf": 0
              },
              "weather": [
                {
                  "id": 804,
                  "main": "Clouds",
                  "description": "couvert",
                  "icon": "04n"
                }
              ],
              "clouds": {
                "all": 100
              },
              "wind": {
                "speed": 0.86,
                "deg": 119,
                "gust": 1.51
              },
              "visibility": 10000,
              "pop": 0,
              "sys": {
                "pod": "n"
              },
              "dt_txt": "2022-10-31 03:00:00"
            },
            {
              "dt": 1667196000,
              "main": {
                "temp": 15,
                "feels_like": 14.17,
                "temp_min": 15,
                "temp_max": 15,
                "pressure": 1019,
                "sea_level": 1019,
                "grnd_level": 1014,
                "humidity": 62,
                "temp_kf": 0
              },
              "weather": [
                {
                  "id": 804,
                  "main": "Clouds",
                  "description": "couvert",
                  "icon": "04n"
                }
              ],
              "clouds": {
                "all": 100
              },
              "wind": {
                "speed": 2.08,
                "deg": 132,
                "gust": 4.61
              },
              "visibility": 10000,
              "pop": 0.11,
              "sys": {
                "pod": "n"
              },
              "dt_txt": "2022-10-31 06:00:00"
            },
            {
              "dt": 1667206800,
              "main": {
                "temp": 15.17,
                "feels_like": 14.78,
                "temp_min": 15.17,
                "temp_max": 15.17,
                "pressure": 1018,
                "sea_level": 1018,
                "grnd_level": 1014,
                "humidity": 78,
                "temp_kf": 0
              },
              "weather": [
                {
                  "id": 804,
                  "main": "Clouds",
                  "description": "couvert",
                  "icon": "04d"
                }
              ],
              "clouds": {
                "all": 97
              },
              "wind": {
                "speed": 2.4,
                "deg": 137,
                "gust": 4.62
              },
              "visibility": 10000,
              "pop": 0.25,
              "sys": {
                "pod": "d"
              },
              "dt_txt": "2022-10-31 09:00:00"
            }
        }
    """.trimIndent()
}
