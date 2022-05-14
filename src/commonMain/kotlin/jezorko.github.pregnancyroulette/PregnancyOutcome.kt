package jezorko.github.pregnancyroulette

import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
data class Tags(
    val own: Set<String> = emptySet(),
    val excludes: Set<String> = emptySet()
)

data class Chance(val value: String) {
    fun test(random: Random = Random) = when (value) {
        in Regex("\\d+ in \\d+") -> {
            val split = value.split(" in ")
            split[0].toInt() >= random.nextInt(split[1].toInt()) + 1
        }
        in Regex("\\d+%") -> {
            value.removeSuffix("%").toInt() >= random.nextInt(100) + 1
        }
        else -> throw IllegalArgumentException("invalid chance value $value")
    }

    override fun toString() = value

    private operator fun Regex.contains(text: CharSequence): Boolean = matches(text)
}

@Serializable
data class PregnancyOutcome(
    val name: String,
    val commonNames: List<String>,
    val description: String,
    val isNegative: Boolean,
    val tags: Tags,
    val images: List<PregnancyOutcomeImage>,
    val chance: String,
    val references: List<PregnancyOutcomeReference>,
    val consecutiveOutcomes: List<PregnancyOutcome>
) {
    val parsedChance get() = Chance(chance)
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