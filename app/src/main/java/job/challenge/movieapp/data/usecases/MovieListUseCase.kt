package job.challenge.movieapp.data.usecases

import job.challenge.movieapp.android.viewmodels.utils.State
import job.challenge.movieapp.data.domain.MovieList
import job.challenge.movieapp.data.domain.PresetException
import job.challenge.movieapp.data.repositories.MoviesRepository
import job.challenge.movieapp.data.repositories.TheMovieDbApiEndpoints
import job.challenge.movieapp.e
import javax.inject.Inject

/**
 * Use cases are used in ViewModels. They handle exceptions from the repository, interpret exceptions
 * and bridge the gap between the (UI) domain later and the (raw) data layer coming from the repositories
 */
class MovieListUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    suspend fun getNowPlaying(page: Int?): State<MovieList> {
        return try {
            moviesRepository.getNowPlaying(page).let {
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