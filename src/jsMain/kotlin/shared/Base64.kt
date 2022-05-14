package shared

import api.ApiClient
import jezorko.github.pregnancyroulette.OutcomesSerializer

private external fun btoa(input: String): String
private external fun atob(input: String): String

object Base64 : jezorko.github.pregnancyroulette.Base64 {

    override fun encode(input: String) = btoa(input)
    override fun decode(input: String) = atob(input)

}

val outcomesSerializerPromise = ApiClient.allOutcomes.then { allOutcomes ->
    OutcomesSerializer(Base64, allOutcomes)
}
