package com.dash.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.xml.XmlMapper

object XmlParserUtils {
    fun parseXmlData(xml: String): String = ObjectMapper().writeValueAsString(
        XmlMapper().readValue(xml, Any::class.java)
    )
}
