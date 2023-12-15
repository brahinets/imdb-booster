package io.ysb.imdb.booster.api.graphql

import io.ysb.imdb.booster.api.graphql.model.UpdateRatingResponse
import io.ysb.imdb.booster.domain.TitleId
import io.ysb.imdb.booster.port.output.SetTitleRatingPort
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient

@Component
class SetTitleRatingAdapter(
    private val imdbClient: WebClient
) : SetTitleRatingPort {

    override fun setTitleRating(titleId: TitleId, rating: Int) {
        doRequest(titleId, rating)
    }

    private fun doRequest(titleId: TitleId, rating: Int): UpdateRatingResponse {
        return imdbClient.post()
            .body(
                BodyInserters.fromValue(
                    "{\"query\":\"mutation UpdateTitleRating(\$rating: Int!, \$titleId: ID!) {  rateTitle(input: {rating: \$rating, titleId: \$titleId}) {    rating {      value      __typename    }    __typename  }}\",\"operationName\":\"UpdateTitleRating\",\"variables\":{\"rating\":$rating,\"titleId\":\"$titleId\"}}"
                )
            )
            .retrieve()
            .bodyToMono(UpdateRatingResponse::class.java)
            .block()!!
    }
}
