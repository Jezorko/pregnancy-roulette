package jezorko.github.pregnancyroulette

object Constants {
    const val APPLICATION_TITLE = "Pregnancy Roulette"
    fun applicationDescription(outcomes: List<PregnancyOutcome>?) =
        if (outcomes == null) {
            "Spin the wheel and see which side-effects of pregnancy you can get!"
        } else if (outcomes.isEmpty())
            "My simulated pregnancy was a full success!" +
                    " Spin the wheel and see how lucky YOU are!"
        else
            "My simulated pregnancy ended up with ${
                outcomes.filter { it.isNegative }.size.let { negativeOutcomesCount ->
                    val positiveOutcomesCount = outcomes.size - negativeOutcomesCount
                    ((if (positiveOutcomesCount == 0) emptyList()
                    else listOf("$positiveOutcomesCount positive")) +
                            (if (negativeOutcomesCount == 0) emptyList()
                            else listOf("$negativeOutcomesCount negative"))
                            ).joinToString(" and ")
                }
            } outcomes!" +
                    " Spin the wheel and see how many you can get!"

}