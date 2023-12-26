package io.ysb.imdb.booster.port.output

fun interface SearchMovieByNamePort {
    fun searchMovieByName(titleName: String): TitleSuggestion
}
