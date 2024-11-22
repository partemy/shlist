package dev.partemy.shlist

import android.app.Application
import dev.partemy.shlist.di.shlistAppDI
import org.koin.android.ext.koin.androidContext

class ShlistApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        shlistAppDI{
            androidContext(this@ShlistApplication)
        }
    }
}