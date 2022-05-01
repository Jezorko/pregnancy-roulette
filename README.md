[![Master Build Status](https://img.shields.io/github/checks-status/Jezorko/pregnancy-roulette/master?label=build&logo=github)](https://github.com/Jezorko/pregnancy-roulette/actions?query=branch%3Amaster)
[![Heroku Deployment](https://img.shields.io/github/workflow/status/Jezorko/pregnancy-roulette/Deploy%20application%20to%20Heroku?label=deployment&logo=heroku)](https://pregnancy-roulette.herokuapp.com/)
[![License: WTFPL](https://img.shields.io/badge/License-WTFPL-red.svg)](http://www.wtfpl.net/txt/copying/)

# Pregnancy Roulette

## Running

### Locally

The application can be run locally using Gradle.

```shell
./gradlew run
```

### Production

Build the production files using either `stage` or `installDist` command.

```shell
./gradlew stage
```

Then execute the generated binaries.

```shell
build/install/pregnancy-roulette/bin/pregnancy-roulette
```

## Configuration

The configuration is defined by the [Configuration](src/jvmMain/kotlin/jezorko/github/pregnancyroulette/Configuration.kt) object.
Configuration parameters are fetched from environment variables.