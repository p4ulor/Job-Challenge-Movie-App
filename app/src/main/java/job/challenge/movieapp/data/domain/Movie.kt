package job.challenge.movieapp.data.domain

data class Movie(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val backdropPathUrl: String,
    val overview: String,
    val releaseDate: String,
    val voteAverage: Float,
    val voteCount: Int,
    // Additional movie information (popularity, language, etc.)
    val popularity: Float,
    val spokenLanguages: List<String>
)
