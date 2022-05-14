package jezorko.github.pregnancyroulette

import com.fasterxml.jackson.core.type.TypeReference


val outcomesSerializer = deserializeJsonResource(
    Routes.PregnancyOutcomesRoute.path.substring(1),
    object : TypeReference<List<PregnancyOutcome>>() {}
).let { allOutcomes ->
    OutcomesSerializer(
        object : Base64 {
            override fun encode(input: String): String = String(
                java.util.Base64.getEncoder().encode(input.toByteArray())
            )

            override fun decode(input: String): String = String(
                java.util.Base64.getDecoder().decode(input.toByteArray())
            )

        },
        allOutcomes
    )
}