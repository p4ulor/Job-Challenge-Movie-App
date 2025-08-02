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
    val dates: Dates,
    val page: Int,
    val results: Array<Result>,
    val total_pages: Int,
    val total_results: Int
) {
    // These are sub classes part of MoviesNowPlaying

    @Serializable
    data class Dates(
        val maximum: String,
        val minimum: String
    )

    @Serializable
    data class Result(
        val id: Int,
        val title: String,
        val original_title: String,
        val release_date: String,
        val poster_path: String, // already contains '/'. We can use it like https://image.tmdb.org/t/p/w500{backdrop_path}. Example: https://image.tmdb.org/t/p/w1280/53dsJ3oEnBhTBVMigWJ9tkA5bzJ.jpg
        val vote_average: Float
        // more fields could be added here, but this class only needs the fields for this challenge, ignoreUnknownKeys is enabled in Ktor
    )
}

/** Used in https://developer.themoviedb.org/reference/movie-details */
@Serializable
data class MovieResponse(
    val id: Int,
    val title: String,
    val original_title: String,
    val backdrop_path: String, // already contains '/'. We can use it like https://image.tmdb.org/t/p/w500{backdrop_path}. Example: https://image.tmdb.org/t/p/w1280/8J6UlIFcU7eZfq9iCLbgc8Auklg.jpg
    val overview: String,
    val release_date: String,
    val vote_average: Float,
    val vote_count: Int,
    // Additional movie information (popularity, language, etc.)
    val popularity: Float,
    val spoken_languages: List<SpokenLanguage>
){

    @Serializable
    data class SpokenLanguage(
        val iso_639_1: String, // "en", "fr", etc
    )
}


