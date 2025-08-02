package job.challenge.movieapp.android.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import job.challenge.movieapp.android.utils.NetworkObserver
import job.challenge.movieapp.android.viewmodels.utils.State
import job.challenge.movieapp.android.viewmodels.utils.launch
import job.challenge.movieapp.data.domain.Movie
import job.challenge.movieapp.data.domain.MovieList
import job.challenge.movieapp.data.local.preferences.UserPreferences
import job.challenge.movieapp.data.local.preferences.dataStore
import job.challenge.movieapp.data.usecases.MovieDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    @ApplicationContext private val ctx: Context,
    private val movieListUseCase: MovieDetailsUseCase,
    val networkObserver: NetworkObserver,
) : ViewModel() {

    private val _movie = MutableStateFlow<State<Movie>>(State.None)
    val movie = _movie.asStateFlow()

    fun getMovieById(id: Int) {
        launch {
            _movie.value = State.Loading
            //_movie.value = movieListUseCase.getMovieById()
        }
    }
}