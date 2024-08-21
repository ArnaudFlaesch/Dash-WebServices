package com.cashmanager.app.controller

import com.cashmanager.domain.model.CashManagerImportData
import com.cashmanager.domain.model.ExpenseDomain
import com.cashmanager.domain.model.ExpenseExportDomain
import com.cashmanager.domain.model.LabelDomain
import com.cashmanager.domain.service.ExpenseService
import com.cashmanager.domain.service.LabelService
import com.common.infra.utils.JsonExporter.export
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/cashManagerConfig")
class CashManagerConfigController(
    private val labelService: LabelService,
    private val expenseService: ExpenseService
) {
    private val logger = LoggerFactory.getLogger(this::class.java.name)

    @GetMapping("/export")
    fun downloadJsonFile(): ResponseEntity<ByteArray?>? {
        val expensesToExport =
            expenseService.getUserExpenses().map { expense: ExpenseDomain ->
                ExpenseExportDomain(expense.id, expense.amount, expense.expenseDate.toString(), expense.labelId)
            }
        val labels: List<LabelDomain> = labelService.getUserLabels()
        return export(mapOf("expenses" to expensesToExport, "labels" to labels))
            .toByteArray()
            .let { dataJsonBytes ->
                ResponseEntity
                    .ok()
                    .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=cashManagerData_${LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE)}.json"
                    ).contentType(MediaType.APPLICATION_JSON)
                    .contentLength(dataJsonBytes.size.toLong())
                    .body(dataJsonBytes)
            }
    }

    @PostMapping("/import")
    fun importConfig(
        @RequestParam("file") file: MultipartFile
    ): Boolean {
        logger.info("Import commencé")
        val importData = ObjectMapper().registerModule(JavaTimeModule()).readValue(file.bytes, CashManagerImportData::class.java)
        importData.labels.forEach { label ->
            val expenses = importData.expenses.filter { expense -> expense.labelId == label.id }
            val insertedLabel = labelService.addLabel(label.label)
            expenses.forEach { expense ->
                expenseService.addExpense(expense.amount, LocalDate.parse(expense.expenseDate), insertedLabel.id)
            }
        }
        logger.info("Import terminé")
        return true
    }
}
