package com.dash.shared.application.port.outbound

import com.dash.shared.domain.event.DashEvent

fun interface EventPublisherPort {
    fun publish(event: DashEvent)
}
