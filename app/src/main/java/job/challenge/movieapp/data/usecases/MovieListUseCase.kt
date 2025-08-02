package job.challenge.movieapp.data.usecases

import android.content.Context
import job.challenge.movieapp.android.viewmodels.utils.State
import job.challenge.movieapp.android.viewmodels.utils.launch
import job.challenge.movieapp.data.domain.MovieList
import job.challenge.movieapp.data.domain.PresetException
import job.challenge.movieapp.data.local.preferences.UserPreferences
import job.challenge.movieapp.data.local.preferences.dataStore
import job.challenge.movieapp.data.repositories.MoviesRepository
import job.challenge.movieapp.data.repositories.TheMovieDbApiEndpoints
import job.challenge.movieapp.e
import job.challenge.movieapp.i
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * Use cases are used in ViewModels. They handle exceptions from the repository, interpret exceptions
 * and bridge the gap between the (UI) domain later and the (raw) data layer coming from the repositories.
 * And because Hilt destroys viewmodels when exiting the composable screen
 */
class MovieListUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    private val _isUserAuthenticated = MutableStateFlow(false)
    val isUserAuthenticated = _isUserAuthenticated.asStateFlow()

    private val _movieList = MutableStateFlow<State<MovieList>>(State.None)
    val movieList = _movieList.asStateFlow()

    suspend fun loadUserPrefs(ctx: Context){
        UserPreferences.getFrom(ctx.dataStore).also {
            if (it.tmdbToken != null) {
                _isUserAuthenticated.value = true
            }
        }
    }

    suspend fun getNowPlaying(page: Int?) {
        val latestPageOpened = (movieList.value as? State.Success)?.value?.page
        val currentPage = page ?: latestPageOpened
        i("getNowPlaying requestedPage=$page currentPage=$currentPage")
        if (page == null && currentPage != null) {
            return // to avoid unnecessary calls, when changing screens
        }

        _movieList.value = try {
            _movieList.value = State.Loading
            moviesRepository.getNowPlaying(currentPage).let {
                MovieList(
                    results = it.results.map { item ->
                        MovieList.MovieListItem(
                            id = item.id,
                            title = item.title,
                            originalTitle = item.original_title,
                            releaseDate = item.release_date,
                            posterPathUrl = item.poster_path.let { pathSegment ->
                                TheMovieDbApiEndpoints.Resources.Picture.Width500(pathSegment).path
                            },
                            voteAverage = item.vote_average
                        )
                    },
                    page = it.page,
                    totalPages = it.total_pages
                ).let { moviesList ->
                    State.Success(moviesList)
                }
            }
        } catch (preset: PresetException){
            State.Error(preset.message.toString())
        } catch (exception: Exception) {
            e("Unhandled exception ${exception.message}")
            State.Error("Unhandled exception")
        }
    }
}