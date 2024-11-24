package dev.partemy.shlist.common.core

import android.app.Application
import dev.partemy.shlist.common.preferences.PreferencesFactory

class ShlistApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        PreferencesFactory.appContext = this
    }
}