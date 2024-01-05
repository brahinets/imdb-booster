package io.ysb.imdb.booster.config

import io.ysb.imdb.booster.domain.BatchLoadingService
import io.ysb.imdb.booster.domain.LoadingService
import io.ysb.imdb.booster.domain.MatchingService
import io.ysb.imdb.booster.domain.RatingService
import io.ysb.imdb.booster.domain.TitleService
import io.ysb.imdb.booster.domain.handler.MovieHandler
import io.ysb.imdb.booster.domain.handler.ShortsHandler
import io.ysb.imdb.booster.domain.handler.TitleHandler
import io.ysb.imdb.booster.domain.handler.TvEpisodeHandler
import io.ysb.imdb.booster.domain.handler.VideoGameHandler
import io.ysb.imdb.booster.domain.loader.MovieLoader
import io.ysb.imdb.booster.filesystem.LoadLocalRatingsAdapter
import io.ysb.imdb.booster.port.input.BatchLoadRatingUseCase
import io.ysb.imdb.booster.port.input.GetTitleUseCase
import io.ysb.imdb.booster.port.input.LoadRatingUseCase
import io.ysb.imdb.booster.port.input.RateTitleUseCase
import io.ysb.imdb.booster.port.output.GetTitleRatingPort
import io.ysb.imdb.booster.port.output.LoadLocalRatingsPort
import io.ysb.imdb.booster.port.output.SearchMovieByNamePort
import io.ysb.imdb.booster.port.output.SearchTitleByIdPort
import io.ysb.imdb.booster.port.output.SetTitleRatingPort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanConfig {

    @Bean
    fun titleService(
        getTitleRatingPort: GetTitleRatingPort,
        searchTitleByIdPort: SearchTitleByIdPort
    ): TitleService {
        return TitleService(getTitleRatingPort, searchTitleByIdPort)
    }

    @Bean
    fun matchingService(): MatchingService {
        return MatchingService()
    }

    @Bean
    fun ratingService(
        setTitleRatingPort: SetTitleRatingPort,
        getTitleRatingPort: GetTitleRatingPort,
    ): RatingService {
        return RatingService(setTitleRatingPort, getTitleRatingPort)
    }

    @Bean
    fun loadingService(
        movieHandler: TitleHandler,
        videoGameHandler: TitleHandler,
        tvEpisodeHandler: TvEpisodeHandler,
        shortsHandler: ShortsHandler
    ): LoadingService {
        return LoadingService(
            movieHandler,
            videoGameHandler,
            tvEpisodeHandler,
            shortsHandler
        )
    }

    @Bean
    fun movieHandler(
        movieLoader: MovieLoader
    ): MovieHandler {
        return MovieHandler(movieLoader)
    }

    @Bean
    fun movieLoader(
        getTitleUseCase: GetTitleUseCase,
        rateTitleUseCase: RateTitleUseCase
    ): MovieLoader {
        return MovieLoader(
            getTitleUseCase,
            rateTitleUseCase
        )
    }

    @Bean
    fun videoGameHandler(
        searchMovieByName: SearchMovieByNamePort,
        movieHandler: TitleHandler
    ): VideoGameHandler {
        return VideoGameHandler(
            searchMovieByName,
            movieHandler
        )
    }

    @Bean
    fun tvEpisodeHandler(
        movieHandler: MovieHandler,
        searchMovieById: SearchTitleByIdPort,
        searchMovieByName: SearchMovieByNamePort
    ): TvEpisodeHandler {
        return TvEpisodeHandler(
            movieHandler,
            searchMovieById,
            searchMovieByName
        )
    }


    @Bean
    fun shortsHandler(
        movieHandler: MovieHandler,
        searchMovieByName: SearchMovieByNamePort
    ): ShortsHandler {
        return ShortsHandler(
            movieHandler,
            searchMovieByName
        )
    }

    @Bean
    fun loadLocalRatingsAdapter(): LoadLocalRatingsAdapter {
        return LoadLocalRatingsAdapter()
    }

    @Bean
    fun batchLoadRatingUseCase(
        loadRatingUseCase: LoadRatingUseCase,
        loadLocalRatingsPort: LoadLocalRatingsPort
    ): BatchLoadRatingUseCase {
        return BatchLoadingService(
            loadRatingUseCase,
            loadLocalRatingsPort
        )
    }
}
