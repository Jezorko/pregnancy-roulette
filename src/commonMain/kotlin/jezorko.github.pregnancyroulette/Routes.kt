package jezorko.github.pregnancyroulette

sealed interface Route {
    val path: String
}

sealed class Route0(override val path: String) : Route

object Routes {

    object VersionsRoute : Route0("/versions")
    object PregnancyRisksRoute : Route0("/static/pregnancy_risks.json")

}