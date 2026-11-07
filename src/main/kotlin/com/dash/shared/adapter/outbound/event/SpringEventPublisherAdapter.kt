package com.dash.shared.adapter.outbound.event

import com.dash.shared.application.port.outbound.EventPublisherPort
import com.dash.shared.domain.event.DashEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

/** Publishes the events as Spring application events, received by the @EventListener adapters. */
@Component
class SpringEventPublisherAdapter(
    private val applicationEventPublisher: ApplicationEventPublisher
) : EventPublisherPort {
    override fun publish(event: DashEvent) {
        applicationEventPublisher.publishEvent(event)
    }
}
