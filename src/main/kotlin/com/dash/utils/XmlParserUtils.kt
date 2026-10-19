package com.dash.utils

import tools.jackson.databind.json.JsonMapper
import tools.jackson.dataformat.xml.XmlMapper

object XmlParserUtils {
    private val xmlMapper = XmlMapper()
    private val jsonMapper = JsonMapper()

    fun parseXmlDataToJsonString(xml: String): String =
        jsonMapper.writeValueAsString(xmlMapper.readValue(xml, Any::class.java))
}
