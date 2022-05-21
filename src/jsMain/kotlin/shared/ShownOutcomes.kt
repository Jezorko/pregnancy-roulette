package shared

import kotlinx.browser.localStorage
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

private const val shownOutcomesLocalStorageKey = "shownOutcomes"

object ShownOutcomes {

    fun checkNew(outcomeName: String): Boolean {
        try {
            val outcomeNames = localStorage.getItem(
                shownOutcomesLocalStorageKey
            )?.let { json.decodeFromString<OutcomeNames>(it) }
                ?: OutcomeNames.empty()

            val outcomeAlreadyShown = outcomeNames.contains(outcomeName)
            if (!outcomeAlreadyShown) {
                localStorage.setItem(
                    shownOutcomesLocalStorageKey,
                    json.encodeToString(outcomeNames.with(outcomeName))
                )
            }
            return !outcomeAlreadyShown
        } catch (error: Throwable) {
            localStorage.removeItem(shownOutcomesLocalStorageKey)
            return false
        }
    }

    @Serializable
    data class OutcomeNames(private val data: Set<String>) {

        companion object {
            fun empty() = OutcomeNames(emptySet())
        }

        fun contains(outcomeName: String) = data.contains(outcomeName)
        fun with(outcomeName: String) = copy(data = data + setOf(outcomeName))

    }

}