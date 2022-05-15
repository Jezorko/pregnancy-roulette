package jezorko.github.pregnancyroulette.database

import jezorko.github.pregnancyroulette.Configuration
import jezorko.github.pregnancyroulette.Environment
import jezorko.github.pregnancyroulette.suggestions.OutcomeSuggestions
import mu.KotlinLogging.logger
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlLogger
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.statements.StatementContext
import org.jetbrains.exposed.sql.statements.expandArgs
import org.jetbrains.exposed.sql.transactions.transaction

interface DatabaseConfiguration {
    val jdbcUrl: String
    val username: String
    val password: String
}

object Database {

    private val log = logger {}
    private val sqlLogger = object : SqlLogger {
        override fun log(context: StatementContext, transaction: Transaction) {
            log.info { "executing SQL: ${context.expandArgs(transaction)}" }
        }
    }
    private val configuration = when (Configuration.ENVIRONMENT.value) {
        Environment.HEROKU -> PostgresHeroku()
        Environment.LOCAL -> PostgresLocal()
    }

    private val registeredTables = setOf(OutcomeSuggestions)

    init {
        doInTransaction {
            registeredTables.forEach(SchemaUtils::create)
        }
    }

    fun recreateTables() = doInTransaction {
        log.info { "recreating tables" }
        addLogger(sqlLogger)
        registeredTables.forEach { table ->
            log.info { "recreating ${table.tableName}" }
            exec("DROP TABLE IF EXISTS ${table.tableName}")
            SchemaUtils.create(table)
        }
        log.info { "tables recreated" }
    }

    fun <T> doInTransaction(operation: Transaction.() -> T) = getConnection().run {
        transaction {
            if (Configuration.LOG_SQL_QUERIES.value) addLogger(sqlLogger)
            operation(this)
        }
    }

    private fun getConnection() = org.jetbrains.exposed.sql.Database.connect(
        url = configuration.jdbcUrl,
        user = configuration.username,
        password = configuration.password
    )

}