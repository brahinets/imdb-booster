package io.ysb.imdb.booster.api.graphql.model

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateRatingResponse(
    val data: Data,
    val extensions: Extensions,
) {
    data class Data(
        val rateTitle: RateTitle,
    )

    data class RateTitle(
        val rating: Rating,
        @JsonProperty("__typename") val typename: String,
    )

    data class Rating(
        val value: Long,
        @JsonProperty("__typename") val typename: String,
    )
}