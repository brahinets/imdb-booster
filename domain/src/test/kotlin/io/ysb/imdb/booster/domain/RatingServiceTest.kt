package io.ysb.imdb.booster.domain

import io.ysb.imdb.booster.domain.RatingService
import io.ysb.imdb.booster.domain.TitleId
import io.ysb.imdb.booster.domain.port.input.RateTitleUseCase
import io.ysb.imdb.booster.domain.port.output.GetTitleRatingPort
import io.ysb.imdb.booster.domain.port.output.SetTitleRatingPort
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class RatingServiceTest {
    private var titles: MutableMap<String, Int> = mutableMapOf(
        Pair("tt12345678", 8)
    )

    private lateinit var ratingService: RatingService

    @BeforeEach
    fun setUp() {
        ratingService = RatingService(
            TestSetTitleRatingPort(),
            TestGetTitleRatingPort()
        )
    }

    @Test
    fun `rate title should rate title without rate yet`() {
        val ratingService: RateTitleUseCase = RatingService(
            TestSetTitleRatingPort(),
            TestGetTitleRatingPort()
        )

        ratingService.rateTitle("tt87654321", 10)

        assertEquals(10, titles["tt87654321"])
    }

    @Test
    fun `rate title should not rate title with rate`() {
        ratingService.rateTitle("tt12345678", 10)

        assertEquals(8, titles["tt12345678"])
    }

    private inner class TestGetTitleRatingPort : GetTitleRatingPort {
        override fun getTitleRating(titleId: TitleId): Optional<Int> {
            return Optional.ofNullable(titles[titleId]);
        }
    }

    private inner class TestSetTitleRatingPort : SetTitleRatingPort {
        override fun setTitleRating(titleId: TitleId, rating: Int) {
            titles[titleId] = rating
        }
    }
}
