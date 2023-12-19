package io.ysb.imdb.booster.port.input

enum class TitleType(private val imdbCode: String) {
    MOVIE("movie"),
    SHORT("short"),
    TV_EPISODE("tvEpisode"),
    TV_MINI_SERIES("tvMiniSeries"),
    TV_MOVIE("tvMovie"),
    TV_SERIES("tvSeries"),
    TV_SHORT("tvShort"),
    VIDEO("video"),
    VIDEO_GAME("videoGame"),
    UNKNOWN("unknown");

    companion object {
        fun from(imdbCode: String): TitleType = entries
            .filter { it.imdbCode == imdbCode }
            .elementAtOrElse(0) { UNKNOWN }
    }
}
