package job.challenge.movieapp.data.domain

data class MovieList(
    val results: List<MovieListItem>,
    val page: Int,
    val totalPages: Int
) {
    data class MovieListItem(
        val id: Int,
        val title: String,
        val originalTitle: String,
        val releaseDate: String,
        val posterPathUrl: String,
        val voteAverage: Float
    )

    fun hasPrev() = page != 1
    fun hasNext() = page != totalPages
}
