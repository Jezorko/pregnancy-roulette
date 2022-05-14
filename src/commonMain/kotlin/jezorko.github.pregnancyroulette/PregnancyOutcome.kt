package jezorko.github.pregnancyroulette

import kotlinx.serialization.Serializable

val healthyBaby = PregnancyOutcome(
    name = "Congratulations!",
    description = "You were lucky enough to birth a healthy baby and have no negative outcomes, but remember that this list is not exhaustive.",
    commonNames = emptyList(),
    isNegative = false,
    tags = Tags(),
    images = listOf(
        PregnancyOutcomeImage(
            url = "https://mothertobaby.org/wp-content/uploads/2020/06/MotherToBaby-Homepage-Header-1600x900-1.jpg",
            description = "Parent holding a healthy baby.",
            source = "https://mothertobaby.org/"
        )
    ),
    chance = 0.0,
    references = emptyList(),
    consecutiveOutcomes = emptyList()
)

@Serializable
data class Tags(
    val own: Set<String> = emptySet(),
    val excludes: Set<String> = emptySet()
)

@Serializable
data class PregnancyOutcome(
    val name: String,
    val commonNames: List<String>,
    val description: String,
    val isNegative: Boolean,
    val tags: Tags,
    val images: List<PregnancyOutcomeImage>,
    val chance: Double,
    val references: List<PregnancyOutcomeReference>,
    val consecutiveOutcomes: List<PregnancyOutcome>
) {
    init {
        if (chance < 0 || chance > 1) {
            throw IllegalArgumentException("chance of outcome $name must be between 0 and 1")
        }
    }
}

@Serializable
data class PregnancyOutcomeImage(
    val url: String,
    val description: String,
    val source: String
)

@Serializable
data class PregnancyOutcomeReference(
    val name: String,
    val authors: List<String>,
    val url: String
) {
    override fun toString(): String {
        return if (authors.isEmpty()) "$name."
        else "${authors.joinToString(", ")}. $name."
    }
}