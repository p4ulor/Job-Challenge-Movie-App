enum class Screen {
    MovieList,
    MovieDetails;

    companion object {
        val HOME = MovieList
        fun from(string: String?) = Screen.entries.first { it.name == string }
    }
}