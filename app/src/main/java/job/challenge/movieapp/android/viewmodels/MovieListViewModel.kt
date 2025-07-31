package job.challenge.movieapp.android.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import job.challenge.movieapp.android.viewmodels.utils.launch
import job.challenge.movieapp.data.local.preferences.UserPreferences
import job.challenge.movieapp.data.local.preferences.dataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MovieListViewModel(private val ctx: Context) : ViewModel() {

    private val _prefs= MutableStateFlow<UserPreferences?>(null)
    val prefs = _prefs.asStateFlow()

    fun loadUserPrefs(){
        launch {
            UserPreferences.getFrom(ctx.dataStore).also {
                _prefs.value = it
            }
        }
    }

    fun saveUserPrefs(newPrefs: UserPreferences) {
        _prefs.value = newPrefs
        launch {
            newPrefs.saveIn(ctx.dataStore)
        }
    }

    fun getMovieById(int: Int) {

    }
}