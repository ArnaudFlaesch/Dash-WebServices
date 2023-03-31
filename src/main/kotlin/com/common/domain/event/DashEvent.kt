package com.common.domain.event

import com.common.domain.model.EventTypeEnum
import org.springframework.context.ApplicationEvent

class DashEvent(source: Any, val messageContent: String, val eventType: EventTypeEnum) : ApplicationEvent(source)
