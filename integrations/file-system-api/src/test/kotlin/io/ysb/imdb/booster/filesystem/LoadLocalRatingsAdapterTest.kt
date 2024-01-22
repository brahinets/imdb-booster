package io.ysb.imdb.booster.filesystem

import io.ysb.imdb.booster.port.input.TitleType
import io.ysb.imdb.booster.port.output.ImdbLocalTitle
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.io.StringReader

class LoadLocalRatingsAdapterTest {

    private lateinit var readLocalRatingsAdapter: ReadLocalRatingsAdapter

    @BeforeEach
    fun setUp() {
        readLocalRatingsAdapter = ReadLocalRatingsAdapter()
    }

    @Test
    fun loadFullTitle() {
        val readLocalTitles = readLocalRatingsAdapter.readLocalTitles(
            StringReader(
                """
                    Const;Your Rating;Date Rated;Title;URL;Title Type;IMDb Rating;Runtime (mins);Year;Genres;Num Votes;Release Date;Directors
                    tt0100835;9;02.02.2018;Ultra;https://www.imdb.com/title/tt0100835/;movie;6,5;95;1991;Drama, Sport;918;28.02.1991;Ricky Tognazzi
                """.trimIndent()
            )
        )

        assertEquals(
            listOf(ImdbLocalTitle("Ultra", "tt0100835", 9, TitleType.MOVIE, 1991, setOf("Drama", "Sport"))), readLocalTitles
        )
    }


    @Test
    fun loadTitleWithMissingNonRequiredFields() {
        val readLocalTitles = readLocalRatingsAdapter.readLocalTitles(
            StringReader(
                """
                    Const;Your Rating;Date Rated;Title;URL;Title Type;IMDb Rating;Runtime (mins);Year;Genres;Num Votes;Release Date;Directors
                    tt0100835;9;;Ultra;;movie;;;1991;Drama, Sport;;;
                """.trimIndent()
            )
        )

        assertEquals(
            listOf(ImdbLocalTitle("Ultra", "tt0100835", 9, TitleType.MOVIE ,1991, setOf("Drama", "Sport"))),
            readLocalTitles
        )
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            ";9;;Ultra;;movie;;;1991;Drama, Sport;;;", // missing title id
            "tt0100835;9;;;;movie;;;1991;Drama, Sport;;;", // missing title name
            "tt0100835;;;Ultra;;movie;;;1991;Drama, Sport;;;", // missing rating
            "tt0100835;9;;Ultra;;movie;;;;Drama, Sport;;;", // missing year
            "tt0100835;9;;Ultra;;;;;;Drama, Sport;;;" // missing type
        ]
    )
    fun failLoadTitleWithMissingRequiredFields(limitedTitle: String) {
        val error = assertThrows<IllegalArgumentException> {
            val header =
                "Const;Your Rating;Date Rated;Title;URL;Title Type;IMDb Rating;Runtime (mins);Year;Genres;Num Votes;Release Date;Directors"
            readLocalRatingsAdapter.readLocalTitles(
                StringReader(header + "\n" + limitedTitle)
            )
        }
        assertEquals("Failed to parse CSV. Some required field are missing in csv", error.message)
    }
}
