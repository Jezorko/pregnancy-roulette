package jezorko.github.pregnancyroulette

interface Base64 {
    fun encode(input: String): String
    fun decode(input: String): String
}

private fun flattenOutcomes(outcomes: List<PregnancyOutcome>): List<PregnancyOutcome> = outcomes.flatMap { outcome ->
    listOf(outcome) + flattenOutcomes(outcome.consecutiveOutcomes)
}

class OutcomesSerializer(private val base64: Base64, private val allOutcomes: List<PregnancyOutcome>) {
    fun serializeOutcomes(outcomes: List<PregnancyOutcome>) = outcomes.map { outcome ->
        allOutcomes.indexOfFirst { outcome.name == it.name }
    }.joinToString(",").let(base64::encode)

    fun deserializeOutcomes(outcomes: String): List<PregnancyOutcome> = base64.decode(outcomes)
        .let { decodedOutcomes ->
            if (decodedOutcomes.isEmpty()) emptyList()
            else if (decodedOutcomes == "EVERYTHING") flattenOutcomes(allOutcomes)
            else decodedOutcomes.split(",")
                .map(String::toInt)
                .map(allOutcomes::get)
        }

}