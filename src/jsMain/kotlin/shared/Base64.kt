package shared

import api.ApiClient
import jezorko.github.pregnancyroulette.PregnancyOutcome
import kotlin.js.Promise

private external fun btoa(input: String): String
private external fun atob(input: String): String

object Base64 {

    fun encode(input: String) = btoa(input)
    fun decode(input: String) = atob(input)

}

object OutcomesSerializer {
    fun serializeOutcomes(outcomes: List<PregnancyOutcome>): Promise<String> =
        ApiClient.allOutcomes.then { allOutcomes ->
            outcomes.map { outcome ->
                allOutcomes.indexOfFirst { outcome.name == it.name }
            }.joinToString(",")
        }.then(Base64::encode)

    fun deserializeOutcomes(outcomes: String): Promise<List<PregnancyOutcome>> =
        ApiClient.allOutcomes.then { allOutcomes ->
            Base64.decode(outcomes)
                .split(",")
                .map(String::toInt)
                .map(allOutcomes::get)
        }
}