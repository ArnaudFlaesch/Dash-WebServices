package com.cashmanager.app.controller

import com.cashmanager.domain.model.CashManagerImportData
import com.cashmanager.domain.service.CashManagerConfigService
import com.common.app.controller.jsonFileResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import tools.jackson.databind.json.JsonMapper
import tools.jackson.module.kotlin.readValue

@RestController
@RequestMapping("/cashManagerConfig")
class CashManagerConfigController(
    private val cashManagerConfigService: CashManagerConfigService,
    private val jsonMapper: JsonMapper
) {
    @GetMapping("/export")
    fun downloadJsonFile(): ResponseEntity<ByteArray> =
        jsonFileResponse("cashManagerData", jsonMapper.writeValueAsBytes(cashManagerConfigService.exportData()))

    @PostMapping("/import")
    fun importConfig(
        @RequestParam file: MultipartFile
    ): Boolean =
        jsonMapper
            .readValue<CashManagerImportData>(file.bytes)
            .let(cashManagerConfigService::importData)
            .let { true }
}
