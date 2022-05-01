package components

import api.ApiClient.getVersionInfo
import jezorko.github.pregnancyroulette.VersionInfo
import react.FC
import react.Props
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.footer
import react.useState

val versionInfoPromise = getVersionInfo()

val Footer = FC<Props> { _ ->

    var versionInfoState: VersionInfo? by useState(null)
    if (versionInfoState == null) {
        versionInfoPromise.then {
            versionInfoState = it
        }
    }

    val buildVersionInfoId = "build-version-info"
    val versionInfo = versionInfoState

    footer {
        id="footer"

        if (versionInfo == null) {
            div {
                +"Built from unknown commit"
                id = buildVersionInfoId
            }
        } else {
            div {
                +"Built from "
                id = buildVersionInfoId
                a {
                    id = "$buildVersionInfoId-link"
                    val commitUrl = "${versionInfo.repositoryUrl}/commit/${versionInfo.commitSlug}"
                    +versionInfo.commitSlug.substring(0, 7)
                    href = commitUrl
                }
            }
        }
    }
}