package dev.partemy.shlist.di

import dev.partemy.shlist.common.core.dataModules
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun shlistAppDI(
    appDeclaration: KoinAppDeclaration? = null
) = startKoin {
    appDeclaration?.invoke(this)
    modules(dataModules)
}