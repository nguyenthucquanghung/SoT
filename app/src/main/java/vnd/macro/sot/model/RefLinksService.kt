package vnd.macro.sot.model

import io.reactivex.Single
import retrofit2.Response
import vnd.macro.sot.di.DaggerApiComponent
import vnd.macro.sot.util.RefLinksApi
import javax.inject.Inject

class RefLinksService {
    @Inject
    lateinit var api:RefLinksApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getRefLinks(searchRequestBody: SearchRequestBody, bearerToken: String): Single<SearchResponse> {
        return api.getRefLinks(searchRequestBody, bearerToken)
    }
    fun getRefLinks(searchRequestBody: SelectRequestBody, bearerToken: String): Single<SearchResponse> {
        return api.getRefLinks(searchRequestBody, bearerToken)
    }
    fun getRefLinks(searchRequestBody: SearchRequestBody, bearerToken: String, lang: String): Single<SearchResponse> {
        return api.getRefLinks(searchRequestBody, bearerToken, lang)
    }
    fun login(loginRequestBody: LoginRequestBody): Single<LoginResponse> {
        return api.login(loginRequestBody)
    }
    fun databaseSearch(bearerToken: String, keyword: String, lang: String): Single<List<DatabaseSearchRefLink>> {
        return api.databaseSearch(bearerToken, keyword, lang)
    }
    fun databaseSearch(bearerToken: String, keyword: String): Single<List<DatabaseSearchRefLink>> {
        return api.databaseSearch(bearerToken, keyword)
    }
}