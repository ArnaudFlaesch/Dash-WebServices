package com.dash.domain.service

import com.common.app.security.SecurityConditions
import com.dash.domain.model.incidentWidget.IncidentDomain
import com.dash.domain.model.incidentWidget.IncidentStreakDomain
import com.dash.infra.adapter.IncidentWidgetAdapter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service

@Service
class IncidentWidgetService(
    private val incidentWidgetAdapter: IncidentWidgetAdapter
) {

    @PreAuthorize("${SecurityConditions.doesWidgetBelongToAuthenticatedUser}")
    fun getIncidentConfigForWidget(widgetId: Int): IncidentDomain = incidentWidgetAdapter.getIncidentConfigForWidget(widgetId)

    @PreAuthorize("${SecurityConditions.doesWidgetBelongToAuthenticatedUser}")
    fun startFirstStreak(widgetId: Int): IncidentDomain = incidentWidgetAdapter.startStreak(widgetId)

    @PreAuthorize("${SecurityConditions.doesWidgetBelongToAuthenticatedUser}")
    fun endStreak(widgetId: Int): IncidentDomain = incidentWidgetAdapter.endStreak(widgetId)

    @PreAuthorize("${SecurityConditions.doesIncidentBelongToAuthenticatedUser}")
    fun getIncidentStreaks(incidentId: Int): List<IncidentStreakDomain> = incidentWidgetAdapter.getIncidentStreaks(incidentId)
}
