package com.dash.cashmanager.adapter.inbound.web

import com.dash.cashmanager.application.port.inbound.CashManagerConfigUseCase
import com.dash.cashmanager.domain.model.CashManagerImportData
import com.dash.shared.adapter.inbound.web.jsonFileResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import tools.jackson.databind.json.JsonMapper
import tools.jackson.module.kotlin.readValue

@RestController
@RequestMapping("/cashManagerConfig")
class CashManagerConfigController(
    private val cashManagerConfigUseCase: CashManagerConfigUseCase,
    private val jsonMapper: JsonMapper
) {
    @GetMapping("/export")
    fun downloadJsonFile(): ResponseEntity<ByteArray> =
        jsonFileResponse("cashManagerData", jsonMapper.writeValueAsBytes(cashManagerConfigUseCase.exportData()))

    @PostMapping("/import")
    fun importConfig(
        @RequestParam file: MultipartFile
    ): Boolean =
        jsonMapper
            .readValue<CashManagerImportData>(file.bytes)
            .let(cashManagerConfigUseCase::importData)
            .let { true }
}
