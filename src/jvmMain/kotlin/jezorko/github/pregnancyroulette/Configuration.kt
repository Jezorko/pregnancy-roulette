package jezorko.github.pregnancyroulette

import kotlin.reflect.KProperty0

interface ConfigurationVariable<T> {
    val value: T
}

class EnvironmentVariable<T>(
    private val thisProperty: KProperty0<EnvironmentVariable<T>>,
    private val defaultValue: T,
    private val mapper: (String) -> T
) : ConfigurationVariable<T> {
    override val value: T
        get() = System.getenv(thisProperty.name.uppercase()).let { if (it != null) mapper(it) else defaultValue }
}


object Configuration {

    val PORT: EnvironmentVariable<Int> = EnvironmentVariable(
        Configuration::PORT,
        3000,
        String::toInt
    )

    private val HEROKU_APP_NAME: EnvironmentVariable<String?> = EnvironmentVariable(
        Configuration::HEROKU_APP_NAME,
        null,
        String::toString
    )

    val APPLICATION_URL: EnvironmentVariable<String> = EnvironmentVariable(
        Configuration::APPLICATION_URL,
        HEROKU_APP_NAME.value?.let { "https://$it.herokuapp.com/" } ?: "unknown",
        String::toString
    )

    val APPLICATION_IMAGE_URL: EnvironmentVariable<String> = EnvironmentVariable(
        Configuration::APPLICATION_IMAGE_URL,
        "static/logo.svg",
        String::toString
    )

    val HEROKU_SLUG_COMMIT: EnvironmentVariable<String> = EnvironmentVariable(
        Configuration::HEROKU_SLUG_COMMIT,
        "unknown",
        String::toString
    )

    val PROJECT_GIT_REPOSITORY: EnvironmentVariable<String> = EnvironmentVariable(
        Configuration::PROJECT_GIT_REPOSITORY,
        "https://github.com/Jezorko/pregnancy-roulette",
        String::toString
    )

}