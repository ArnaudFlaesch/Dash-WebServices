package com.dash.shared.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Clock

@Configuration
class SharedConfiguration {
    /** The time source of the use cases, injected so they stay deterministic in tests. */
    @Bean
    fun clock(): Clock = Clock.systemDefaultZone()
}
