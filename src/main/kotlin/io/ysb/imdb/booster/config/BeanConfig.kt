package io.ysb.imdb.booster.config

import io.ysb.imdb.booster.domain.dump.handler.imdb.ImdbBatchLoadingService
import io.ysb.imdb.booster.domain.dump.handler.kp.KinoposhukBatchLoadingService
import io.ysb.imdb.booster.domain.rating.MatchingService
import io.ysb.imdb.booster.domain.rating.TitleLoadingService
import io.ysb.imdb.booster.domain.rating.RatingService
import io.ysb.imdb.booster.domain.rating.TitleService
import io.ysb.imdb.booster.filesystem.ReadLocalRatingsAdapter
import io.ysb.imdb.booster.filesystem.ReadLocalVotesAdapter
import io.ysb.imdb.booster.port.input.BatchLoadRatingUseCase
import io.ysb.imdb.booster.port.input.GetTitleUseCase
import io.ysb.imdb.booster.port.input.TitleLoadingUseCase
import io.ysb.imdb.booster.port.input.RateTitleUseCase
import io.ysb.imdb.booster.port.output.GetTitleRatingPort
import io.ysb.imdb.booster.port.output.ReadLocalRatingsPort
import io.ysb.imdb.booster.port.output.ReadLocalVotesPort
import io.ysb.imdb.booster.port.output.SearchTitleByIdPort
import io.ysb.imdb.booster.port.output.SearchTitlePort
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
    fun ratingLoadingService(
        getTitleUseCase: GetTitleUseCase,
        rateTitleUseCase: RateTitleUseCase
    ): TitleLoadingService {
        return TitleLoadingService(
            getTitleUseCase,
            rateTitleUseCase
        )
    }

    @Bean
    fun readLocalRatingsAdapter(): ReadLocalRatingsAdapter {
        return ReadLocalRatingsAdapter()
    }

    @Bean
    fun readLocalVotesAdapter(): ReadLocalVotesAdapter {
        return ReadLocalVotesAdapter()
    }

    @Bean
    fun batchLoadRatingUseCase(
        titleLoadingUseCase: TitleLoadingUseCase,
        readLocalRatingsPort: ReadLocalRatingsPort
    ): BatchLoadRatingUseCase {
        return ImdbBatchLoadingService(
            titleLoadingUseCase,
            readLocalRatingsPort
        )
    }

    @Bean
    fun kinoposhukBatchLoadingService(
        searchTitlePort: SearchTitlePort,
        loadingService: TitleLoadingUseCase,
        titleService: TitleService,
        readLocalRatingsPort: ReadLocalVotesPort
    ): KinoposhukBatchLoadingService {
        return KinoposhukBatchLoadingService(
            searchTitlePort,
            loadingService,
            titleService,
            readLocalRatingsPort
        )
    }
}
