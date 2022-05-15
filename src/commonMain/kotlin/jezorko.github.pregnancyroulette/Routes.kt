package jezorko.github.pregnancyroulette

sealed interface Route {
    val path: String
}

sealed class Route0(override val path: String) : Route

object Routes {

    object VersionsRoute : Route0("/versions")
    object PregnancyOutcomesRoute : Route0("/static/pregnancy_outcomes.json")
    object AcceptedSuggestionsRoute : Route0("/accepted-suggestions")
    object RejectedSuggestionsRoute : Route0("/rejected-suggestions")
    object SuggestionsRoute : Route0("/suggestions")
    object AllSuggestionsRoute : Route0("/suggestions/all")

}

const val outcomesParamName = "outcomes"