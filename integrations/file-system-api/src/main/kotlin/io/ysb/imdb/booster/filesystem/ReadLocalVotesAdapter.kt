package io.ysb.imdb.booster.filesystem

import com.opencsv.bean.CsvToBeanBuilder
import com.opencsv.exceptions.CsvRequiredFieldEmptyException
import io.ysb.imdb.booster.port.output.KpLocalTitle
import java.io.Reader

class ReadLocalVotesAdapter : io.ysb.imdb.booster.port.output.ReadLocalVotesPort {

    override fun readLocalTitles(reader: Reader): List<KpLocalTitle> {
        try {
            val records = CsvToBeanBuilder<KpVotesExportModel>(reader)
                .withType(KpVotesExportModel::class.java)
                .withIgnoreLeadingWhiteSpace(true)
                .withSeparator(';')
                .build()
                .parse()

            return records.map {
                KpLocalTitle(
                    localisedName = it.localisedTitle,
                    originalName = it.originalTitle.ifBlank { it.localisedTitle },
                    myRating = it.myScore.toInt(),
                    year = it.year.substring(0, 4).toInt(),
                    genres = it.genres.split(",").map { genre -> genre.trim() }.toSet()
                )
            }
        } catch (e: RuntimeException) {
            if (e.cause is CsvRequiredFieldEmptyException) {
                throw IllegalArgumentException("Failed to parse CSV. Some required field are missing in csv", e)
            } else {
                throw RuntimeException("Failed to parse CSV", e)
            }
        }
    }
}
