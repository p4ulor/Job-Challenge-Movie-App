package job.challenge.movieapp.data.local.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import job.challenge.movieapp.e
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

data class UserPreferences(
    var tmdbToken: String? = null,
) {
    companion object {
        // Don't put this inside an object like the other things, or these will be null
        val tmdbTokenKey = stringPreferencesKey("tmdbToken")

        suspend fun getFrom(storage: DataStore<Preferences>) = withContext(Dispatchers.IO) {
            return@withContext storage.data
                .catch {
                    e("Error reading preferences: $it")
                }
                .firstOrNull().run {
                    UserPreferences(
                        this?.get(tmdbTokenKey)?.let {
                            it.ifBlank {
                                null
                            }
                        },
                    )
                }
        }
    }

    suspend fun saveIn(storage: DataStore<Preferences>) = withContext(Dispatchers.IO) {
        storage.edit { preferences ->
            preferences[tmdbTokenKey] = this@UserPreferences.tmdbToken ?: "" // because we can't store nullable pref keys
        }
    }
}