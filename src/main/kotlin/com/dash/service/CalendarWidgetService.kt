package com.dash.service

import com.fasterxml.jackson.databind.ObjectMapper
import net.fortuna.ical4j.data.CalendarBuilder
import net.fortuna.ical4j.model.Calendar
import org.springframework.stereotype.Service
import java.io.InputStreamReader
import java.io.Reader
import java.net.URL

@Service
class CalendarWidgetService {

    fun getIcalDataFromUrl(url: String): String {
        val reader: Reader = InputStreamReader(URL(url).openStream(), "ISO-8859-15")
        val calendar: Calendar = CalendarBuilder().build(reader)
        return ObjectMapper().writeValueAsString(calendar)
    }
}
