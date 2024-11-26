package dev.partemy.shlist.common.data.local

import dev.partemy.shlist.common.database.dao.ShoppingListDao
import dev.partemy.shlist.common.preferences.ShlistPreferences

class AuthLocalDataSource(
    private val preferences: ShlistPreferences,
    private val dao: ShoppingListDao,
) : IAuthLocalDataSource {
    override suspend fun setKey(key: String) = preferences.set("key", key)

    override fun getKey() = preferences.getString(key = "key")

    override suspend fun clearAll() = dao.clearAll()
}