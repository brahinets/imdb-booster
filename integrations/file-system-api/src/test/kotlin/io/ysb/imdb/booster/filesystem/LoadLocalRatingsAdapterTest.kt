package io.ysb.imdb.booster.filesystem

import io.ysb.imdb.booster.port.output.LocalTitle
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.StringReader

class LoadLocalRatingsAdapterTest {

    private lateinit var loadLocalRatingsAdapter: LoadLocalRatingsAdapter


    @BeforeEach
    fun setUp() {
        loadLocalRatingsAdapter = LoadLocalRatingsAdapter()
    }

    @Test
    fun loadFullTitle() {
        val loadLocalTitles = loadLocalRatingsAdapter.loadLocalTitles(
            StringReader(
                """
                    Const;Your Rating;Date Rated;Title;URL;Title Type;IMDb Rating;Runtime (mins);Year;Genres;Num Votes;Release Date;Directors
                    tt0100835;9;02.02.2018;Ultra;https://www.imdb.com/title/tt0100835/;movie;6,5;95;1991;Drama, Sport;918;28.02.1991;Ricky Tognazzi
                """.trimIndent()
            )
        )

        assertEquals(
            listOf(LocalTitle("Ultra", "tt0100835", 9, 1991, setOf("Drama", "Sport"))),
            loadLocalTitles
        )
    }


    @Test
    fun loadTitleWithMissingNonRequiredFields() {
        val loadLocalTitles = loadLocalRatingsAdapter.loadLocalTitles(
            StringReader(
                """
                    Const;Your Rating;Date Rated;Title;URL;Title Type;IMDb Rating;Runtime (mins);Year;Genres;Num Votes;Release Date;Directors
                    tt0100835;9;;Ultra;;;;;1991;Drama, Sport;;;
                """.trimIndent()
            )
        )

        assertEquals(
            listOf(LocalTitle("Ultra", "tt0100835", 9, 1991, setOf("Drama", "Sport"))),
            loadLocalTitles
        )
    }
}
