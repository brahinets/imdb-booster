package io.ysb.imdb.booster.filesystem

import com.opencsv.bean.CsvToBeanBuilder
import io.ysb.imdb.booster.port.output.LocalTitle
import java.io.Reader

class LoadLocalRatingsAdapter : io.ysb.imdb.booster.port.output.LoadLocalRatingsPort {

    override fun loadLocalTitles(reader: Reader): List<LocalTitle> {
        val records = CsvToBeanBuilder<ImdbRatingsExportModel>(reader)
            .withType(ImdbRatingsExportModel::class.java)
            .withIgnoreLeadingWhiteSpace(true)
            .withSeparator(';')
            .build()
            .parse()

        return records.map {
            LocalTitle(
                name = it.title,
                id = it.titleId,
                myRating = it.yourRating.toInt(),
                year = it.year.toInt(),
                genres = it.genres.split(",").map { genre -> genre.trim() }.toSet()
            )
        }
    }
}
