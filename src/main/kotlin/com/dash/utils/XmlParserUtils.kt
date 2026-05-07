package com.dash.utils

import tools.jackson.databind.ObjectMapper
import tools.jackson.dataformat.xml.XmlMapper

object XmlParserUtils {
    fun parseXmlDataToJsonString(xml: String): String =
        ObjectMapper().writeValueAsString(XmlMapper().readValue(xml, Any::class.java))
}
