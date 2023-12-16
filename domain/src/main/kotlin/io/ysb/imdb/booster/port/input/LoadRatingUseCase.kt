package io.ysb.imdb.booster.port.input

fun interface LoadRatingUseCase {
    fun loadRating(title: LoadingTitle)
}
