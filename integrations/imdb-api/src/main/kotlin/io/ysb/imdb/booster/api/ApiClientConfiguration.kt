package io.ysb.imdb.booster.api;

import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class ApiClientConfiguration {

    @Bean
    fun objectMapper(): Jackson2ObjectMapperBuilder {
        return Jackson2ObjectMapperBuilder()
            .failOnUnknownProperties(false)
            .modules(
                KotlinModule.Builder()
                    .configure(KotlinFeature.NullToEmptyCollection, true)
                    .configure(KotlinFeature.NullToEmptyMap, true)
                    .build()
            )
    }

    @Bean
    fun imdbWebClient(): WebClient {
        return WebClient.builder()
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_GRAPHQL_RESPONSE_VALUE)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build()
    }
}
