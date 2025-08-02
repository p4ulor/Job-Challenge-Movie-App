package job.challenge.movieapp.data.repositories

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.client.call.body
import job.challenge.movieapp.data.client.KtorHttpClient
import job.challenge.movieapp.data.client.QueryParam
import job.challenge.movieapp.data.client.handle
import job.challenge.movieapp.data.domain.HttpError
import job.challenge.movieapp.data.domain.NoBearerTokenSet
import job.challenge.movieapp.data.domain.PresetException
import job.challenge.movieapp.data.local.preferences.UserPreferences
import job.challenge.movieapp.data.local.preferences.dataStore
import job.challenge.movieapp.e
import javax.inject.Inject

/** Repository for The Movie DB API1 */
class TheMovieDbRepository @Inject constructor(
    @ApplicationContext private val ctx: Context // IMO, this is the cleanest way to pass the token to Ktor
) : MoviesRepository {
    private val http = KtorHttpClient(TheMovieDbApiEndpoints.HOSTNAME)

    /**
     * Gets a list of movies that are currently in theatres.
     * https://developer.themoviedb.org/reference/movie-now-playing-list
     */
    @Throws(PresetException::class)
    override suspend fun getNowPlaying(page: Int?): MoviesNowPlayingResponse {
        checkAuthorization()
        val path = TheMovieDbApiEndpoints.Resources.Movie.NowPlaying.path
        val queryParams = listOf(
            QueryParam(
                TheMovieDbApiEndpoints.QueryKey.page.name,
                page?.toString() ?: TheMovieDbApiEndpoints.QueryKey.page.default.toString()
            )
        )
        return http.get(path, queryParams)
            .handle(
                onSuccess = {
                    it.body<MoviesNowPlayingResponse>()
                },
                onFailure = {
                    e("HttpError ${it.status.value}")
                    throw HttpError(it.body<ErrorResponse>(), it.status.value)
                }
            )
    }

    /**
     * Get the top level details of a movie by ID.
     * https://developer.themoviedb.org/reference/movie-details
     */
    @Throws(PresetException::class)
    override suspend fun getMovieById(id: Int): MovieResponse {
        checkAuthorization()
        val path = TheMovieDbApiEndpoints.Resources.Movie.Get(id).path
        return http.get(path)
            .handle(
                onSuccess = {
                    it.body<MovieResponse>()
                },
                onFailure = {
                    e("HttpError ${it.status.value}")
                    throw HttpError(it.body<ErrorResponse>(), it.status.value)
                }
            )
    }

    private suspend fun checkAuthorization() {
        val tmdbToken = UserPreferences.getFrom(ctx.dataStore).tmdbToken
        if (tmdbToken == null) {
            e("Bearer token is not set")
            throw NoBearerTokenSet()
        } else {
            http.setBearerToken(tmdbToken)
        }
    }
}
