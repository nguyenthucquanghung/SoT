package vnd.macro.sot.util

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import vnd.macro.sot.model.SearchResponse
import vnd.macro.sot.model.SearchRequestBody
import vnd.macro.sot.model.SelectRequestBody

interface RefLinksApi {

    @POST("fake-news-checking")
    fun getRefLinks(@Body searchRequestBody: SearchRequestBody, @Header("Authorization") bearerToken: String): Single<SearchResponse>
    @POST("fake-news-checking")
    fun getRefLinks(@Body searchRequestBody: SelectRequestBody, @Header("Authorization") bearerToken: String): Single<SearchResponse>
    @POST("fake-news-checking")
    fun getRefLinks(@Body searchRequestBody: SearchRequestBody, @Header("Authorization") bearerToken: String, @Query("accept-language") lang: String): Single<SearchResponse>
}