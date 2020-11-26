package vnd.macro.sot.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import vnd.macro.sot.model.RefLinksService
import vnd.macro.sot.util.RefLinksApi

@Module
class ApiModule {
    private val BASE_URL = "https://api.serumoftruth.com/"

    @Provides
    fun provideRefLinkApi() : RefLinksApi {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(RefLinksApi::class.java)
    }

    @Provides
    fun provideRefLinksService() : RefLinksService {
        return RefLinksService()
    }
}