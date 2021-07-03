package com.dash.controller

import com.dash.entity.ImportData
import com.dash.entity.Tab
import com.dash.service.JsonExporter
import com.dash.service.TabService
import com.dash.service.WidgetService
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/config")
class ConfigController {

    @Autowired
    private lateinit var jsonExporter: JsonExporter

    @Autowired
    private lateinit var tabService: TabService

    @Autowired
    private lateinit var widgetService: WidgetService

    private val logger = LoggerFactory.getLogger(this::class.java.name)

    @GetMapping("/export")
    fun downloadJsonFile(): ResponseEntity<ByteArray?>? {
        val widgets: List<Any> = widgetService.getAllWidgets()
        val tabs: List<Tab> = tabService.getTabs()
        val customerJsonString: String = jsonExporter.export(mapOf("widgets" to widgets, "tabs" to tabs))
        val customerJsonBytes = customerJsonString.toByteArray()
        return ResponseEntity
            .ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=dashboardConfig.json")
            .contentType(MediaType.APPLICATION_JSON)
            .contentLength(customerJsonBytes.size.toLong())
            .body(customerJsonBytes)
    }

    @PostMapping("/import")
    fun importConfig(@RequestParam("file") file: MultipartFile): Boolean {
        logger.info("Import commencé")
        val importData = ObjectMapper().readValue(file.bytes, ImportData::class.java)
        importData.tabs.forEach { tab ->
            val widgets = importData.widgets.filter { widget -> widget.tab?.id == tab.id }
            tab.id = null
            val insertedTab = tabService.addTab(tab)
            widgets.forEach { widget ->
                widget.tab?.id = insertedTab.id
                widget.id = null
                widgetService.addWidget(widget)
            }
        }
        logger.info("Import terminé")
        return true
    }
}
