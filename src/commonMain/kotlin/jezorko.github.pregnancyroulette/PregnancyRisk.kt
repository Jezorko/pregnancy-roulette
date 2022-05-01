package jezorko.github.pregnancyroulette

import kotlinx.serialization.Serializable

val healthyBaby = PregnancyRisk(
    name = "Congratulations!",
    description = "You were lucky enough to birth a healthy baby, but remember that this list of potential side-effects is not exhaustive.",
    commonNames = emptyList(),
    images = listOf(
        PregnancyRiskImage(
            url = "https://mothertobaby.org/wp-content/uploads/2020/06/MotherToBaby-Homepage-Header-1600x900-1.jpg",
            description = "Parent holding a healthy baby.",
            source = "https://mothertobaby.org/"
        )
    ),
    chance = 0.0,
    references = emptyList()
)

@Serializable
data class PregnancyRisk(
    val name: String,
    val commonNames: List<String>,
    val description: String,
    val images: List<PregnancyRiskImage>,
    val chance: Double,
    val references: List<PregnancyRiskReference>
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
        return "${authors.joinToString(", ")}. $name."
    }
}