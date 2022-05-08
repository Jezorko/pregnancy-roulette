package jezorko.github.pregnancyroulette

import kotlinx.serialization.Serializable

val healthyBaby = PregnancyRisk(
    name = "Congratulations!",
    description = "You were lucky enough to birth a healthy baby and have no side effects, but remember that this list is not exhaustive.",
    commonNames = emptyList(),
    tags = Tags(),
    images = listOf(
        PregnancyRiskImage(
            url = "https://mothertobaby.org/wp-content/uploads/2020/06/MotherToBaby-Homepage-Header-1600x900-1.jpg",
            description = "Parent holding a healthy baby.",
            source = "https://mothertobaby.org/"
        )
    ),
    chance = 0.0,
    references = emptyList(),
    consecutiveRisks = emptyList()
)

@Serializable
data class Tags(
    val own: Set<String> = emptySet(),
    val excludes: Set<String> = emptySet()
)

@Serializable
data class PregnancyRisk(
    val name: String,
    val commonNames: List<String>,
    val description: String,
    val tags: Tags,
    val images: List<PregnancyRiskImage>,
    val chance: Double,
    val references: List<PregnancyRiskReference>,
    val consecutiveRisks: List<PregnancyRisk>
) {
    init {
        if (chance < 0 || chance > 1) {
            throw IllegalArgumentException("chance of risk $name must be between 0 and 1")
        }
    }
}

@Serializable
data class PregnancyRiskImage(
    val url: String,
    val description: String,
    val source: String
)

@Serializable
data class PregnancyRiskReference(
    val name: String,
    val authors: List<String>,
    val url: String
) {
    override fun toString(): String {
        return if (authors.isEmpty()) "$name."
        else "${authors.joinToString(", ")}. $name."
    }
}