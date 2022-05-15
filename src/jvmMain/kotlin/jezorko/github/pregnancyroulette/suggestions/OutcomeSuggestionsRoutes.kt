package jezorko.github.pregnancyroulette.suggestions

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.html.*
import io.ktor.response.*
import io.ktor.routing.*
import jezorko.github.pregnancyroulette.*
import kotlinx.html.*
import java.lang.System.currentTimeMillis
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

private val submitterIpToLastSubmittedTimestamp = ConcurrentHashMap<String, Long>()

private object RequestParameters {
    const val name = "name"
    const val description = "description"
    const val chance = "chance"
    const val referenceUrl = "reference"
}

fun Application.outcomeSuggestionsRoutes() = routing {
    get(Routes.SuggestionsRoute.path) {
        call.respondHtml {
            head {
                title("${Constants.APPLICATION_TITLE} - submit a suggestion")
                attachDefaultTagsTo(this)
                meta {
                    name = "description"
                    content = "Submit a new suggestion for ${Constants.APPLICATION_TITLE}"
                }
                meta {
                    attributes["property"] = "og:description"
                    content = "Submit a new suggestion for ${Constants.APPLICATION_TITLE}"
                }
            }
            body {
                form {
                    method = FormMethod.post

                    h1 {
                        +"Submit a suggestion for pregnancy outcome"
                    }

                    br {}

                    label {
                        attributes["for"] = RequestParameters.name
                        +"Name "
                    }
                    input {
                        type = InputType.text
                        required = true
                        name = RequestParameters.name
                        maxLength = "255"
                        placeholder = "Badoutcomeosis"
                    }

                    br {}

                    label {
                        attributes["for"] = RequestParameters.description
                        +"Description "
                    }
                    input {
                        type = InputType.text
                        required = true
                        name = RequestParameters.description
                        maxLength = "16383"
                        placeholder = "Something awful that happens"
                    }

                    br {}

                    label {
                        attributes["for"] = RequestParameters.chance
                        +"Chance "
                    }
                    input {
                        type = InputType.text
                        required = true
                        name = RequestParameters.chance
                        maxLength = "255"
                        placeholder = "1 in 1000"
                    }

                    br {}

                    label {
                        attributes["for"] = RequestParameters.referenceUrl
                        +"Reference URL "
                    }
                    input {
                        type = InputType.text
                        required = true
                        name = RequestParameters.referenceUrl
                        maxLength = "255"
                        placeholder = "https://www.ncbi.nlm.nih.gov/pmc/articles/someArticle"
                    }

                    br {}

                    input {
                        type = InputType.submit
                        value = "Submit"
                    }
                }
            }
        }
    }
    post(Routes.SuggestionsRoute.path) {
        val submitterIp = call.request.local.remoteHost
        val currentTimestamp = currentTimeMillis()
        val lastSubmittedTimestamp = submitterIpToLastSubmittedTimestamp.put(submitterIp, currentTimestamp) ?: 0L
        if (currentTimestamp - lastSubmittedTimestamp > TimeUnit.MINUTES.toMillis(1)) {
            try {
                val body = bodyAsForm()
                OutcomeSuggestionsRepository.save(
                    OutcomeSuggestion(
                        suggestionSourceIp = submitterIp,
                        createdAtTimestamp = currentTimestamp,
                        name = body[RequestParameters.name]!!,
                        description = body[RequestParameters.description]!!,
                        chance = body[RequestParameters.chance]!!,
                        referenceUrl = body[RequestParameters.referenceUrl]!!
                    )
                )
                call.respondRedirect(Routes.AcceptedSuggestionsRoute.path)
            } catch (exception: Exception) {
                log.error("failed to submit a suggestion", exception)
                call.respondRedirect(Routes.RejectedSuggestionsRoute.path)
            }
        } else {
            submitterIpToLastSubmittedTimestamp[submitterIp] = lastSubmittedTimestamp
            call.respondRedirect(Routes.RejectedSuggestionsRoute.path)
        }
    }
    get(Routes.AcceptedSuggestionsRoute.path) {
        call.respondHtml {
            head {
                title("${Constants.APPLICATION_TITLE} - thank you for your suggestion!")
                attachDefaultTagsTo(this)
                meta {
                    name = "description"
                    content = "Suggestion for ${Constants.APPLICATION_TITLE} successfully accepted"
                }
                meta {
                    attributes["property"] = "og:description"
                    content = "Suggestion for ${Constants.APPLICATION_TITLE} successfully accepted"
                }
            }
            body {
                h1 {
                    +"Thank you for your suggestion!"
                }
                div {
                    +"It will now be reviewed and (if there are no issues) added to the website in a few days."
                }
                br {}
                button {
                    onClick = "window.close();"
                    +"Coolio, thanks!"
                }
            }
        }
    }
    get(Routes.RejectedSuggestionsRoute.path) {
        call.respondHtml {
            head {
                title("${Constants.APPLICATION_TITLE} - suggestion rejected!")
                attachDefaultTagsTo(this)
                meta {
                    name = "description"
                    content = "Suggestion for ${Constants.APPLICATION_TITLE} has been rejected"
                }
                meta {
                    attributes["property"] = "og:description"
                    content = "Suggestion for ${Constants.APPLICATION_TITLE} has been rejected"
                }
            }
            body {
                h1 {
                    +"Your suggestion cannot be accepted at this time."
                }
                div {
                    +"Please try again in a few minutes."
                }
                br {}
                button {
                    onClick = "history.back();"
                    +"Go back to suggestion screen"
                }
            }
        }
    }
    get(Routes.AllSuggestionsRoute.path) {
        requireAdmin {
            call.respondJson { OutcomeSuggestionsRepository.findAll() }
        }
    }
    delete(Routes.AllSuggestionsRoute.path) {
        requireAdmin {
            OutcomeSuggestionsRepository.deleteAll()
            call.respondText { "all suggestions deleted" }
        }
    }
}