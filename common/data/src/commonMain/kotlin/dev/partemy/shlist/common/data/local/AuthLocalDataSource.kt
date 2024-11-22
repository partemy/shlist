package dev.partemy.shlist.common.data.local

import Preferences

class AuthLocalDataSource(
    private val preferences: Preferences
) : IAuthLocalDataSource {
    override suspend fun setKey(key: String) = preferences.set("key", key)

    override fun getKey() = preferences.getString(key = "key")
}