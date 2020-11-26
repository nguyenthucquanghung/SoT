package vnd.macro.sot.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import vnd.macro.sot.R
import vnd.macro.sot.model.SearchRequestBody
import vnd.macro.sot.util.Const
import vnd.macro.sot.util.hideKeyboard
import vnd.macro.sot.viewmodel.ListViewModel

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: ListViewModel
    private val refLinkAdapter = RefLinkAdapter(arrayListOf(), this)
    private val langList = listOf("All languages", "English")
    private var currentLangPos = 0
    private var currentLang: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)

        rv_ref.visibility = View.GONE
        tv_error.visibility = View.GONE
        pb_loading.visibility = View.GONE

        iv_search.setOnClickListener { searchEventDetected() }

        et_news.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                searchEventDetected()
                return@setOnKeyListener true
            }
            false
        }

        tv_language.setOnClickListener {
            if (currentLangPos < langList.size - 1) currentLangPos++
            else currentLangPos = 0
            tv_language.text = langList[currentLangPos]
        }

        et_news.setOnClickListener { et_news.isCursorVisible = true }

    }

    private fun searchEventDetected() {
        this.hideKeyboard()
        if (et_news.text.toString().isEmpty()) {
            Toast.makeText(this, "Please enter some incredulous news!", Toast.LENGTH_SHORT).show()
        } else {
            val searchRequestBody = SearchRequestBody(Const.LENGTH_PARAM, et_news.text.toString())
            et_news.isCursorVisible = false

            when (currentLangPos) {
                0 -> currentLang = ""
                1 -> currentLang = "en-US"
            }

            if (currentLang.isEmpty()) viewModel.getRefLinks(searchRequestBody, Const.DEFAULT_BEARER_TOKEN)
            else viewModel.getRefLinks(searchRequestBody, Const.DEFAULT_BEARER_TOKEN, currentLang)

            rv_ref.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = refLinkAdapter
                observeViewModel()
            }
        }

    }
    private fun observeViewModel() {
        viewModel.refLinks.observe(this, Observer { singleRefLinks ->
            singleRefLinks?.let {
                rv_ref.visibility = View.VISIBLE
                refLinkAdapter.updateRefLinks(it)
            }
        })

        viewModel.serverError.observe(this, Observer { isError ->
            isError?.let {
                tv_error.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                pb_loading.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    tv_error.visibility = View.GONE
                    rv_ref.visibility = View.GONE
                }
            }
        })
    }
}