package dev.partemy.shlist.common.preferences

import android.content.Context

actual object PreferencesFactory {

    lateinit var appContext: Context

    actual fun createPreferences(): ShlistPreferences =
        ShlistPreferences(
            createDataStore(appContext.filesDir.resolve(dataStoreFileName).absolutePath)
        )

}
