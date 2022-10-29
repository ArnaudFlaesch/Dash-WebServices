package com.dash.infra.api.response

object SteamApiResponse {
    val playerJsonData =
        """
        {
          "response": {
            "players":
                [{
                    "personaname": "personaname",
                    "profileurl": "profileUrl",
                    "avatar": "avatar"
                }]
            }
        }
    """.trimIndent()

    val ownedGamesJsonData = """
        {
          "response": {
            "game_count": 28,
            "games": [
              {
                "appid": 220,
                "name": "Half-Life 2"
              },
              {
                "appid": 340,
                "name": "Half-Life 2: Lost Coast"
              },
              {
                "appid": 280,
                "name": "Half-Life: Source"
              },
              {
                "appid": 360,
                "name": "Half-Life Deathmatch: Source"
              },
              {
                "appid": 320,
                "name": "Half-Life 2: Deathmatch"
              },
              {
                "appid": 380,
                "name": "Half-Life 2: Episode One"
              },
              {
                "appid": 420,
                "name": "Half-Life 2: Episode Two"
              },
              {
                "appid": 2620,
                "name": "Call of Duty"
              },
              {
                "appid": 2630,
                "name": "Call of Duty 2"
              },
              {
                "appid": 2641,
                "name": "Call of Duty 4"
              },
              {
                "appid": 2642,
                "name": "Call of Duty 5"
              },
              {
                "appid": 2643,
                "name": "Call of Duty 6"
              },
              {
                "appid": 2644,
                "name": "Call of Duty 7"
              },
              {
                "appid": 2645,
                "name": "Call of Duty 8"
              },
              {
                "appid": 2646,
                "name": "Call of Duty 9"
              },
              {
                "appid": 2647,
                "name": "Call of Duty 10"
              },
              {
                "appid": 2648,
                "name": "Call of Duty 11"
              },
              {
                "appid": 2649,
                "name": "Call of Duty 12"
              },
              {
                "appid": 2650,
                "name": "Call of Duty 13"
              },
              {
                "appid": 2651,
                "name": "Call of Duty 14"
              },
              {
                "appid": 2652,
                "name": "Call of Duty 15"
              },
              {
                "appid": 2653,
                "name": "Call of Duty 16"
              },
              {
                "appid": 2654,
                "name": "Call of Duty 17"
              },
              {
                "appid": 2655,
                "name": "Call of Duty 18"
              },
              {
                "appid": 2656,
                "name": "Call of Duty 19"
              },
              {
                "appid": 2657,
                "name": "Call of Duty 20"
              },
              {
                "appid": 2658,
                "name": "Call of Duty 21"
              }
            ]
          }
        }
        """.trimIndent()

    val halfLifeTwoEpisodeTwoStatsResponse = """
        {
          "playerstats": {
            "steamID": "76561198046131373",
            "gameName": "Half-Life 2: Episode Two",
            "achievements": [
              {
                "apiname": "EP2_KILL_POISONANTLION",
                "achieved": 1,
                "unlocktime": 1352488322
              },
              {
                "apiname": "EP2_KILL_ALLGRUBS",
                "achieved": 0,
                "unlocktime": 0
              },
              {
                "apiname": "EP2_BREAK_ALLWEBS",
                "achieved": 1,
                "unlocktime": 1446892109
              },
              {
                "apiname": "EP2_BEAT_ANTLIONINVASION",
                "achieved": 1,
                "unlocktime": 1352489474
              },
              {
                "apiname": "EP2_BEAT_ANTLIONGUARDS",
                "achieved": 1,
                "unlocktime": 1352492128
              },
              {
                "apiname": "EP2_KILL_ENEMIES_WITHCAR",
                "achieved": 1,
                "unlocktime": 1396087044
              },
              {
                "apiname": "EP2_BEAT_HUNTERAMBUSH",
                "achieved": 1,
                "unlocktime": 1352494692
              },
              {
                "apiname": "EP2_KILL_CHOPPER_NOMISSES",
                "achieved": 1,
                "unlocktime": 1446972886
              },
              {
                "apiname": "EP2_KILL_COMBINECANNON",
                "achieved": 1,
                "unlocktime": 1352496053
              },
              {
                "apiname": "EP2_FIND_ALLRADARCACHES",
                "achieved": 1,
                "unlocktime": 1352497466
              },
              {
                "apiname": "EP2_BEAT_RACEWITHDOG",
                "achieved": 1,
                "unlocktime": 1352497786
              },
              {
                "apiname": "EP2_BEAT_ROCKETCACHEPUZZLE",
                "achieved": 1,
                "unlocktime": 1352496289
              },
              {
                "apiname": "EP2_BEAT_WHITEFORESTINN",
                "achieved": 1,
                "unlocktime": 1352497109
              },
              {
                "apiname": "EP2_PUT_ITEMINROCKET",
                "achieved": 0,
                "unlocktime": 0
              },
              {
                "apiname": "EP2_BEAT_MISSILESILO2",
                "achieved": 1,
                "unlocktime": 1352498951
              },
              {
                "apiname": "EP2_BEAT_OUTLAND12_NOBUILDINGSDESTROYED",
                "achieved": 0,
                "unlocktime": 0
              },
              {
                "apiname": "EP2_BEAT_GAME",
                "achieved": 1,
                "unlocktime": 1352555354
              },
              {
                "apiname": "EP2_KILL_HUNTER_WITHFLECHETTES",
                "achieved": 1,
                "unlocktime": 1396035018
              },
              {
                "apiname": "HLX_KILL_ENEMIES_WITHPHYSICS",
                "achieved": 1,
                "unlocktime": 1446893075
              },
              {
                "apiname": "HLX_KILL_ENEMY_WITHHOPPERMINE",
                "achieved": 1,
                "unlocktime": 1352488798
              },
              {
                "apiname": "HLX_KILL_SOLDIER_WITHHISGRENADE",
                "achieved": 1,
                "unlocktime": 1446974675
              },
              {
                "apiname": "EPX_GET_ZOMBINEGRENADE",
                "achieved": 1,
                "unlocktime": 1352487603
              },
              {
                "apiname": "GLOBAL_GNOME_ALONE",
                "achieved": 0,
                "unlocktime": 0
              }
            ],
            "success": true
          }
        }
    """.trimIndent()
}