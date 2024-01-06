package io.ysb.imdb.booster.filesystem

import com.opencsv.bean.CsvBindByName

class KpVotesExportModel {
    @CsvBindByName(column = "Localised Title")
    lateinit var localisedTitle: String

    @CsvBindByName(column = "Original Title")
    lateinit var originalTitle: String

    @CsvBindByName(column = "Year")
    lateinit var year: String

    @CsvBindByName(column = "Countries")
    lateinit var countries: String

    @CsvBindByName(column = "Director")
    lateinit var director: String

    @CsvBindByName(column = "Genres")
    lateinit var genres: String

    @CsvBindByName(column = "Duration")
    lateinit var durationMinutes: String

    @CsvBindByName(column = "My Score")
    lateinit var myScore: String

    @CsvBindByName(column = "Kinoposhuk Score")
    lateinit var kinoposhukScore: String

    @CsvBindByName(column = "Kinoposhuk Number of Scores")
    lateinit var kinoposhukNumberOfScores: String

    @CsvBindByName(column = "Imdb Score")
    lateinit var imdbScore: String

    @CsvBindByName(column = "Imdb Number of Scores")
    lateinit var imdbNumberOfScores: String

    @CsvBindByName(column = "World Premiere")
    lateinit var worldPremiere: String

    @CsvBindByName(column = "Local Premiere")
    lateinit var localPremiere: String

    @CsvBindByName(column = "DVD Premiere")
    lateinit var dvdPremiere: String

    @CsvBindByName(column = "Budget")
    lateinit var budget: String

    @CsvBindByName(column = "Rating Date")
    lateinit var ratingDate: String
}
