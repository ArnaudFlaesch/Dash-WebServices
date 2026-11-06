package com.dash.app.controller

import com.common.app.controller.jsonFileResponse
import com.common.app.security.SecurityConditions
import com.common.domain.event.DashEvent
import com.dash.domain.model.config.ImportData
import com.dash.domain.model.notification.NotificationType
import com.dash.domain.service.DashConfigService
import com.dash.domain.utils.Constants
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
    private val dashConfigService: DashConfigService,
    private val jsonMapper: JsonMapper
) {
    @GetMapping("/export")
    fun downloadJsonFile(): ResponseEntity<ByteArray> {
        val config = dashConfigService.exportConfig()
        applicationEventPublisher.publishEvent(DashEvent(Constants.EXPORT_CONFIG_EVENT, NotificationType.WARN))
        return jsonFileResponse("dashboardConfig", jsonMapper.writeValueAsBytes(config))
    }

    @PostMapping("/import")
    fun importConfig(
        @RequestParam file: MultipartFile
    ): Boolean {
        dashConfigService.importConfig(jsonMapper.readValue<ImportData>(file.bytes))
        return true
    }
}
