package com.dash.service

import com.dash.model.CalendarEvent
import net.fortuna.ical4j.data.CalendarBuilder
import net.fortuna.ical4j.model.*
import net.fortuna.ical4j.model.component.VEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.InputStreamReader
import java.io.Reader
import java.nio.charset.StandardCharsets

@Service
class CalendarWidgetService {

    @Autowired
    private lateinit var proxyService: ProxyService

    fun getIcalDataFromUrl(url: String): List<CalendarEvent>? {
        return proxyService.getDataFromProxy(url)?.let { calendarData ->
            val stream = calendarData.byteInputStream(StandardCharsets.ISO_8859_1)
            val reader: Reader = InputStreamReader(stream, StandardCharsets.ISO_8859_1)
            CalendarBuilder().build(reader).components.getComponents<VEvent>(Component.VEVENT).map {
                CalendarEvent(
                    it.startDate.date,
                    it.endDate.date,
                    it.summary.value
                )
            }
        }
    }
}
