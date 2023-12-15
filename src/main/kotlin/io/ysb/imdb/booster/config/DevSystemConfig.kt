package io.ysb.imdb.booster.config

import io.github.cdimascio.dotenv.dotenv
import org.springframework.context.annotation.Configuration

@Configuration
class DevSystemConfig {

    @Suppress("unused")
    private val dotenv = dotenv {
        ignoreIfMissing = true
        ignoreIfMalformed = true
        systemProperties = true
    }
}
