package job.challenge.movieapp.data.usecases

import job.challenge.movieapp.data.domain.Movie
import job.challenge.movieapp.data.domain.NoBearerTokenSet
import job.challenge.movieapp.data.repositories.MoviesRepository
import javax.inject.Inject

class MoviesDetailsUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    suspend fun getMovieById(id: Int): Movie? {
        return try {
            moviesRepository.getMovieById(id).let {
                Movie(it.id) //TODO()
            }
        } catch (error: NoBearerTokenSet){
            null
        }
    }
}