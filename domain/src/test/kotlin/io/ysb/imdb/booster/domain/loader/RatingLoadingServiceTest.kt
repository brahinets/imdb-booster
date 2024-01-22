package io.ysb.imdb.booster.domain.loader

import io.ysb.imdb.booster.domain.TitleId
import io.ysb.imdb.booster.domain.rating.RatingLoadingService
import io.ysb.imdb.booster.port.input.LoadingTitle
import io.ysb.imdb.booster.port.input.Title
import io.ysb.imdb.booster.port.input.TitleType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RatingLoadingServiceTest {
    private val titles: MutableMap<TitleId, Title> = mutableMapOf(
        Pair("tt12345678", Title("tt12345678", TitleType.MOVIE, "Movie", 2020, 7)),
    )

    @Test
    fun `should not rate title with non-matched year`() {
        val loader = RatingLoadingService(
            { titleId -> titles[titleId]!! },
            { _, _ -> throw IllegalStateException("Should not be called") }
        )

        val title = LoadingTitle("tt12345678", "Movie", 2021, 10)
        loader.loadRating(title)

        assertEquals(7, titles["tt12345678"]!!.myRating)
    }

    @Test
    fun `should not rate title with non-matched name`() {
        val loader = RatingLoadingService(
            { titleId -> titles[titleId]!! },
            { _, _ -> throw IllegalStateException("Should not be called") }
        )

        val title = LoadingTitle("tt12345678", "The Movie", 2020, 10)
        loader.loadRating(title)

        assertEquals(7, titles["tt12345678"]!!.myRating)
    }

    @Test
    fun `should rate matched title`() {
        val loader = RatingLoadingService(
            { titleId -> titles[titleId]!! },
            { titleId, rating ->
                val title = titles[titleId]!!.copy(myRating = rating)
                titles[titleId] = title
            }
        )

        val title = LoadingTitle("tt12345678", "Movie", 2020, 10)
        loader.loadRating(title)

        assertEquals(10, titles["tt12345678"]!!.myRating)
    }
}
