package vnd.macro.sot.util

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import vnd.macro.sot.model.SearchResponse
import vnd.macro.sot.model.SearchRequestBody

interface RefLinksApi {

    @POST("fake-news-checking?accept-language=en-US")
    fun getRefLinks(@Body searchRequestBody: SearchRequestBody, @Header("Authorization") bearerToken: String): Single<SearchResponse>
}