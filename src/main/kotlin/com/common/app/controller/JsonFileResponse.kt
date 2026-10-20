package com.common.app.controller

import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun jsonFileResponse(
    fileNamePrefix: String,
    content: ByteArray
): ResponseEntity<ByteArray> {
    val fileName = "${fileNamePrefix}_${LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE)}.json"
    return ResponseEntity
        .ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=$fileName")
        .contentType(MediaType.APPLICATION_JSON)
        .contentLength(content.size.toLong())
        .body(content)
}
