package jezorko.github.pregnancyroulette.suggestions

import jezorko.github.pregnancyroulette.database.Database.doInTransaction
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import java.util.*

internal object OutcomeSuggestionsRepository {

    internal fun findAll() = doInTransaction {
        OutcomeSuggestions.selectAll().map {
            OutcomeSuggestion(
                suggestionSourceIp = it[OutcomeSuggestions.suggestionSourceIp],
                createdAtTimestamp = it[OutcomeSuggestions.createdAtTimestamp],
                name = it[OutcomeSuggestions.name],
                description = it[OutcomeSuggestions.description],
                chance = it[OutcomeSuggestions.chance],
                referenceUrl = it[OutcomeSuggestions.referenceUrl]
            )
        }
    }

    internal fun save(suggestion: OutcomeSuggestion) = doInTransaction {
        OutcomeSuggestions.insert {
            it[id] = UUID.randomUUID()
            it[createdAtTimestamp] = suggestion.createdAtTimestamp
            it[suggestionSourceIp] = suggestion.suggestionSourceIp
            it[name] = suggestion.name
            it[description] = suggestion.description
            it[chance] = suggestion.chance
            it[referenceUrl] = suggestion.referenceUrl
        }
    }

    internal fun deleteAll() = doInTransaction { OutcomeSuggestions.deleteAll() }

}