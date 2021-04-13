package com.dash.service

import com.dash.entity.WidgetType
import com.dash.repository.WidgetTypeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class WidgetTypeService {
    @Autowired
    private lateinit var widgetTypeRepository: WidgetTypeRepository

    fun getAllWidgetTypes(): List<WidgetType> {
        return widgetTypeRepository.findAll()
    }
}
