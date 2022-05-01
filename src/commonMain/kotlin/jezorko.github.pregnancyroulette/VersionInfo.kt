package jezorko.github.pregnancyroulette

import kotlinx.serialization.Serializable

@Serializable
data class VersionInfo(val repositoryUrl: String, val commitSlug: String)