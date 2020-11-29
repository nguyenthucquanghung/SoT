package vnd.macro.sot.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import vnd.macro.sot.R
import vnd.macro.sot.di.DaggerApiComponent
import vnd.macro.sot.model.LoginRequestBody
import vnd.macro.sot.model.LoginResponse
import vnd.macro.sot.model.RefLinksService
import vnd.macro.sot.util.AppPreferences
import vnd.macro.sot.util.Const
import vnd.macro.sot.util.hideKeyboard
import javax.inject.Inject


class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var refLinksService: RefLinksService

    init {
        DaggerApiComponent.create().inject(this)
    }
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (AppPreferences.isLogin) {
            val intent =
                Intent(this, MainActivity::class.java)
            startActivity(intent)
            this.finish()
        }

        tv_sign_up.setOnClickListener { openSoTWeb() }

        tv_forgot_password.setOnClickListener { openSoTWeb() }

        et_password.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                login()
                return@setOnKeyListener true
            }
            false
        }

        tv_login.setOnClickListener {
            login()
        }
    }

    private fun login(){
        this.hideKeyboard()
        when {
            et_email.text.toString().isEmpty() ->
                Toast.makeText(this, "Please enter your username!", Toast.LENGTH_SHORT).show()
            et_password.text.toString().isEmpty() ->
                Toast.makeText(this, "Please enter your password!", Toast.LENGTH_SHORT).show()
            et_password.text.toString().length < 6 ->
                Toast.makeText(
                    this,
                    "Password must have at least 6 characters!",
                    Toast.LENGTH_SHORT
                ).show()
            else -> {
                Toast.makeText(
                    this,
                    "Logging in ...",
                    Toast.LENGTH_LONG
                ).show()
                disposable.add(
                    refLinksService
                        .login(
                            loginRequestBody = LoginRequestBody(
                                et_email.text.toString(),
                                et_password.text.toString()
                            )
                        )
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<LoginResponse>() {
                            override fun onSuccess(t: LoginResponse) {
                                AppPreferences.isLogin = true
                                AppPreferences.accessToken = "Bearer " + t.accessToken
                                AppPreferences.expiredTime = t.expiresTime
                                AppPreferences.userId = t.userId
                                AppPreferences.username = tv_email.text.toString()
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Login successfully!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent)
                                this@LoginActivity.finish()
                            }

                            override fun onError(e: Throwable) {
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Wrong email or password!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })

                )
            }
        }
    }

    private fun openSoTWeb() {
        val defaultBrowser =
            Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_BROWSER)
        defaultBrowser.data = Uri.parse(Const.SOT_WEB_URL)
        this.startActivity(defaultBrowser)
    }


}