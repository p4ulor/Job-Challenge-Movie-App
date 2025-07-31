package job.challenge.movieapp.data.repositories

import kotlinx.serialization.Serializable


/**
 * Error definition. Per example when accessing https://api.themoviedb.org/3/movie/now_playing
 * in the browser (a GET request with no token sent)
 */
@Serializable
data class ErrorResponse(
    val status_code: Int,
    val status_message: String,
    val success: Boolean
)

/** Used in https://developer.themoviedb.org/reference/movie-now-playing-list */
@Serializable
data class MoviesNowPlayingResponse(
    val dates: ListDates,
    val page: Int,
    val results: Array<MovieListItem>,
    val total_pages: Int,
    val total_results: Int
) {
    // These are sub classes part of MoviesNowPlaying

    @Serializable
    data class ListDates(
        val maximum: String,
        val minimum: String
    )

    @Serializable
    data class MovieListItem(
        val id: Int,
        // TODO()
        // more fields could be added here (the class only has the used fields for this challenge, ignoreUnknownKeys is enabled in Ktor
    )
}

/** Used in https://developer.themoviedb.org/reference/movie-details */
@Serializable
data class MovieResponse(
    val id: Int,
    val title: String
    // TODO()
){

}


