package dev.partemy.shlist.common.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dev.partemy.shlist.common.database.dao.ShoppingListDao
import dev.partemy.shlist.common.database.entity.ShoppingListItemDBO
import dev.partemy.shlist.common.database.entity.ShoppingListDBO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(entities = [ShoppingListItemDBO::class, ShoppingListDBO::class], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class ShlistDatabase : RoomDatabase() {
    abstract fun shoppingListDao(): ShoppingListDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<ShlistDatabase> {
    override fun initialize(): ShlistDatabase
}

fun getShlistDatabase(
    builder: RoomDatabase.Builder<ShlistDatabase>
): ShlistDatabase {
    return builder
        .fallbackToDestructiveMigration(dropAllTables = true)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}