package job.challenge.movieapp.android.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import job.challenge.movieapp.android.utils.NetworkObserver
import job.challenge.movieapp.android.viewmodels.utils.launch
import job.challenge.movieapp.data.usecases.MovieListUseCase
import job.challenge.movieapp.i
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    @ApplicationContext private val ctx: Context,
    private val movieListUseCase: MovieListUseCase,
    val networkObserver: NetworkObserver,
) : ViewModel() {

    init {
        i("init MovieListViewModel")
        launch {
            combine(networkObserver.hasConnection, movieListUseCase.isUserAuthenticated) { hasConnection, isUserAuthenticated ->
                hasConnection && isUserAuthenticated
            }.collect { isScreenGranted ->
                if (isScreenGranted) {
                    getNowPlaying()
                }
            }
        }
    }

    val movieList = movieListUseCase.movieList
    val isUserAuthenticated = movieListUseCase.isUserAuthenticated

    fun loadUserPrefs(){
        launch {
            movieListUseCase.loadUserPrefs(ctx)
        }
    }

    fun getNowPlaying(page: Int? = null) {
        launch {
            movieListUseCase.getNowPlaying(page)
        }
    }

    override fun onCleared() {
        i("MovieListViewModel destroyed")
    }
}