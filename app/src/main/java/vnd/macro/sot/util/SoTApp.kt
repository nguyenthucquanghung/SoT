package vnd.macro.sot.util

import android.app.Application

class SoTApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppPreferences.init(this)
    }
}