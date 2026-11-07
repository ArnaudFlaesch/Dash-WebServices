package com.dash.dashboard.adapter.inbound.web

import com.dash.dashboard.application.port.inbound.DashConfigUseCase
import com.dash.dashboard.domain.model.config.ImportData
import com.dash.shared.adapter.inbound.web.jsonFileResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import tools.jackson.databind.json.JsonMapper
import tools.jackson.module.kotlin.readValue

@RestController
@RequestMapping("/dashConfig")
class DashConfigController(
    private val dashConfigUseCase: DashConfigUseCase,
    private val jsonMapper: JsonMapper
) {
    @GetMapping("/export")
    fun downloadJsonFile(): ResponseEntity<ByteArray> =
        jsonFileResponse("dashboardConfig", jsonMapper.writeValueAsBytes(dashConfigUseCase.exportConfig()))

    @PostMapping("/import")
    fun importConfig(
        @RequestParam file: MultipartFile
    ): Boolean =
        jsonMapper
            .readValue<ImportData>(file.bytes)
            .let(dashConfigUseCase::importConfig)
            .let { true }
}
