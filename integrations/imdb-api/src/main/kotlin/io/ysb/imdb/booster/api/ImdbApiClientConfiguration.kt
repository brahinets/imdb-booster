package io.ysb.imdb.booster.api;

import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class ImdbApiClientConfiguration {

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
    fun imdbWebClient(
        @Value("\${imdb_api_session_cookie_ubid_main}") ubid: String,
        @Value("\${imdb_api_session_cookie_at_main}") at: String,
    ): WebClient {
        return WebClient.builder()
            .baseUrl("https://api.graphql.imdb.com")
            .defaultCookie("ubid-main", ubid)
            .defaultCookie("at-main", at)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_GRAPHQL_RESPONSE_VALUE)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build()
    }
}
