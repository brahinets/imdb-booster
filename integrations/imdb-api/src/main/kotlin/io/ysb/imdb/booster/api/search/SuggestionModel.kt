package io.ysb.imdb.booster.api.search

import com.fasterxml.jackson.annotation.JsonProperty

class SuggestionModel(
    @JsonProperty("d") val d: List<D>,
    @JsonProperty("q") val q: String,
    @JsonProperty("v") val v: Int
)

data class I(
    @JsonProperty("height") val height: Int,
    @JsonProperty("imageUrl") val imageUrl: String,
    @JsonProperty("width") val width: Int
)

data class D(
    @JsonProperty("i") val i: I?,
    @JsonProperty("id") val id: String,
    @JsonProperty("l") val title: String?,
    @JsonProperty("q") val q: String,
    @JsonProperty("qid") val qid: String,
    @JsonProperty("rank") val rank: Int?,
    @JsonProperty("s") val s: String,
    @JsonProperty("y") val year: Int
)
