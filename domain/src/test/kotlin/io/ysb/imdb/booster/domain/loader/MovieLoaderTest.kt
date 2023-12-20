package io.ysb.imdb.booster.domain.loader

import io.ysb.imdb.booster.domain.TitleId
import io.ysb.imdb.booster.port.input.LoadingTitle
import io.ysb.imdb.booster.port.input.Title
import io.ysb.imdb.booster.port.input.TitleType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MovieLoaderTest {
    private val movies: MutableMap<TitleId, Title> = mutableMapOf(
        Pair("tt12345678", Title("tt12345678", "Movie", 2020, 7)),
    )

    @Test
    fun `should not rate non movie`() {
        val movieLoader = MovieLoader(
            { movieId -> movies[movieId]!! },
            { _, _ -> throw IllegalStateException("Should not be called") }
        )

        val movie = LoadingTitle("tt12345678", "Movie", 2020, 10, TitleType.VIDEO_GAME)
        movieLoader.loadTitle(movie)

        assertEquals(7, movies["tt12345678"]!!.myRating)
    }

    @Test
    fun `should not rate movie with non-matched year`() {
        val movieLoader = MovieLoader(
            { movieId -> movies[movieId]!! },
            { _, _ -> throw IllegalStateException("Should not be called") }
        )

        val movie = LoadingTitle("tt12345678", "Movie", 2021, 10, TitleType.MOVIE)
        movieLoader.loadTitle(movie)

        assertEquals(7, movies["tt12345678"]!!.myRating)
    }

    @Test
    fun `should not rate movie with non-matched name`() {
        val movieLoader = MovieLoader(
            { movieId -> movies[movieId]!! },
            { _, _ -> throw IllegalStateException("Should not be called") }
        )

        val movie = LoadingTitle("tt12345678", "The Movie", 2020, 10, TitleType.MOVIE)
        movieLoader.loadTitle(movie)

        assertEquals(7, movies["tt12345678"]!!.myRating)
    }

    @Test
    fun `should rate matched movie`() {
        val movieLoader = MovieLoader(
            { movieId -> movies[movieId]!! },
            { movieId, rating ->
                val newTitle = movies[movieId]!!.copy(myRating = rating)
                movies[movieId] = newTitle
            }
        )

        val movie = LoadingTitle("tt12345678", "Movie", 2020, 10, TitleType.MOVIE)
        movieLoader.loadTitle(movie)

        assertEquals(10, movies["tt12345678"]!!.myRating)
    }
}
