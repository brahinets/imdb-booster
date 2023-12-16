package io.ysb.imdb.booster.domain

import io.ysb.imdb.booster.port.input.GetTitleUseCase
import io.ysb.imdb.booster.port.input.LoadingTitle
import io.ysb.imdb.booster.port.input.MatchTitleUseCase
import io.ysb.imdb.booster.port.input.MatchingTitle
import io.ysb.imdb.booster.port.input.RateTitleUseCase
import io.ysb.imdb.booster.port.input.Title
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LoadingServiceTest {
    private var titles: MutableMap<TitleId, Title> = mutableMapOf(
        Pair("tt12345678", Title("tt12345678", "Movie-Movie", 2020, 7)),
        Pair("tt87654321", Title("tt87654321", "Movie-Movie 2", 2022, 7)),
    )

    @Test
    fun `should rate matched title`() {
        val loadingService = LoadingService(
            TestMatchTitleUseCase(true),
            TestGetTitleUseCase(),
            TestRateTitleUseCase()
        )

        val title = LoadingTitle("tt12345678", "Movie-Movie", 2020, 10)
        loadingService.loadRating(title)

        assertEquals(10, titles["tt12345678"]!!.myRating)
    }

    @Test
    fun `should not rate non matched title`() {
        val loadingService = LoadingService(
            TestMatchTitleUseCase(false),
            TestGetTitleUseCase(),
            TestRateTitleUseCase()
        )

        val title = LoadingTitle("tt87654321", "Movie Movie 2", 2020, 10)
        loadingService.loadRating(title)

        assertEquals(7, titles["tt87654321"]!!.myRating)
    }

    private inner class TestMatchTitleUseCase(private val result: Boolean) : MatchTitleUseCase {
        override fun matchTitle(first: MatchingTitle, second: MatchingTitle): Boolean {
            return result
        }
    }

    private inner class TestRateTitleUseCase : RateTitleUseCase {
        override fun rateTitle(titleId: TitleId, rating: Int) {
            val newTitle = titles[titleId]!!.copy(myRating = rating)
            titles[titleId] = newTitle
        }
    }

    private inner class TestGetTitleUseCase : GetTitleUseCase {
        override fun getTitle(titleId: TitleId): Title {
            return titles[titleId]!!
        }
    }
}
