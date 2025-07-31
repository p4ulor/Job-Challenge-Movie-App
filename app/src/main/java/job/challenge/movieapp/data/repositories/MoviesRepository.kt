package job.challenge.movieapp.data.repositories

interface MoviesRepository {
    suspend fun getNowPlaying(): MoviesNowPlayingResponse
    suspend fun getMovieById(id: Int): MovieResponse
}