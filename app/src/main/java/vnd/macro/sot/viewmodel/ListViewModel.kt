package vnd.macro.sot.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import vnd.macro.sot.model.RefLinksService
import vnd.macro.sot.model.ReferenceLink
import javax.inject.Inject
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import vnd.macro.sot.di.DaggerApiComponent
import vnd.macro.sot.model.SearchResponse
import vnd.macro.sot.model.SearchRequestBody

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

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}