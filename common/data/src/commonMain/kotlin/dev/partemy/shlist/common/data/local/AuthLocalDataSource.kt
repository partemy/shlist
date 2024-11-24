package dev.partemy.shlist.common.data.local

import dev.partemy.shlist.common.preferences.ShlistPreferences

class AuthLocalDataSource(
    private val preferences: ShlistPreferences
) : IAuthLocalDataSource {
    override suspend fun setKey(key: String) = preferences.set("key", key)

    override fun getKey() = preferences.getString(key = "key")
}