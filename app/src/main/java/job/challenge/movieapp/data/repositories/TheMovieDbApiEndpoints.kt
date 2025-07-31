package job.challenge.movieapp.data.repositories

import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.headers

class TheMovieDbApiEndpoints {
    sealed interface Resources {
        sealed interface Movie {
            object NowPlaying : Endpoint("/movie/now_playing", HttpMethod.Get)
            class Get(movieId: Int) : Endpoint("/movie/$movieId", HttpMethod.Get)
        }
    }

    abstract class Endpoint(endpointPath: String, val method: HttpMethod) {
        val path = "/$VERSION$endpointPath"
    }

    companion object {
        const val HOSTNAME = "api.themoviedb.org"
        const val VERSION = 3
        val defaultHeaders = headers {
            append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        }
    }
}