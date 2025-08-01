package job.challenge.movieapp.data.usecases

import job.challenge.movieapp.data.domain.MovieList
import job.challenge.movieapp.data.domain.NoBearerTokenSet
import job.challenge.movieapp.data.repositories.MoviesRepository
import javax.inject.Inject

/**
 * Use cases are used in ViewModels. They handle exceptions from the repository and bridge
 * the gap between the (UI) domain later and the (raw) data layer coming from the repositories
 */
class MovieListUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    suspend fun getNowPlaying(): MovieList? {
        return try {
            moviesRepository.getNowPlaying().let {
                MovieList(it.toString()) //TODO
            }
        } catch (error: NoBearerTokenSet){
            null
        }
    }
}