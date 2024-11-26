package dev.partemy.shlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dev.partemy.shlist.di.shlistAppDI
import org.koin.android.ext.koin.androidContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            shlistAppDI(
                appDeclaration = { androidContext(this@MainActivity) }
            ) {
                ShlistApp()
            }
        }
    }
}