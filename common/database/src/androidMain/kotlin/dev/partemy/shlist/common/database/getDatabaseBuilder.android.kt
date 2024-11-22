package dev.partemy.shlist.common.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

fun getDatabaseBuilder(ctx: Context): RoomDatabase.Builder<ShlistDatabase> {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath("shlist.db")
    return Room.databaseBuilder<ShlistDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}