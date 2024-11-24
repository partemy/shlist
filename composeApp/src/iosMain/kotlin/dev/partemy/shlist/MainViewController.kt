package dev.partemy.shlist

import androidx.compose.ui.window.ComposeUIViewController
import dev.partemy.shlist.di.shlistAppDI

fun MainViewController() = ComposeUIViewController(configure = { shlistAppDI() }) { App() }