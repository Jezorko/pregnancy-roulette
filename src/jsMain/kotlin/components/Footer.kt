package components

import api.ApiClient.versionInfo
import jezorko.github.pregnancyroulette.VersionInfo
import kotlinx.browser.window
import react.FC
import react.Props
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.footer
import react.dom.html.ReactHTML.span
import react.useState

val Footer = FC<Props> { _ ->

    var versionInfoState: VersionInfo? by useState(null)
    if (versionInfoState == null) {
        versionInfo.then { versionInfoState = it }
    }

    val versionInfo = versionInfoState

    footer {
        id = "footer"

        val buildVersionInfoId = "build-version-info"
        if (versionInfo == null) {
            span {
                +"Built from unknown commit"
                id = buildVersionInfoId
                className = "footer-element"
            }
        } else {
            span {
                +"Built from "
                id = buildVersionInfoId
                className = "footer-element"
                a {
                    id = "$buildVersionInfoId-link"
                    className = "footer-element"
                    val commitUrl = "${versionInfo.repositoryUrl}/commit/${versionInfo.commitSlug}"
                    +versionInfo.commitSlug.substring(0, 7)
                    href = commitUrl
                }
            }
        }

        span {
            +"Inspired by"
            className = "footer-element"
            a {
                className = "footer-element"
                +"r/childfree"
                href = "https://www.reddit.com/r/childfree"
            }
            a {
                className = "footer-element"
                +"r/antinatalism"
                href = "https://www.reddit.com/r/antinatalism/"
            }
            a {
                className = "footer-element"
                +"StopHavingKids.org"
                href = "https://www.stophavingkids.org/"
            }
        }

        span {
            +"See all possible outcomes"
            className = "footer-element"
            a {
                className = "footer-element"
                +"here"
                href = "/?outcomes=RVZFUllUSElORw=="
            }
        }

        span {
            className = "footer-element"
            a {
                className = "footer-element"
                +"Suggest an outcome"
                href="/suggestions"
                onClick = {
                    window.open(
                        "/suggestions",
                        "pop",
                        "width=600, height=400"
                    )
                }
            }
        }
    }
}