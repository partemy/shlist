package dev.partemy.shlist.common.core

import dev.partemy.shlist.common.database.di.databaseModule

val dataModules =
    remoteModule + databaseModule + localDataSourceModule + repositoryModule + preferencesModule + remoteDataSourceModule