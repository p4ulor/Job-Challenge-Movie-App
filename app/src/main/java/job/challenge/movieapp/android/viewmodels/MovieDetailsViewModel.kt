package job.challenge.movieapp.android.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import job.challenge.movieapp.android.utils.NetworkObserver
import job.challenge.movieapp.android.viewmodels.utils.launch
import job.challenge.movieapp.data.usecases.MovieDetailsUseCase
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    @ApplicationContext private val ctx: Context,
    private val movieDetailsUseCase: MovieDetailsUseCase,
    val networkObserver: NetworkObserver,
) : ViewModel() {

    val movie = movieDetailsUseCase.movie

    fun getMovieById(id: Int) {
        launch {
            movieDetailsUseCase.getMovieById(id)
        }
    }
}