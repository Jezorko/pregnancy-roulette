package jezorko.github.pregnancyroulette.database

import org.testcontainers.containers.PostgreSQLContainer

private const val credential = "admin"
private val container = PostgreSQLContainer("postgres:latest")
    .withDatabaseName("postgres-local")
    .withUsername(credential)
    .withPassword(credential)

class PostgresLocal : DatabaseConfiguration {

    init {
        container.start()
    }

    override val jdbcUrl: String = container.jdbcUrl
    override val username = credential
    override val password = credential

}