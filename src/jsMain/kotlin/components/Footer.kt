package components

import api.ApiClient.versionInfo
import jezorko.github.pregnancyroulette.VersionInfo
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

        val inspiredById = "inspired-by"
        span {
            +"Inspired by"
            id = inspiredById
            className = "footer-element"
            a {
                id = "$inspiredById-childfree-link"
                className = "footer-element"
                +"r/childfree"
                href = "https://www.reddit.com/r/childfree"
            }
            a {
                id = "$inspiredById-antinatalism-link"
                className = "footer-element"
                +"r/antinatalism"
                href = "https://www.reddit.com/r/antinatalism/"
            }
        }
    }
}