package com.dash.infra.api.response

object StravaApiResponse {

    val stravaTokenResponse = """
        {
          "token_type": "Bearer",
          "expiresAt": 1644882561,
          "expires_in": 10384,
          "refreshToken": "TOKEN",
          "accessToken": "REFRESH_TOKEN",
          "athlete": {
            "id": 12346,
            "username": "aflaesch",
            "resource_state": 2,
            "firstname": "Arnaud",
            "lastname": "Flaesch",
            "bio": "",
            "city": "Paris",
            "state": "",
            "country": "France",
            "sex": "M",
            "premium": false,
            "summit": false,
            "createdAt": "2017-09-28T21:10:11Z",
            "updatedAt": "2022-02-14T20:56:15Z",
            "badgeTypeId": 0,
            "weight": 0.0,
            "profileMedium": "jpg",
            "profile": "jpg"
          }
        }
    """.trimIndent()

    val stravaAthleteData = """
        {
          "id": 123456,
          "username": "aflaesch",
          "resource_state": 2,
          "firstname": "Arnaud",
          "lastname": "Flaesch",
          "bio": "",
          "city": "Paris",
          "state": "",
          "country": "France",
          "sex": "M",
          "premium": false,
          "summit": false,
          "created_at": "2017-09-28T21:10:11Z",
          "updated_at": "2022-10-24T12:11:25Z",
          "badge_type_id": 0,
          "weight": 0,
          "profile_medium": "link",
          "profile": "link",
          "friend": null,
          "follower": null
        }
    """.trimIndent()

    val stravaActivitiesData = """
        [
          {
            "resource_state": 2,
            "athlete": {
              "id": 25345795,
              "resource_state": 1
            },
            "name": "Evening Run",
            "distance": 10704.7,
            "moving_time": 2958,
            "elapsed_time": 3118,
            "total_elevation_gain": 49.2,
            "type": "Run",
            "sport_type": "Run",
            "workout_type": 0,
            "id": 7993416249,
            "start_date": "2022-10-20T16:34:39Z",
            "start_date_local": "2022-10-20T18:34:39Z",
            "timezone": "(GMT+01:00) Europe/Paris",
            "utc_offset": 7200,
            "location_city": null,
            "location_state": null,
            "location_country": "France",
            "achievement_count": 1,
            "kudos_count": 2,
            "comment_count": 0,
            "athlete_count": 1,
            "photo_count": 0,
            "map": {
              "id": "a7993416249",
              "summary_polyline": "}~aiH}psMCh@Np@NvAJvAFdB?fCHt@@f@@HV?ZNLVUb@k@d@c@f@Gh@On@\\nA?d@@JRd@Hb@?ZI^Nb@@R?l@Kn@F|@Ch@BFAbARPXr@PjD?\\Iv@Sh@Iv@Fb@?j@J`@^bAKXM?]M?FGJFJ@H[XgAR]GhA~DP`AHa@e@mAUjb@g@Ka@[[a@Z`C]zBIPOAAZ@XNz@J~A?n@GbAYpCKjCIp@K^T`@f@^xAf@p@b@xAE|@PLP`@jA@NIBIVNeAFsAAWKg@QKpHjR]AUMTy@XZDRAXiAdAu@b@c@JOX[BWb@[PEXS\\^@ALSHi@C}A|ByBvDYz@Yf@y@x@e@Ra@Ca@W_@t@MJODS?SKSWOe@OLAf@V~EAVB~@E`@uOxOEGGs@Fu@A_Asc@vp@Mi@bApEX~@NVRJLIcBwCEa@DOLOBk@_AaCK}@uAkBSIg@GQmAz@iDD]?]M]m@q@@]Ee@k@iBU[K]IKe@i@YSF_BAk@Ma@e@{Be@aB@I?g@YgBAa@_@cBCc@@WD@AQo@_AMm@Aa@EGLn@Z[rE{AHzBa@\\{Fk@r@kGdDo@XU?Dd@B?k@^Qj@_A`@BjAXbAId@JNIR@RHFAZ[XMJS^MHFHVRLDRBsAJy@?sABy@Ls@Z_ANSp@k@XCTSHg@NMI_@?UHYCWDSPK`@?PMVAAwABoANaAb@kAR[XORLTELHDPJFk@c@KMCMCoAJ[~@yAb@_@X_@l@Wn@BBYCuBEiAAWBUtAsBJ_@^u@^QPeAFMNKFg@NWNI?QDWHULUFm@Rg@C[BIRe@R[l@DNN@d@CJe@x@VmETsATs@b@y@V_@^eA\\c@JS^QFQPWBSA_@Do@Ty@Hc@j@cAx@u@DM?[HSB[^sAZa@Xo@n@g@\\OJOJGDc@NSLGGw@?c@Ig@YaAG]Iw@@c@J_@Pe@RQFQ?k@E[Di@\\{@Py@Pg@Bc@Ji@@k@Jk@?y@Hw@BMLSTi@Bc@Le@RgAB_ADc@Cq@Fw@Fe@R[DUHK`@ONUX@NUB{@Ho@@YESA{@Kq@]AYSa@eAGa@DIFg@BmAVyCGe@Dq@Ci@Dy@WwEDk@AGGEEm@@o@BS",
              "resource_state": 2
            },
            "trainer": false,
            "commute": false,
            "manual": false,
            "private": false,
            "visibility": "everyone",
            "flagged": false,
            "gear_id": "g11571174",
            "start_latlng": [
              48.84,
              2.4
            ],
            "end_latlng": [
              48.84,
              2.4
            ],
            "average_speed": 3.619,
            "max_speed": 16.919,
            "has_heartrate": false,
            "heartrate_opt_out": false,
            "display_hide_heartrate_option": false,
            "elev_high": 57,
            "elev_low": 34.6,
            "upload_id": 8550990828,
            "upload_id_str": "8550990828",
            "external_id": "ad38998a-3d06-41ee-9e9d-b6bb7c143edf-activity.fit",
            "from_accepted_tag": false,
            "pr_count": 0,
            "total_photo_count": 0,
            "has_kudoed": false
          },
          {
            "resource_state": 2,
            "athlete": {
              "id": 25345795,
              "resource_state": 1
            },
            "name": "Evening Run",
            "distance": 12397.6,
            "moving_time": 2611,
            "elapsed_time": 2916,
            "total_elevation_gain": 102.2,
            "type": "Run",
            "sport_type": "Run",
            "workout_type": 0,
            "id": 7978046184,
            "start_date": "2022-10-17T16:12:50Z",
            "start_date_local": "2022-10-17T18:12:50Z",
            "timezone": "(GMT+01:00) Europe/Paris",
            "utc_offset": 7200,
            "location_city": null,
            "location_state": null,
            "location_country": "France",
            "achievement_count": 0,
            "kudos_count": 0,
            "comment_count": 0,
            "athlete_count": 1,
            "photo_count": 0,
            "map": {
              "id": "a7978046184",
              "summary_polyline": "m~aiHqnsMATJF@V?vAEl@ZrAEn@Dw@?pAZlAT|Ba@lDHxBAPIRi@Vn@WLCJFPh@HvAW`BO~A@j@Kn@?n@It@Df@If@D\\?\\^NL\\FX@b@A|@Fb@?`ADn@UfCWnA?TILL`@`@VFJ@RIXQHc@Ci@He@}AAoBF{@\\EZTTt@Hn@sBlf@TwAACFCyArE[j@CtAK~@Jh@CfAStAM^]j@KH_@?c@k@SiAbMdNEDDPGV@\\DTVr@lHnMOd@N[e@Ae@`@g@RW`@]FEVSJGNYBi@lAKJa@EY_@EMAa@H_@eBpGs@|BWn@y@hA[JKf@Af@KNIBUOWg@?w@m@fBaAdD[l@SJ]CWN]H]OSYO[ImAHu@N[RSh@Gz@j@H|@C\\U\\SL[FUG]i@Ig@?o@Tk@d@e@hAb@IDMEI_Aqs@bfAYOQU\\nChDhGc@Kw@{_@CB}@`ZD@tA_[{AfVm@YVBHGI{@IKOa@Em@F_@JMv@a@v@oMYh@qCdCYh@YN@Ps@AYUKWL_BZwAAi@D_AmA_Bq@UGkAAuADu@So@Cq@MSCg@OOQ[C]Oa@Em@KSCy@EUFm@Mm@Sc@Es@Qe@Kk@]UOk@@pAIQ@A?KX[PGHFxAe@nB_Gx@Uj@DZIx@N?{@DSDEb@G`BXb@]ZCp@e@b@KTyANa@RUIw@BQP[\\]VmAX]@[Hi@TQ?IFKVGHi@L[TU\\ORoBPg@@i@VkAZY\\q@ANZARQRu@Bs@L[Mq@B_@j@e@HDLEFOh@]JSM@ZPJX?qEPcAf@kAZ_@Hc@Pk@\\c@Ja@Xc@L_@XWFMZKHi@AqA^yAx@iA\\JJ\\PLL\\TH?cBFm@WoCB[ZeB`BeBd@MPe@XMNmAZm@Fe@Ri@He@?i@Pa@B_@L_@z@qA^OLe@VSZcAXSn@mAVUF@Ao@Bs@CKRKiAqAs@o@X_ApAkDF]P_@Fq@Na@GWRi@Ci@Bk@Vm@Ci@B[Po@Zg@JcARg@Hg@T_@@i@Ne@Ik@Hk@EUBQHSVIl@JN^VRl@Gq@uFCc@B_ANuBGcDHk@HOHACHQNEd@_@H@LeAcACu@FuAVmB\\qARc@Gy@B_@Am@De@EM}@_@QYKs@A_@JiAFH?EGDB?IA?F",
              "resource_state": 2
            },
            "trainer": false,
            "commute": false,
            "manual": false,
            "private": false,
            "visibility": "everyone",
            "flagged": false,
            "gear_id": "g11571174",
            "start_latlng": [
              48.84,
              2.4
            ],
            "end_latlng": [
              48.84,
              2.4
            ],
            "average_speed": 4.748,
            "max_speed": 25.746,
            "has_heartrate": false,
            "heartrate_opt_out": false,
            "display_hide_heartrate_option": false,
            "elev_high": 60.1,
            "elev_low": 28.1,
            "upload_id": 8533704724,
            "upload_id_str": "8533704724",
            "external_id": "55bbcb3c-6b6c-4e5e-9ddd-310a03a7e0a4-activity.fit",
            "from_accepted_tag": false,
            "pr_count": 0,
            "total_photo_count": 0,
            "has_kudoed": false
          },
          {
            "resource_state": 2,
            "athlete": {
              "id": 25345795,
              "resource_state": 1
            },
            "name": "Lunch Run",
            "distance": 10529.8,
            "moving_time": 2397,
            "elapsed_time": 2489,
            "total_elevation_gain": 63.6,
            "type": "Run",
            "sport_type": "Run",
            "workout_type": 0,
            "id": 7935092006,
            "start_date": "2022-10-09T09:44:01Z",
            "start_date_local": "2022-10-09T11:44:01Z",
            "timezone": "(GMT+01:00) Europe/Paris",
            "utc_offset": 7200,
            "location_city": null,
            "location_state": null,
            "location_country": "France",
            "achievement_count": 0,
            "kudos_count": 1,
            "comment_count": 0,
            "athlete_count": 1,
            "photo_count": 0,
            "map": {
              "id": "a7935092006",
              "summary_polyline": "o|aiHworMHx@BrABzBCn@A|@HpAA^Qf@d@J\\|AA^MHK@GJHnAQfEHbAEQEEE@ST[RGBQBAt@E^PdB@RGTMYIGKMI[GeA@YMm@C_@DI@Q^pi@CBEAc@WHk@@c@CGc@fFWtAK`AHnA?`BIz@Ov@P\\FZBb@Aj@EZMd@MJO@fBb@f@RlAp@b@`@Zd@P\\DNBVAJCEXx@EABLI`@_@oAYqAKs@Cc@~KxRPNN\\[bBMXE@KIKSIKSKKUCUKBEEGDMRcAlB[b@[\\QLSTOJS@[MYi@IWIe@CoAIp@_AvEeAdE]~AUj@W\\SRWJKe@Ce@KKQ]EFUl@MPa@dAKL[PMCMGS[Kq@AUCW@YJc@Pg@LMPEN@XNz@|AIVSTYFMAOGSYGYAQ@YFWZu@Va@LGRA_@bIGU?_@iq@lnAANb`@bOa`@yN{@nIKd@GD@ICFCADk@FEj@ZXXP\\cGuRSp@KP?YHg@`@oBPaA?]Du@A?Ec@K?KUGe@?a@jCcOLm@@CBDDEy@lB{BnEy@lAGISPCAIQKqAGk@GaA@w@G]U[Ys@_@cB@a@Hc@DGGe@Qy@AWFYIOq@w@a@m@Uo@QaACmAGYHo@CWDUFMLACG[_@y@u@k@y@i@qAC]H@DER@FCTLJ@a@N]@k@kBE_@@SHWP?HL@b@I\\_@x@M`@|DgHNQpCe@VKz@g@PIZA^DHAP^XCl@SN?RDH?ZMZBTOR@HFDDn@UFM?_@B[LWj@i@a@g@IW?ABAEs@FSFIEUBKFK^KFe@HSNMXM@e@Fa@Ie@FWF@JCLERQ`@EF]L]DGTEDELLBAQ}@Gk@?MDUh@aBz@sBXGPDRELYLIFi@L_@IkAAc@Fc@Bk@Jg@b@i@LIXGX_@JGLCLu@HKLIBg@DU^_ARW@c@Nu@JSPKFSFMTQBc@HYRUHOZSPm@N[@y@D[FQLOBQR_@@DZ]@TDJ]~CNwEFy@Nm@V]\\WPWVQRk@No@Xk@Rq@PWRQNa@PWa@QAWF]f@iAf@m@Py@\\k@Jg@JYNO@KFELG@GBEJ??WDOVWH?BBFMPIHSQi@F[JMTINQNIT[NEHe@J]@UC{@SuAA_@Bo@Ie@?[PaAVc@EMB[P]Ja@R]@g@PaATg@QmA?SBSNc@BWLi@NaA",
              "resource_state": 2
            },
            "trainer": false,
            "commute": false,
            "manual": false,
            "private": false,
            "visibility": "everyone",
            "flagged": false,
            "gear_id": "g11571174",
            "start_latlng": [
              48.84,
              2.39
            ],
            "end_latlng": [
              48.84,
              2.39
            ],
            "average_speed": 4.393,
            "max_speed": 27.142,
            "has_heartrate": false,
            "heartrate_opt_out": false,
            "display_hide_heartrate_option": false,
            "elev_high": 52.7,
            "elev_low": 31.6,
            "upload_id": 8484120451,
            "upload_id_str": "8484120451",
            "external_id": "fb87134a-6899-4de5-899d-52e268350771-activity.fit",
            "from_accepted_tag": false,
            "pr_count": 0,
            "total_photo_count": 0,
            "has_kudoed": false
          },
          {
            "resource_state": 2,
            "athlete": {
              "id": 25345795,
              "resource_state": 1
            },
            "name": "Afternoon Run",
            "distance": 11666.4,
            "moving_time": 2331,
            "elapsed_time": 2555,
            "total_elevation_gain": 101.5,
            "type": "Run",
            "sport_type": "Run",
            "workout_type": 0,
            "id": 7849958186,
            "start_date": "2022-09-22T15:31:15Z",
            "start_date_local": "2022-09-22T17:31:15Z",
            "timezone": "(GMT+01:00) Europe/Paris",
            "utc_offset": 7200,
            "location_city": null,
            "location_state": null,
            "location_country": "France",
            "achievement_count": 0,
            "kudos_count": 0,
            "comment_count": 0,
            "athlete_count": 1,
            "photo_count": 0,
            "map": {
              "id": "a7849958186",
              "summary_polyline": "k|aiH_krMDz@Ep@VjCB`AVvBBdAEt@EHQbAMh@IB`@bAC?EVGDKTEBADPlA\\`B@^Kb@OVKHIBMTMJQFGCUEOKKMKSG_@XqBFGL?JBRLFX@^G\\MJOOM_@Ea@Cc@@_AB_@Wli@EEGO@UCxAEHMDGFMVQHUBIFBJTXDTAb@DbCE`ABNG^M\\c@v@CJLd@b@z@j@xAJJVb@Z`@`@v@TLNVJRH\\DfAEl@_@d@[?KCC@KXUYSkAMaBBu@Jc@PU~O~VI@Gh@I}@@a@Dg@Vg@@BYt@e@NSAKPWv@KPOJKf@EBOOEAISe@~BMb@o@lAa@b@WD[IKMIQQYOd@]jBe@nBg@rAMRSTMHMB[BKAUDQKGQMMQm@FfCHlAAj@CNm@t@WNUBWh@U^QTc@VCAE@CIC@AGA?Jg@sBrC{@~@kAz@q@IHo@Mc@cGWdIxAhBPqB}C_FcEIA~CbUQGeNyK?CxGhOk@pFd@e`@c@p_@mUgS`@tBDj@fCxR_D~@]n@lNe^{Vhk@tBoHCBjLpKA@cSfFASLa@|@mEqAzD@b@Ff@|CxFx@~AKz@Wn@?TGIMWMe@@IJIx@Kg@]iC_Bk@m@OYEq@?YPiAAa@Dm@CO@q@SQKWYiBMc@OUFsAC_@@a@GO?OA?Mp@E\\BBWkASe@IG@kBMy@Eq@SAKCQO]i@]{@?SMa@Io@EeAE_@OYSs@OuABs@Cg@Ba@QUQo@QMCGYsAB~@c@yBQiA?aBBe@HBFAHMFOc@d@g@XAFC\\zDmFr@eBPa@^g@PM`@t@LDLEFJHGPBAGBCF@DA?KBG`@KL?NFJPJWDCDQBC~@CPMRADKRGFGLA@QPKHKDOn@YOB?_@JMFALBEsANUH?EKCSR[L]\\g@BQEGAOBMPO\\\\?[DUHONSG_A@QNWF]La@`@YX]JCJI\\}@Ge@@ILe@TkALa@LSDCD@PCPFPCNENMe@QIK?IT_BPs@Xm@`@i@TAb@O`AAJi@Fm@W}ABML]?KT[FEAOBGNQREEi@LIBOBENMD]LIDULOCO?ONOFULI@QNMLENFHY?i@DOFMCa@?GBKJMCG?INSBQJIHUHMZSJOb@]Gk@@WHU?YRe@Hg@T_@f@i@LWF[FORU`@q@?o@LkAJ_@Zo@S\\I?CEAWDS\\_AV_Af@WTSRSRe@TW",
              "resource_state": 2
            },
            "trainer": false,
            "commute": false,
            "manual": false,
            "private": false,
            "visibility": "everyone",
            "flagged": false,
            "gear_id": "g2726529",
            "start_latlng": [
              48.84,
              2.39
            ],
            "end_latlng": [
              48.84,
              2.39
            ],
            "average_speed": 5.005,
            "max_speed": 36.188,
            "has_heartrate": false,
            "heartrate_opt_out": false,
            "display_hide_heartrate_option": false,
            "elev_high": 52.7,
            "elev_low": 29.8,
            "upload_id": 8388107116,
            "upload_id_str": "8388107116",
            "external_id": "a03d0c6a-47dd-49e7-93dc-3e82316376e7-activity.fit",
            "from_accepted_tag": false,
            "pr_count": 0,
            "total_photo_count": 0,
            "has_kudoed": false
          },
          {
            "resource_state": 2,
            "athlete": {
              "id": 25345795,
              "resource_state": 1
            },
            "name": "Lunch Run",
            "distance": 9584.6,
            "moving_time": 2277,
            "elapsed_time": 2352,
            "total_elevation_gain": 52.5,
            "type": "Run",
            "sport_type": "Run",
            "workout_type": 0,
            "id": 7685158288,
            "start_date": "2022-08-23T09:26:52Z",
            "start_date_local": "2022-08-23T11:26:52Z",
            "timezone": "(GMT+01:00) Europe/Paris",
            "utc_offset": 7200,
            "location_city": null,
            "location_state": null,
            "location_country": "France",
            "achievement_count": 2,
            "kudos_count": 0,
            "comment_count": 0,
            "athlete_count": 1,
            "photo_count": 0,
            "map": {
              "id": "a7685158288",
              "summary_polyline": "s|aiHslrMFr@GvA?LDNGnA?fAClAFd@FdBPzBAxAEl@?p@NjBDCLZSgACD@`AG`@GbBHc@?\\Ij@a@kB{A~d@CBIn@?\\FCZYLGMAUBoAfEA`@Gl@Fh@A^O`AS`@GXX_AFMB?x@pBn@hBVbAFl@Tz@P|@Dt@AbAIrAG^@r@Ej@GXKTOPrNvFAGDAj@PP?Fm@ADqBpBqEjE_@h@MZGf@S|@IJYj@ONKDM@IISa@Ig@EAo@nBS^YTQ?Wh@WZm@Vc@zA]|@]j@g@b@Qj@W`@]Pi@?KBSGOYc@Ya@?mDDYL]VDZTj@Rt@HvAEn@Kh@Q^UVUFE?eBuBQi@KOMWOs@\\hJDlBA^Iz@Ry@G{BSaCuXpXMPO\\In@?nALzDCj@O^MJOBc@SWUyBxDaBzCc@n@YTUCEUECIUIEAYEMA]tJ|Ke@@YAQFVLDH?PONQEa@]MQ_@]M[q@oDQsAFe@@[Eq@EEAI@g@Ke@]IEGYk@Ya@Yu@`@aAVaAHg@E@ASGXC?EIAMSSg@a@GKSmAKWo@_A?WEe@GUU[Sq@_@{CQeDCq@Qq@Iy@[k@WaAIy@Mk@Ce@M]Eg@Mc@Ae@IOBi@RgB^wANSJGN@\\NDAH?NKR@XG\\?FABCXu@NOJCBDH@TJNSPCa@_@AG`BUN?^FPCb@?LQLKRGRAXQ\\K`@y@FSBo@F]Be@J_@`AqA|@_BJK?BPUe@n@@eAH}@BEFEZ_@K\\BCVg@Vq@Zq@HYZeBd@eAj@cCDGb@EVWRq@q@t@Tk@\\qARYb@eA\\o@\\At@[Lw@F_B`@uBn@gBf@}@Tu@R_@d@k@J]Tc@?k@@S`@mBNQTOPQJ@LGB?`ASHi@LO@I?a@DMm@KGKZo@DAD@`AmCP[Re@X_@RoALc@\\aANm@P_@FWPk@d@u@nAqCTo@VOJMF]JYZa@Vm@Nq@TcBCOg@{AGa@Aa@Na@Jy@XcB",
              "resource_state": 2
            },
            "trainer": false,
            "commute": false,
            "manual": false,
            "private": false,
            "visibility": "everyone",
            "flagged": false,
            "gear_id": "g2726529",
            "start_latlng": [
              48.84,
              2.39
            ],
            "end_latlng": [
              48.84,
              2.39
            ],
            "average_speed": 4.209,
            "max_speed": 18.291,
            "has_heartrate": false,
            "heartrate_opt_out": false,
            "display_hide_heartrate_option": false,
            "elev_high": 55.1,
            "elev_low": 34.3,
            "upload_id": 8202218998,
            "upload_id_str": "8202218998",
            "external_id": "1e99086a-d309-4599-81ce-da7ad67c57e9-activity.fit",
            "from_accepted_tag": false,
            "pr_count": 1,
            "total_photo_count": 0,
            "has_kudoed": false
          },
          {
            "resource_state": 2,
            "athlete": {
              "id": 25345795,
              "resource_state": 1
            },
            "name": "Lunch Run",
            "distance": 8478.6,
            "moving_time": 1899,
            "elapsed_time": 1939,
            "total_elevation_gain": 63.3,
            "type": "Run",
            "sport_type": "Run",
            "workout_type": 0,
            "id": 7663204250,
            "start_date": "2022-08-19T09:05:28Z",
            "start_date_local": "2022-08-19T11:05:28Z",
            "timezone": "(GMT+01:00) Europe/Paris",
            "utc_offset": 7200,
            "location_city": null,
            "location_state": null,
            "location_country": "France",
            "achievement_count": 0,
            "kudos_count": 0,
            "comment_count": 0,
            "athlete_count": 1,
            "photo_count": 0,
            "map": {
              "id": "a7663204250",
              "summary_polyline": "a|aiHwgrM?JVhCAhA?l@d@tB@hAB`@FXZb@Lp@a@XK`@An@H~AEb@E`AGf@Cz@ALINKj@Gz@Bp@Q\\?f@@LHHN@HVR`ALRRTTb@Dh@PlABj@Z`BFr@cGzGm@z@e@|@?NRKDLARE?GHOd@CWI?ERDhACnA?z@Ft@?rBDp@BZTz@Pd@Xj@v@r@hAt@ZPdA^n@\\e@xAI^E\\Cd@?VD^LXHHPHXFZ@lE`Gr@x@?\\D\\AEBCBBDCRBXGcC|@cAVYNw@z@INGZIDMAGDOPQf@?ZBPMHIREZENGb@KvAIbCClDsAWsAOq@K]Cg@BUHYPUbACl@CPVd@TRd@TFFZHR?sEQcA@iAJSGKIKEOBKDWPU`@Kd@CXBVPl@El@O`A?VHj@Vh@LBPED@d@RVD\\P^Fco@lw@BRSHEFKf@O^MH{@AWLQN_AoF[sAGm@?mBBy@Ce@G]EIIE[a@WMgAL?[\\aCTwBFQHg@@OE[GOGYJjBKI_@EEEMi@U[M[OQKUKISACg@Oa@DkACWEWIQSW_@KCIAe@TuAC[ESQ]Eg@Yi@GaACG]YEc@GGQGCUGSGIIEBSC_@GMMQEYByAEUMSMMe@u@s@w@WMM@GDKZ`CaFd@w@Vi@l@{@\\]JOBAFFRODU?i@ECC@[`@c@ZMR|A@jCCd@A\\Gd@IVOj@k@lASf@]`@?XUJ@TEPSDc@Ic@?MDMGc@DMV]H]f@g@FMJa@B\\JBf@MDKHu@B_ABIX[HUB_@ViADu@DGTSFKFSDYBGHCHKBMBk@DKFo@Eg@BIb@I~@MR_@Nc@PuALWHWX_@DOBWPg@@g@H[BW@]C]DKXUJQJYNCj@D?eAM}@XYJOZ?JGHSFm@TSJWBQFMLGDGF]?a@Lo@PYDSFk@DGLEDEDODm@FQ?e@R[Lg@j@CTMHKRa@PWP_AFQDw@JOBIHGLEJMDQDe@T[Hi@BGHCDGHSBWFKVKT]HYFc@LYNu@Z_A",
              "resource_state": 2
            },
            "trainer": false,
            "commute": false,
            "manual": false,
            "private": false,
            "visibility": "everyone",
            "flagged": false,
            "gear_id": "g2726529",
            "start_latlng": [
              48.84,
              2.39
            ],
            "end_latlng": [
              48.84,
              2.39
            ],
            "average_speed": 4.465,
            "max_speed": 15.998,
            "has_heartrate": false,
            "heartrate_opt_out": false,
            "display_hide_heartrate_option": false,
            "elev_high": 51,
            "elev_low": 22.3,
            "upload_id": 8177396793,
            "upload_id_str": "8177396793",
            "external_id": "93042f1f-a0e6-47ce-b01c-5d5b717ed676-activity.fit",
            "from_accepted_tag": false,
            "pr_count": 0,
            "total_photo_count": 0,
            "has_kudoed": false
          }
        ]
    """.trimIndent()
}
