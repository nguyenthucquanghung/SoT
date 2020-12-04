package vnd.macro.sot.util

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*
import vnd.macro.sot.model.*

interface RefLinksApi {
    @POST("/extension/fake-news-checking")
    fun getRefLinks(@Body searchRequestBody: SearchRequestBody, @Header("Authorization") bearerToken: String): Single<SearchResponse>
    @POST("/extension/fake-news-checking")
    fun getRefLinks(@Body searchRequestBody: SelectRequestBody, @Header("Authorization") bearerToken: String): Single<SearchResponse>
    @POST("/extension/fake-news-checking")
    fun getRefLinks(@Body searchRequestBody: SearchRequestBody, @Header("Authorization") bearerToken: String, @Query("accept-language") lang: String): Single<SearchResponse>
    @POST("/customer/login")
    fun login(@Body loginRequestBody: LoginRequestBody): Single<LoginResponse>
    @GET("/database-search")
    fun databaseSearch(@Header("Authorization") bearerToken: String, @Query("keyword") keyword: String, @Query("language") lang: String, @Query("skip") skip: Int = 0, @Query("take") take: Int = 10): Single<List<DatabaseSearchRefLink>>
    @GET("/database-search")
    fun databaseSearch(@Header("Authorization") bearerToken: String, @Query("keyword") keyword: String, @Query("skip") skip: Int = 0, @Query("take") take: Int = 10): Single<List<DatabaseSearchRefLink>>
}