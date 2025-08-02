package job.challenge.movieapp.data.usecases

import job.challenge.movieapp.android.viewmodels.utils.State
import job.challenge.movieapp.data.domain.Movie
import job.challenge.movieapp.data.domain.PresetException
import job.challenge.movieapp.data.repositories.MoviesRepository
import job.challenge.movieapp.data.repositories.TheMovieDbApiEndpoints
import job.challenge.movieapp.e
import java.util.Locale
import javax.inject.Inject

/**
 * Use cases are used in ViewModels. They handle exceptions from the repository, interpret exceptions
 * and bridge the gap between the (UI) domain later and the (raw) data layer coming from the repositories
 */
class MovieDetailsUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    suspend fun getMovieById(id: Int): State<Movie> {
        return try {
            moviesRepository.getMovieById(id).let {
                Movie(
                    it.id,
                    title = it.title,
                    originalTitle = it.original_title,
                    backdropPathUrl = it.belongs_to_collection.backdrop_path.let { pathSegment ->
                        TheMovieDbApiEndpoints.Resources.Picture.Width500(pathSegment).path
                    },
                    overview = it.overview,
                    releaseDate = it.release_date,
                    voteAverage = it.vote_average,
                    voteCount = it.vote_count,
                    popularity = it.popularity,
                    spokenLanguages = it.spoken_languages.map { it.iso_639_1.uppercase(Locale.getDefault()) }
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
}