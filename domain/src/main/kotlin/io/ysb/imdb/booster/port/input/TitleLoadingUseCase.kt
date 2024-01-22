package io.ysb.imdb.booster.port.input

fun interface TitleLoadingUseCase {
    fun loadRating(title: LoadingTitle)
}
