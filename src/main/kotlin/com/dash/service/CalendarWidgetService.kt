package com.dash.service

import com.fasterxml.jackson.databind.ObjectMapper
import net.fortuna.ical4j.data.CalendarBuilder
import net.fortuna.ical4j.model.Calendar
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader
import java.nio.charset.StandardCharsets


@Service
class CalendarWidgetService {

    @Autowired
    private lateinit var proxyService: ProxyService

    fun getIcalDataFromUrl(url: String): String {
        val calendarData = proxyService.getDataFromProxy(url)
        val stream: InputStream = calendarData!!.byteInputStream(StandardCharsets.ISO_8859_1)
        val reader: Reader = InputStreamReader(stream, StandardCharsets.ISO_8859_1)
        val calendar: Calendar = CalendarBuilder().build(reader)
        return ObjectMapper().writeValueAsString(calendar)
    }
}
