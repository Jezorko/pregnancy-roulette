package api

import jezorko.github.pregnancyroulette.PregnancyRisk
import jezorko.github.pregnancyroulette.Routes
import jezorko.github.pregnancyroulette.VersionInfo
import kotlinx.browser.window
import kotlinx.serialization.decodeFromString
import org.w3c.fetch.RequestInit
import shared.flatThen
import shared.json
import kotlin.js.Promise

object ApiClient {

    fun getVersionInfo(): Promise<VersionInfo> {
        return window.fetch(
            Routes.VersionsRoute.path,
            object : RequestInit {
                override var method: String? = "GET"
            }
        ).flatThen { response ->
            when (response.status.toInt()) {
                200 -> response.text()
                    .then { responseBodyAsText -> json.decodeFromString(responseBodyAsText) }
                else -> Promise.resolve(VersionInfo("unknown", "unknown")).also {
                    console.error("cannot resolve version information, received HTTP ${response.status}")
                }
            }
        }
    }

    fun getAllRisks(): Promise<List<PregnancyRisk>> = window.fetch(
        Routes.PregnancyRisksRoute.path,
        object : RequestInit {}.apply {
            method = "GET"
        }
    ).flatThen { response ->
        when (response.status.toInt()) {
            200 -> response.text()
                .then { responseBodyAsText -> json.decodeFromString(responseBodyAsText) }
            else -> Promise.reject(
                IllegalStateException("cannot resolve pregnancy risks, received HTTP ${response.status}")
            )
        }
    }

}