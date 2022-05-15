package jezorko.github.pregnancyroulette.suggestions

data class OutcomeSuggestion(
    val suggestionSourceIp: String,
    val createdAtTimestamp: Long,
    val name: String,
    val description: String,
    val chance: String,
    val referenceUrl: String
)