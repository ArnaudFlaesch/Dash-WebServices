package com.cashmanager.controller

import com.cashmanager.controller.requests.InsertExpensePayload
import com.cashmanager.entity.Expense
import com.cashmanager.entity.Label
import com.cashmanager.model.ExpenseToDisplay
import com.cashmanager.model.ImportData
import com.cashmanager.service.ExpenseService
import com.cashmanager.service.LabelService
import com.common.utils.JsonExporter.export
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/cashManagerConfig")
class CashManagerConfigController {

    @Autowired
    private lateinit var labelService: LabelService

    @Autowired
    private lateinit var expenseService: ExpenseService

    private val logger = LoggerFactory.getLogger(this::class.java.name)

    @GetMapping("/export")
    fun downloadJsonFile(): ResponseEntity<ByteArray?>? {
        val expenses: List<Expense> = expenseService.getAllExpenses()
        val expensesToExport = expenses.map { expense: Expense ->
            ExpenseToDisplay(expense.id, expense.amount, expense.expenseDate.toString(), expense.label.id)
        }
        val labels: List<Label> = labelService.getLabels()
        val dataJsonString: String = export(mapOf("expenses" to expensesToExport, "labels" to labels))
        val dataJsonBytes = dataJsonString.toByteArray()
        return ResponseEntity
            .ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=cashManagerData.json")
            .contentType(MediaType.APPLICATION_JSON)
            .contentLength(dataJsonBytes.size.toLong())
            .body(dataJsonBytes)
    }

    @PostMapping("/import")
    fun importConfig(@RequestParam("file") file: MultipartFile): Boolean {
        logger.info("Import commencé")
        val importData = ObjectMapper().registerModule(JavaTimeModule()).readValue(file.bytes, ImportData::class.java)
        importData.labels.forEach { label ->
            val expenses = importData.expenses.filter { expense -> expense.labelId == label.id }
            val insertedLabel = labelService.addLabel(label.label)
            expenses.forEach { expense ->
                expenseService.addExpense(InsertExpensePayload(expense.amount, LocalDate.parse(expense.expenseDate), insertedLabel.id))
            }
        }
        logger.info("Import terminé")
        return true
    }
}
