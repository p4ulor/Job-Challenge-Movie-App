package job.challenge.movieapp.data.repositories

interface MoviesRepository {
    suspend fun getNowPlaying(page: Int?): MoviesNowPlayingResponse
    suspend fun getMovieById(id: Int): MovieResponse
}