package com.cashmanager.app.controller

import com.cashmanager.domain.model.CashManagerImportData
import com.cashmanager.domain.model.ExpenseExportDomain
import com.cashmanager.domain.service.ExpenseService
import com.cashmanager.domain.service.LabelService
import com.common.app.controller.jsonFileResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import tools.jackson.databind.json.JsonMapper
import tools.jackson.module.kotlin.readValue
import java.time.LocalDate

@RestController
@RequestMapping("/cashManagerConfig")
class CashManagerConfigController(
    private val labelService: LabelService,
    private val expenseService: ExpenseService,
    private val jsonMapper: JsonMapper
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/export")
    fun downloadJsonFile(): ResponseEntity<ByteArray> {
        val expenses =
            expenseService.getUserExpenses().map { expense ->
                ExpenseExportDomain(expense.id, expense.amount, expense.expenseDate.toString(), expense.labelId)
            }
        val data = mapOf("expenses" to expenses, "labels" to labelService.getUserLabels())
        return jsonFileResponse("cashManagerData", jsonMapper.writeValueAsBytes(data))
    }

    @PostMapping("/import")
    fun importConfig(
        @RequestParam file: MultipartFile
    ): Boolean {
        logger.info("Import commencé")
        val importData = jsonMapper.readValue<CashManagerImportData>(file.bytes)
        val expensesByLabelId = importData.expenses.groupBy { it.labelId }
        importData.labels.forEach { label ->
            val insertedLabel = labelService.addLabel(label.label)
            expensesByLabelId[label.id].orEmpty().forEach { expense ->
                expenseService.addExpense(expense.amount, LocalDate.parse(expense.expenseDate), insertedLabel.id)
            }
        }
        logger.info("Import terminé")
        return true
    }
}
