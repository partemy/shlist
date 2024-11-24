package dev.partemy.shlist.di

import androidx.compose.runtime.Composable
import dev.partemy.shlist.common.core.dataModules
import dev.partemy.shlist.common.core.domainModules
import org.koin.compose.KoinApplication
import org.koin.dsl.KoinAppDeclaration

@Composable
fun shlistAppDI(
    appDeclaration: KoinAppDeclaration = {},
    content: @Composable () -> Unit
) = KoinApplication(
    application = {
        modules(dataModules + domainModules)
        appDeclaration()
    },
    content = content
)