package com.dash.dashboard.domain.model

import com.dash.dashboard.domain.model.config.ImportData
import com.dash.dashboard.domain.model.config.ImportTab
import com.dash.dashboard.domain.model.config.ImportWidget
import com.dash.dashboard.domain.model.incident.Incident
import com.dash.dashboard.domain.model.incident.IncidentStreak
import com.dash.dashboard.domain.model.notification.Notification
import com.dash.dashboard.domain.model.tab.Tab
import com.dash.dashboard.domain.model.widget.Widget
import com.dash.shared.domain.model.NotificationType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime
import java.time.ZoneOffset

class DashboardDomainTests {
    private val streakStart = OffsetDateTime.of(2026, 1, 1, 8, 0, 0, 0, ZoneOffset.UTC)
    private val now = OffsetDateTime.of(2026, 3, 1, 8, 0, 0, 0, ZoneOffset.UTC)

    @Test
    fun shouldPlaceNewTabAfterExistingTabs() {
        assertEquals(Tab(0, "News", 4, 2), Tab.newAfter(3, "News", 2))
    }

    @Test
    fun shouldPlaceNewWidgetAfterExistingWidgets() {
        assertEquals(Widget(0, 5, null, 1, 7), Widget.newAfter(0, 5, 7))
    }

    @Test
    fun shouldEndStreakAndStartNewOne() {
        val (endedStreak, restartedIncident) = Incident(12, streakStart, 3).endStreakAt(now)

        assertEquals(IncidentStreak(0, streakStart, now, 12), endedStreak)
        assertEquals(Incident(12, now, 3), restartedIncident)
    }

    @Test
    fun shouldCreateUnreadNotificationPrefixedWithUsername() {
        val notification = Notification.newUserAction("admintest", "Export", NotificationType.WARN, now)

        assertEquals(Notification(0, "admintest : Export", now, NotificationType.WARN, false), notification)
        assertEquals(true, notification.markAsRead().isRead)
    }

    @Test
    fun shouldGroupImportedWidgetsByTabInTabOrder() {
        val firstTab = ImportTab(10, "First", 1)
        val secondTab = ImportTab(20, "Second", 2)
        val widgetOfSecondTab = ImportWidget(1, null, 1, 20)
        val orphanWidget = ImportWidget(2, null, 1, 99)

        val tabsWithWidgets =
            ImportData(widgets = listOf(widgetOfSecondTab, orphanWidget), tabs = listOf(firstTab, secondTab))
                .tabsWithWidgets()

        assertEquals(listOf(firstTab to emptyList(), secondTab to listOf(widgetOfSecondTab)), tabsWithWidgets)
        assertEquals(Widget(0, 1, null, 1, 42), widgetOfSecondTab.toNewWidget(42))
        assertEquals(Tab(0, "First", 1, 5), firstTab.toNewTab(5))
    }
}
