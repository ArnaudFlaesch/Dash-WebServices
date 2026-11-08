package com.dash.shared.adapter.inbound.web

import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun jsonFileResponse(
    fileNamePrefix: String,
    content: ByteArray
): ResponseEntity<ByteArray> =
    ResponseEntity
        .ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=${jsonFileName(fileNamePrefix, LocalDate.now())}")
        .contentType(MediaType.APPLICATION_JSON)
        .contentLength(content.size.toLong())
        .body(content)

private fun jsonFileName(
    fileNamePrefix: String,
    date: LocalDate
): String = "${fileNamePrefix}_${date.format(DateTimeFormatter.BASIC_ISO_DATE)}.json"
