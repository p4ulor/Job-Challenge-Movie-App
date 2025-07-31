enum class Screens {
    MovieList,
    MovieDetails;

    companion object {
        fun from(string: String?) = Screens.entries.first { it.name == string }
    }
}