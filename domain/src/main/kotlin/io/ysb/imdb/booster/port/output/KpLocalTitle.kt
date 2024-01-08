package io.ysb.imdb.booster.port.output

data class KpLocalTitle(
    val name: String,
    val originalName: String,
    val myRating: Int,
    val year: Int,
    val genres: Set<String>
)
