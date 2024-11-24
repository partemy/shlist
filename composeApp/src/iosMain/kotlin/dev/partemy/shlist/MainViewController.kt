package dev.partemy.shlist

import androidx.compose.ui.window.ComposeUIViewController
import dev.partemy.shlist.di.shlistAppDI
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController {
    shlistAppDI {
        App()
    }
}