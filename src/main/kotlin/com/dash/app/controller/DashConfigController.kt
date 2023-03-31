package com.dash.app.controller

import com.common.infra.utils.JsonExporter.export
import com.dash.domain.model.TabDomain
import com.dash.domain.model.WidgetDomain
import com.dash.domain.model.config.ImportData
import com.dash.domain.service.TabService
import com.dash.domain.service.WidgetService
import com.fasterxml.jackson.databind.ObjectMapper
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
@RequestMapping("/dashConfig")
class DashConfigController(
    private val tabService: TabService,
    private val widgetService: WidgetService
) {

    private val logger = LoggerFactory.getLogger(this::class.java.name)

    @GetMapping("/export")
    fun downloadJsonFile(): ResponseEntity<ByteArray?>? {
        val widgets: List<WidgetDomain> = widgetService.getUserWidgets()
        val tabs: List<TabDomain> = tabService.getUserTabs()
        val configJsonString: String = export(mapOf("widgets" to widgets, "tabs" to tabs))
        val configJsonBytes = configJsonString.toByteArray()
        return ResponseEntity
            .ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=dashboardConfig_${LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE)}.json")
            .contentType(MediaType.APPLICATION_JSON)
            .contentLength(configJsonBytes.size.toLong())
            .body(configJsonBytes)
    }

    @PostMapping("/import")
    fun importConfig(@RequestParam("file") file: MultipartFile): Boolean {
        logger.info("Import commencé")
        val importData = ObjectMapper().readValue(file.bytes, ImportData::class.java)
        importData.tabs.forEach { tab ->
            val widgets = importData.widgets.filter { widget -> widget.tabId == tab.id }
            val insertedTab = tabService.importTab(tab.label, tab.tabOrder)
            widgets.forEach { widget -> widgetService.importWidget(widget.type, widget.widgetOrder, widget.data, insertedTab.id) }
        }
        logger.info("Import terminé")
        return true
    }
}
