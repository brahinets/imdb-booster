package io.ysb.imdb.booster.filesystem

import com.opencsv.bean.CsvBindByName


class ImdbRatingsExportModel {
    @CsvBindByName(column = "Const", required = true)
    lateinit var titleId: String

    @CsvBindByName(column = "Your Rating", required = true)
    lateinit var yourRating: String

    @CsvBindByName(column = "Date Rated", required = false)
    lateinit var dateRated: String

    @CsvBindByName(column = "Title", required = true)
    lateinit var title: String

    @CsvBindByName(column = "URL", required = false)
    lateinit var url: String

    @CsvBindByName(column = "Title Type", required = false)
    lateinit var titleType: String

    @CsvBindByName(column = "IMDb Rating", required = false)
    lateinit var imdbRating: String

    @CsvBindByName(column = "Runtime (mins)", required = false)
    lateinit var runtimeMins: String

    @CsvBindByName(column = "Year", required = true)
    lateinit var year: String

    @CsvBindByName(column = "Genres", required = false)
    lateinit var genres: String

    @CsvBindByName(column = "Num Votes", required = false)
    lateinit var numVotes: String

    @CsvBindByName(column = "Release Date", required = false)
    lateinit var releaseDate: String

    @CsvBindByName(column = "Directors", required = false)
    lateinit var directors: String
}
