package com.dash.controller

import com.dash.model.ImportData
import com.dash.entity.Tab
import com.dash.entity.Widget
import com.common.utils.JsonExporter
import com.common.utils.JsonExporter.export
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
@RequestMapping("/dashConfig")
class DashConfigController {

    @Autowired
    private lateinit var tabService: TabService

    @Autowired
    private lateinit var widgetService: WidgetService

    private val logger = LoggerFactory.getLogger(this::class.java.name)

    @GetMapping("/export")
    fun downloadJsonFile(): ResponseEntity<ByteArray?>? {
        val widgets: List<Widget> = widgetService.getAllWidgets()
        val tabs: List<Tab> = tabService.getTabs()
        val configJsonString: String = export(mapOf("widgets" to widgets, "tabs" to tabs))
        val configJsonBytes = configJsonString.toByteArray()
        return ResponseEntity
            .ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=dashboardConfig.json")
            .contentType(MediaType.APPLICATION_JSON)
            .contentLength(configJsonBytes.size.toLong())
            .body(configJsonBytes)
    }

    @PostMapping("/import")
    fun importConfig(@RequestParam("file") file: MultipartFile): Boolean {
        logger.info("Import commencé")
        val importData = ObjectMapper().readValue(file.bytes, ImportData::class.java)
        importData.tabs.forEach { tab ->
            val widgets = importData.widgets.filter { widget -> widget.tab.id == tab.id }
            val insertedTab = tabService.addTab(tab.copy(id = 0))
            widgets.forEach { widget ->
                widgetService.saveWidget(widget.copy(id = 0, tab = insertedTab))
            }
        }
        logger.info("Import terminé")
        return true
    }
}
