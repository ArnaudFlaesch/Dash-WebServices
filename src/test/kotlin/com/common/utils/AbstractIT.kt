package com.common.utils

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.jdbc.Sql
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Testcontainers

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@Sql(scripts = ["classpath:./data/dash-data.sql", "classpath:./data/cashmanager-data.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class AbstractIT {
    internal constructor()

    companion object {
        // @FIXME remove <Nothing> when Kotlin 1.7 is used
        // and revert to .with() methods
        private var postgresDBContainer: PostgreSQLContainer<*> = PostgreSQLContainer<Nothing>("postgres:13.2-alpine").apply {
            withDatabaseName("dash_test")
            withUsername("postgres")
            withPassword("postgres")
        }

        @DynamicPropertySource
        @JvmStatic
        fun registerPgProperties(registry: DynamicPropertyRegistry) {
            mockServerContainer.start()
            postgresDBContainer.start()
            registry.add(
                "spring.datasource.url"
            ) { postgresDBContainer.jdbcUrl }
            registry.add("spring.datasource.username") { postgresDBContainer.username }
            registry.add("spring.datasource.password") { postgresDBContainer.password }
        }
    }
}
