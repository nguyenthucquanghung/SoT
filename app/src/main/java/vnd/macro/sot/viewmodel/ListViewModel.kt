package vnd.macro.sot.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import retrofit2.Response
import vnd.macro.sot.di.DaggerApiComponent
import vnd.macro.sot.model.*

class ListViewModel: ViewModel() {

    @Inject
    lateinit var refLinksService: RefLinksService

    init {
        DaggerApiComponent.create().inject(this)
    }

    private val disposable = CompositeDisposable()

    val refLinks = MutableLiveData<List<ReferenceLink>>()
    val serverError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun getRefLinks(searchRequestBody: SearchRequestBody, bearerToken: String) {
        fetchRefLinks(searchRequestBody, bearerToken)
    }
    fun getRefLinks(selectRequestBody: SelectRequestBody, bearerToken: String) {
        fetchRefLinks(selectRequestBody, bearerToken)
    }

    fun getDbSearchRes(bearerToken: String, keyword: String, lang:String) {
        fetchDbSearchRes(bearerToken, keyword, lang)
    }
    fun getDbSearchRes(bearerToken: String, keyword: String) {
        fetchDbSearchRes(bearerToken, keyword)
    }


    private fun fetchDbSearchRes(bearerToken: String, keyword: String, lang: String) {
        loading.value = true
        disposable.add(
            refLinksService
                .databaseSearch(bearerToken, keyword, lang)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<DatabaseSearchRefLink>>() {
                    override fun onSuccess(t: List<DatabaseSearchRefLink>) {
                        val dbRefLinks: MutableList<ReferenceLink> = mutableListOf()
                        for (dsrl in t)
                            dbRefLinks.add(
                                ReferenceLink(
                                    dsrl.reviews.get(0).title,
                                    dsrl.reviews.get(0).url,
                                    dsrl.imageUri,
                                    dsrl.text
                                )
                            )
                        refLinks.value = dbRefLinks
                        serverError.value = dbRefLinks.isNullOrEmpty()
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        serverError.value = true
                        loading.value = false
                    }

                })

        )
    }
    private fun fetchDbSearchRes(bearerToken: String, keyword: String) {
        loading.value = true
        disposable.add(
            refLinksService
                .databaseSearch(bearerToken, keyword)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<DatabaseSearchRefLink>>() {
                    override fun onSuccess(t: List<DatabaseSearchRefLink>) {
                        val dbRefLinks: MutableList<ReferenceLink> = mutableListOf()
                        for (dsrl in t)
                            dbRefLinks.add(
                                ReferenceLink(
                                    dsrl.reviews.get(0).title,
                                    dsrl.reviews.get(0).url,
                                    dsrl.imageUri,
                                    dsrl.text
                                )
                            )
                        refLinks.value = dbRefLinks
                        serverError.value = dbRefLinks.isNullOrEmpty()
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        serverError.value = true
                        loading.value = false
                    }

                })

        )
    }
    private fun fetchRefLinks(searchRequestBody: SearchRequestBody, bearerToken: String) {
        loading.value = true
        disposable.add(
            refLinksService
                .getRefLinks(searchRequestBody, bearerToken)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<SearchResponse>() {
                    override fun onSuccess(t: SearchResponse) {
                        refLinks.value = t.referenceLinks
                        serverError.value = t.referenceLinks.isNullOrEmpty()
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        serverError.value = true
                        loading.value = false
                    }

                })

        )
    }
    private fun fetchRefLinks(searchRequestBody: SelectRequestBody, bearerToken: String) {
        loading.value = true
        disposable.add(
            refLinksService
                .getRefLinks(searchRequestBody, bearerToken)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<SearchResponse>() {
                    override fun onSuccess(t: SearchResponse) {
                        refLinks.value = t.referenceLinks
                        serverError.value = t.referenceLinks.isNullOrEmpty()
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        serverError.value = true
                        loading.value = false
                    }

                })

        )
    }
    private fun fetchRefLinks(searchRequestBody: SearchRequestBody, bearerToken: String, lang: String) {
        loading.value = true
        disposable.add(
            refLinksService
                .getRefLinks(searchRequestBody, bearerToken, lang)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<SearchResponse>() {
                    override fun onSuccess(t: SearchResponse) {
                        refLinks.value = t.referenceLinks
                        serverError.value = t.referenceLinks.isNullOrEmpty()
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        serverError.value = true
                        loading.value = false
                    }

                })

        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}