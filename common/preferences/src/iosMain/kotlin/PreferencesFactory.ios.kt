import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

actual object PreferencesFactory {
    @OptIn(ExperimentalForeignApi::class)
    private val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null
    )

    actual fun createPreferences(): Preferences =
        Preferences(
            createDataStore(
                requireNotNull(documentDirectory).path + "/$dataStoreFileName"
            )
        )
}