package k.moviecatalog.data.collections

import androidx.datastore.core.Serializer
import k.moviecatalog.data.collections.models.CollectionsPreferences
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object CollectionsPreferencesSerializer : Serializer<CollectionsPreferences> {
    override val defaultValue: CollectionsPreferences
        get() = CollectionsPreferences()

    override suspend fun readFrom(input: InputStream): CollectionsPreferences {
        return Json.decodeFromString(
            CollectionsPreferences.serializer(),
            input.readBytes().decodeToString()
        )
    }

    override suspend fun writeTo(t: CollectionsPreferences, output: OutputStream) {
        output.use {
            it.write(
                Json.encodeToString(CollectionsPreferences.serializer(), t)
                    .toByteArray()
            )
        }
    }
}