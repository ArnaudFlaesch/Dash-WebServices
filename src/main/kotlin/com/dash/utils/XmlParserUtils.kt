package com.dash.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.xml.XmlMapper

object XmlParserUtils {
    fun parseXmlDataToJsonString(xml: String): String =
        ObjectMapper().writeValueAsString(XmlMapper().readValue(xml, Any::class.java))
}
