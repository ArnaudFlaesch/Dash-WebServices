package com.dash.app.controller

import com.common.app.controller.jsonFileResponse
import com.common.app.security.SecurityConditions
import com.common.domain.event.DashEvent
import com.dash.domain.model.config.ImportData
import com.dash.domain.model.notification.NotificationType
import com.dash.domain.service.TabService
import com.dash.domain.service.WidgetService
import com.dash.domain.utils.Constants
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import tools.jackson.databind.json.JsonMapper
import tools.jackson.module.kotlin.readValue

@RestController
@RequestMapping("/dashConfig")
@PreAuthorize(SecurityConditions.IS_USER_ADMIN)
class DashConfigController(
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val tabService: TabService,
    private val widgetService: WidgetService,
    private val jsonMapper: JsonMapper
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/export")
    fun downloadJsonFile(): ResponseEntity<ByteArray> {
        val config = mapOf("widgets" to widgetService.getUserWidgets(), "tabs" to tabService.getUserTabs())
        applicationEventPublisher.publishEvent(DashEvent(Constants.EXPORT_CONFIG_EVENT, NotificationType.WARN))
        return jsonFileResponse("dashboardConfig", jsonMapper.writeValueAsBytes(config))
    }

    @PostMapping("/import")
    fun importConfig(
        @RequestParam file: MultipartFile
    ): Boolean {
        logger.info("Import commencé")
        val importData = jsonMapper.readValue<ImportData>(file.bytes)
        val widgetsByTabId = importData.widgets.groupBy { it.tabId }
        importData.tabs.forEach { tab ->
            val insertedTab = tabService.importTab(tab.label, tab.tabOrder)
            widgetsByTabId[tab.id].orEmpty().forEach { widget ->
                widgetService.importWidget(widget.type, widget.widgetOrder, widget.data, insertedTab.id)
            }
        }
        logger.info("Import terminé")
        return true
    }
}
