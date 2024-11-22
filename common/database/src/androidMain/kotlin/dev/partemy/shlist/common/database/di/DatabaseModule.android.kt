package dev.partemy.shlist.common.database.di

import androidx.room.RoomDatabase
import dev.partemy.shlist.common.database.ShlistDatabase
import dev.partemy.shlist.common.database.dao.ShoppingListDao
import dev.partemy.shlist.common.database.getDatabaseBuilder
import dev.partemy.shlist.common.database.getShlistDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual val databaseModule: Module = module {
    single<RoomDatabase.Builder<ShlistDatabase>> { getDatabaseBuilder(get()) }
    single<ShlistDatabase> { getShlistDatabase(get()) }
    single<ShoppingListDao> { ShlistDatabase::shoppingListDao.invoke(get()) }
}