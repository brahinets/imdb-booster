package io.ysb.imdb.booster.port.input

interface LoadRatingUseCase {
    fun loadRating(title: LoadingTitle)
}
