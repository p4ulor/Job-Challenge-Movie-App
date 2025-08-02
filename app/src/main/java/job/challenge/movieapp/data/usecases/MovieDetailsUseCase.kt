package job.challenge.movieapp.data.usecases

import job.challenge.movieapp.android.utils.NetworkObserver
import job.challenge.movieapp.android.viewmodels.utils.State
import job.challenge.movieapp.data.domain.Movie
import job.challenge.movieapp.data.domain.PresetException
import job.challenge.movieapp.data.repositories.MoviesRepository
import job.challenge.movieapp.data.repositories.TheMovieDbApiEndpoints
import job.challenge.movieapp.e
import job.challenge.movieapp.i
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale
import javax.inject.Inject

/**
 * Use cases are used in ViewModels. They handle exceptions from the repository, interpret exceptions
 * and bridge the gap between the (UI) domain later and the (raw) data layer coming from the repositories
 */
class MovieDetailsUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
    val networkObserver: NetworkObserver,
) {

    private val _movie = MutableStateFlow<State<Movie>>(State.Loading)
    val movie = _movie.asStateFlow()

    suspend fun getMovieById(id: Int) {
        if (id == (movie.value as? State.Success )?.value?.id) {
            return // to avoid unnecessary calls, when changing screens
        }

        _movie.value = try {
            _movie.value = State.Loading

            // extra - get 5 random movies
            val randomMovies = getRandomMovies(5).filterNotNull()

            moviesRepository.getMovieById(id).let {
                Movie(
                    it.id,
                    title = it.title,
                    originalTitle = it.original_title,
                    backdropPathUrl = it.backdrop_path.let { pathSegment ->
                        TheMovieDbApiEndpoints.Resources.Picture.Width500(pathSegment).path
                    },
                    overview = it.overview,
                    releaseDate = it.release_date,
                    voteAverage = it.vote_average,
                    voteCount = it.vote_count,
                    popularity = it.popularity,
                    spokenLanguages = it.spoken_languages.map { it.iso_639_1.uppercase(Locale.getDefault()) },
                    otherMovies = randomMovies
                )
            }.let { movie ->
                State.Success(movie)
            }
        } catch (preset: PresetException){
            State.Error(preset.message.toString())
        } catch (exception: Exception) {
            e("Unhandled exception ${exception.message}")
            State.Error("Unhandled exception")
        }
    }

    private suspend fun getRandomMovies(amount: Int) = coroutineScope {
        val randomMovieIds = buildList {
            repeat(amount) {
                add((1..100000).random())
            }
        }

        val deferredResults = randomMovieIds.map { randomId ->
            async {
                runCatching {
                    moviesRepository.getMovieById(randomId).let {
                        Movie.OtherMovies(
                            id = it.id,
                            title = it.title,
                            backdropPathUrl = it.backdrop_path.let { pathSegment ->
                                TheMovieDbApiEndpoints.Resources.Picture.Width500(pathSegment).path
                            },
                        )
                    }
                }.onFailure {
                    i("getRandomMovies failed on id $randomId") // some movies no longer exist apparently
                }.getOrNull()
            }
        }
        deferredResults.awaitAll()
    }
}