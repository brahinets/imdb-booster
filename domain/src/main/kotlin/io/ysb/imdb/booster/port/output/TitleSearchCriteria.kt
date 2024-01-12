package io.ysb.imdb.booster.port.output

data class TitleSearchCriteria(
    val originalName: String,
    val localisedName: String,
    val year: Int
)
