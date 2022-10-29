package com.dash.infra.api.response

object AirParifApiResponse {

    fun airParifForecastResponse(communeInseeCode: String) = """
        {
           "$communeInseeCode":[
              {
                 "date":"2021-01-15",
                 "no2":"Bon",
                 "o3":"Mauvais",
                 "pm10":"Moyen",
                 "pm25":"Dégradé",
                 "so2":"Bon",
                 "indice":"Dégradé"
              },
              {
                 "date":"2021-01-16",
                 "no2":"Bon",
                 "o3":"Mauvais",
                 "pm10":"Dégradé",
                 "pm25":"Moyen",
                 "so2":"Dégradé",
                 "indice":"Bon"
              }
           ]
        }
    """.trimIndent()

    val airParifColorsResponse = """
    {
      "Bon": "#50f0e6",
      "Moyen": "#50ccaa",
      "Dégradé": "#f0e641",
      "Mauvais": "#ff5050",
      "Très Mauvais": "#960032",
      "Extrêmement Mauvais": "#7d2181"
    }
    """.trimIndent()
}
