package com.dash.cashmanager.adapter.inbound.web

import com.dash.cashmanager.application.port.inbound.CashManagerConfigUseCase
import com.dash.cashmanager.domain.model.CashManagerImportData
import com.dash.shared.adapter.inbound.web.jsonFileResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import tools.jackson.databind.json.JsonMapper
import tools.jackson.module.kotlin.readValue

@RestController
@RequestMapping("/cash-manager-config")
class CashManagerConfigController(
    private val cashManagerConfigUseCase: CashManagerConfigUseCase,
    private val jsonMapper: JsonMapper
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping
    fun exportConfig(): ResponseEntity<ByteArray> =
        jsonFileResponse("cashManagerData", jsonMapper.writeValueAsBytes(cashManagerConfigUseCase.exportData()))

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun importConfig(
        @RequestParam file: MultipartFile
    ) {
        jsonMapper
            .readValue<CashManagerImportData>(file.bytes)
            .let(cashManagerConfigUseCase::importData)
            .also { imported -> logger.info("Import terminé : {} dépenses importées", imported.size) }
    }
}
