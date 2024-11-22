import android.content.Context

actual object PreferencesFactory {

    lateinit var appContext: Context

    actual fun createPreferences(): Preferences =
        Preferences(
            createDataStore(appContext.filesDir.resolve(dataStoreFileName).absolutePath)
        )
}