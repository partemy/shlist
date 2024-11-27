package dev.partemy.shlist.common.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class ShlistPreferences(private val dataStore: DataStore<Preferences>) {

    suspend fun set(key: String, value: String) {
        val stringKey = stringPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences[stringKey] = value
        }
    }

    fun getString(key: String): Flow<String?> =
        dataStore.data.map { preferences ->
            val stringKey = stringPreferencesKey(key)
            preferences[stringKey]
        }

}