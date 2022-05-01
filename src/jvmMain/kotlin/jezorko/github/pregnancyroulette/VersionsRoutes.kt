package jezorko.github.pregnancyroulette

import io.ktor.application.*
import io.ktor.routing.*

fun Application.versionsRoutes() = routing {
    get(Routes.VersionsRoute.path) {
        call.respondJson(provider = {
            VersionInfo(
                repositoryUrl = Configuration.PROJECT_GIT_REPOSITORY.value,
                commitSlug = Configuration.HEROKU_SLUG_COMMIT.value
            )
        })
    }
}