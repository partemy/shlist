package dev.partemy.shlist.common.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory.createWithPath
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

internal const val dataStoreFileName = "shlist_preferences.preferences_pb"

internal fun createDataStore(path: String): DataStore<Preferences> = createWithPath(
    produceFile = { path.toPath() }
)

expect object PreferencesFactory {

    fun createPreferences(): ShlistPreferences

}
