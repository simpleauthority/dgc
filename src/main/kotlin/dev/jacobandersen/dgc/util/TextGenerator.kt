package dev.jacobandersen.dgc.util

import java.util.concurrent.ThreadLocalRandom

object TextGenerator {
    private val random = ThreadLocalRandom.current()

    fun generate(
        templates: List<String>,
        components: Map<String, List<String>>,
        variables: Map<String, String>
    ): String {
        var message = chooseRandom(templates)

        components.forEach { (key, value) ->
            while (message.contains("{${key}}")) {
                var random = chooseRandom(value)
                while (message.contains(random)) {
                    random = chooseRandom(value)
                }

                message = message.replaceFirst("{${key}}", random)
            }
        }

        variables.forEach { (key, value) -> message = message.replace("{${key}}", value) }

        return message
    }

    private fun chooseRandom(choices: List<String>): String {
        if (choices.size == 1) {
            return choices[0]
        }

        return choices[random.nextInt(choices.size)]
    }
}