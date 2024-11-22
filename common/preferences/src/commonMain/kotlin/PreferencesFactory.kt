import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory.createWithPath
import okio.Path.Companion.toPath

internal const val dataStoreFileName = "shlist_preferences.preferences_pb"

internal fun createDataStore(path: String): DataStore<androidx.datastore.preferences.core.Preferences> = createWithPath(
    produceFile = { path.toPath() }
)

expect object PreferencesFactory {

    fun createPreferences(): Preferences
}
