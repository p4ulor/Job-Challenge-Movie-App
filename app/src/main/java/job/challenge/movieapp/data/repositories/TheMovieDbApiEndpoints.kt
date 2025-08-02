package job.challenge.movieapp.data.repositories

import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.headers

class TheMovieDbApiEndpoints {
    sealed interface Resources {
        sealed interface Movie {
            object NowPlaying : EndPoint("/movie/now_playing", HttpMethod.Get)
            class Get(movieId: Int) : EndPoint("/movie/$movieId", HttpMethod.Get)
        }
        sealed interface Picture {
            class Width500(backdrop_path: String) : ImageEndPoint("/t/p/w500$backdrop_path")
        }
    }

    abstract class EndPoint(endpointPath: String, val method: HttpMethod) {
        val path = "/$VERSION$endpointPath"
    }

    abstract class ImageEndPoint(endpointPath: String) {
        val path = "https://$HOSTNAME_IMAGE/$endpointPath"
    }

    companion object {
        const val HOSTNAME_IMAGE = "image.tmdb.org"
        const val HOSTNAME = "api.themoviedb.org"
        const val VERSION = 3
        val defaultHeaders = headers {
            append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        }
    }

    enum class QueryKey (val minValue: Int) {
        page(minValue = 1)
    }
}