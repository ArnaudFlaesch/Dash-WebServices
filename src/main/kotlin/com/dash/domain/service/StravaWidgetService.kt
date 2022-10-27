package com.dash.domain.service

import com.dash.domain.model.stravaWidget.StravaTokenDataDomain
import com.dash.infra.adapter.StravaWidgetAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class StravaWidgetService {

    @Autowired
    private lateinit var stravaWidgetAdapter: StravaWidgetAdapter

    fun getToken(apiCode: String): StravaTokenDataDomain =
        stravaWidgetAdapter.getToken(apiCode)

    fun getRefreshToken(refreshToken: String): StravaTokenDataDomain =
        stravaWidgetAdapter.getRefreshToken(refreshToken)
}
