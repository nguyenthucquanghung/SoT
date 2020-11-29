package vnd.macro.sot.util

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {
    private const val NAME = "SoT Preferences"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    private val IS_LOGIN = Pair("IS_LOGIN", false)
    private val USERNAME = Pair("USERNAME", "")
    private val USER_ID = Pair("USER_ID", 0)
    private val EXPIRED_TIME = Pair("EXPIRED_TIME", 0)
    private val ACCESS_TOKEN = Pair("ACCESS_TOKEN", Const.DEFAULT_BEARER_TOKEN)

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var isLogin: Boolean
        get() = preferences.getBoolean(IS_LOGIN.first, IS_LOGIN.second)
        set(value) = preferences.edit {
            it.putBoolean(IS_LOGIN.first, value)
        }

    var username: String
        get() = preferences.getString(USERNAME.first, USERNAME.second) ?: ""
        set(value) = preferences.edit {
            it.putString(USERNAME.first, value)
        }

    var userId: Int
        get() = preferences.getInt(USER_ID.first, USER_ID.second) ?: 0
        set(value) = preferences.edit {
            it.putInt(USERNAME.first, value)
        }

    var expiredTime: Int
        get() = preferences.getInt(EXPIRED_TIME.first, EXPIRED_TIME.second) ?: 0
        set(value) = preferences.edit {
            it.putInt(EXPIRED_TIME.first, value)
        }

    var accessToken: String
        get() = preferences.getString(ACCESS_TOKEN.first, ACCESS_TOKEN.second) ?: ""
        set(value) = preferences.edit {
            it.putString(ACCESS_TOKEN.first, value)
        }
}