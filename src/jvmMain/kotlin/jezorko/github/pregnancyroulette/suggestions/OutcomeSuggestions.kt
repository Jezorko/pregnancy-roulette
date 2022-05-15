package jezorko.github.pregnancyroulette.suggestions

import org.jetbrains.exposed.dao.id.UUIDTable

internal object OutcomeSuggestions : UUIDTable() {

    val suggestionSourceIp = varchar("source_ip", 255)
    val createdAtTimestamp = long("created_at")

    val name = varchar("name", 255)
    val description = varchar("description", 16383)
    val chance = varchar("chance", 255)
    val referenceUrl = varchar("reference_url", 8191)

}