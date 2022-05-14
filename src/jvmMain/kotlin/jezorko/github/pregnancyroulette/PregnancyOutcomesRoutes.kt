package jezorko.github.pregnancyroulette

import com.fasterxml.jackson.core.type.TypeReference
import io.ktor.application.*
import io.ktor.routing.*
import java.lang.Thread.currentThread

fun Application.pregnancyOutcomesRoutes() = routing {
    get(Routes.PregnancyOutcomesRoute.path) {

        call.respondJson(provider = {
            VersionInfo(
                repositoryUrl = Configuration.PROJECT_GIT_REPOSITORY.value,
                commitSlug = Configuration.HEROKU_SLUG_COMMIT.value
            )
        })
    }
}

fun main() {
    val outcomesJson = currentThread().contextClassLoader.getResourceAsStream(
        "static/pregnancy_outcomes.json"
    )
    val outcomes = json.readValue(outcomesJson, object : TypeReference<List<PregnancyOutcome>>() {
    })

    println(json.writeValueAsString(
        outcomes.map {it.name to it}.toMap()
    ))

//    println(toml.writeValueAsString(
//        outcomes.map {it.name to it}.toMap()
//    ))
}