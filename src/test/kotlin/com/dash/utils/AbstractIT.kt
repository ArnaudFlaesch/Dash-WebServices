package com.dash.utils
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Testcontainers

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class AbstractIT {
    internal constructor()

    companion object {
        private var postgresDBContainer: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:13.2-alpine")
            .withDatabaseName("dash_test")
            .withUsername("postgres")
            .withPassword("postgres")

        @DynamicPropertySource
        @JvmStatic
        fun registerPgProperties(registry: DynamicPropertyRegistry) {
            postgresDBContainer.start()
            registry.add(
                "spring.datasource.url"
            ) { postgresDBContainer.jdbcUrl }
            registry.add("spring.datasource.username") { postgresDBContainer.username }
            registry.add("spring.datasource.password") { postgresDBContainer.password }
        }
    }
}
