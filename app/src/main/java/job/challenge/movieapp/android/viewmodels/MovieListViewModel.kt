package job.challenge.movieapp.android.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import job.challenge.movieapp.android.utils.NetworkObserver
import job.challenge.movieapp.android.viewmodels.utils.launch
import job.challenge.movieapp.data.domain.MovieList
import job.challenge.movieapp.data.local.preferences.UserPreferences
import job.challenge.movieapp.data.local.preferences.dataStore
import job.challenge.movieapp.data.usecases.MovieListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    @ApplicationContext private val ctx: Context,
    private val movieListUseCase: MovieListUseCase,
    val networkObserver: NetworkObserver,
) : ViewModel() {

    private val prefs= MutableStateFlow<UserPreferences?>(null)

    private val _isUserAuthenticated = MutableStateFlow(false)
    val isUserAuthenticated = _isUserAuthenticated.asStateFlow()

    private val _movieList = MutableStateFlow<MovieList?>(null)
    val movieList = _movieList.asStateFlow()

    fun loadUserPrefs(){
        launch {
            UserPreferences.getFrom(ctx.dataStore).also {
                if (it.tmdbToken != null) {
                    _isUserAuthenticated.value = true
                }
            }
        }
    }

    fun saveUserPrefs(newPrefs: UserPreferences) {
        launch {
            newPrefs.saveIn(ctx.dataStore)
            _isUserAuthenticated.value = true
        }
    }

    fun getNowPlaying() {
        launch {
            _movieList.value = movieListUseCase.getNowPlaying()
        }
    }
}