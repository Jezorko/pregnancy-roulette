package jezorko.github.pregnancyroulette.database

import io.ktor.application.*
import io.ktor.http.HttpStatusCode.Companion.InternalServerError
import io.ktor.response.*
import io.ktor.routing.*
import jezorko.github.pregnancyroulette.requireAdmin

fun Application.databaseRoutes() = routing {
    delete("/api/databases") {
        requireAdmin {
            try {
                Database.recreateTables()
                call.respondText { "tables recreated successfully" }
            } catch (exception: Exception) {
                call.respondText(status = InternalServerError) { "failed to recreate tables" }
            }
        }
    }
}