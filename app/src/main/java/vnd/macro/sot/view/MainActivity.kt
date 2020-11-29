package vnd.macro.sot.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import vnd.macro.sot.R
import vnd.macro.sot.model.SearchRequestBody
import vnd.macro.sot.util.AppPreferences
import vnd.macro.sot.util.Const
import vnd.macro.sot.util.hideKeyboard
import vnd.macro.sot.viewmodel.ListViewModel

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: ListViewModel
    private val refLinkAdapter = RefLinkAdapter(arrayListOf(), this)
    private val langList = listOf("English", "All languages")
    private var currentLangPos = 0
    private var currentLang: String = ""

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!AppPreferences.isLogin) {
            openLoginActivity()
        }

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)

        rv_ref.visibility = View.GONE
        tv_error.visibility = View.GONE
        pb_loading.visibility = View.GONE


        tv_logout.setOnClickListener {
            val b: AlertDialog.Builder = AlertDialog.Builder(this)
            b.setTitle("Do you want to logout?")
            b.setPositiveButton("Logout") { _, _ ->
                AppPreferences.isLogin = false
                openLoginActivity()
            }
            b.setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            val ad = b.create()
            ad.show()
        }
        et_news.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                searchEventDetected()
                return@setOnKeyListener true
            }
            false
        }

        et_news.setOnTouchListener { _, event ->
            et_news.setSelection(0)
            val drawableEndPos = 2;
            val searchClicked = event.action == MotionEvent.ACTION_UP
                    && event.rawX >= (et_news.right - et_news.compoundDrawables[drawableEndPos].bounds.width())
            if (searchClicked) searchEventDetected()
            searchClicked
        }
        tv_language.setOnClickListener {
            if (currentLangPos < langList.size - 1) currentLangPos++
            else currentLangPos = 0
            tv_language.text = langList[currentLangPos]
            Toast.makeText(this, "Searching language changed!", Toast.LENGTH_SHORT).show()
        }

        et_news.setOnClickListener { et_news.isCursorVisible = true }

    }

    private fun openLoginActivity() {
        val intent =
            Intent(this, LoginActivity::class.java)
        startActivity(intent)
        this.finish()
    }

    private fun searchEventDetected() {
        this.hideKeyboard()
        if (et_news.text.toString().isEmpty()) {
            Toast.makeText(this, "Please enter some incredulous news!", Toast.LENGTH_SHORT).show()
        } else {
            val searchRequestBody = SearchRequestBody(Const.LENGTH_PARAM, et_news.text.toString())
            et_news.isCursorVisible = false

            when (currentLangPos) {
                0 -> currentLang = "en-US"
                1 -> currentLang = ""
            }

            if (currentLang.isEmpty()) {
                Log.d("HEHEHE", "all")
                viewModel.getRefLinks(
                    searchRequestBody,
                    AppPreferences.accessToken
                )
            }
            else {
                Log.d("HEHEHE", "en")
                viewModel.getRefLinks(searchRequestBody, AppPreferences.accessToken, currentLang)
            }

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