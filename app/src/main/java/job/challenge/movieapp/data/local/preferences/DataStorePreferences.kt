package job.challenge.movieapp.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

/** Read docs of [preferencesDataStore] */
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "user-preferences"
)
