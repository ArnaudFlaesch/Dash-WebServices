import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.TestInstance
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

    init {
        postgreDBContainer.start()
    }

    companion object {
        var postgreDBContainer: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:13.2-alpine")
            .withDatabaseName("dash_test")
            .withUsername("postgres")
            .withPassword("postgres")

        @DynamicPropertySource
        @JvmStatic
        fun registerPgProperties(registry: DynamicPropertyRegistry) {
            registry.add(
                "spring.datasource.url"
            ) { postgreDBContainer.jdbcUrl }
            registry.add("spring.datasource.username") { postgreDBContainer.username }
            registry.add("spring.datasource.password") { postgreDBContainer.password }
        }
    }
}
