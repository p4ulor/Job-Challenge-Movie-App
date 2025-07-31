package job.challenge.movieapp

import io.ktor.client.call.body
import job.challenge.movieapp.data.client.KtorHttpClient
import job.challenge.movieapp.data.client.handle
import job.challenge.movieapp.data.domain.HttpError
import job.challenge.movieapp.data.repositories.ErrorResponse
import job.challenge.movieapp.data.repositories.MovieResponse
import job.challenge.movieapp.data.repositories.TheMovieDbApiEndpoints
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals


/**
 * To not add the Mockk library here, please comment out all android.util.Log logs in Logging.kt before running this
 */
class ExampleUnitTest {
    /**
     * The tests require their own resources (app/src/test/resources) so
     * [javaClass.classLoader.getResource] is used to get API key and an image
     */
    @Test
    fun `Call Movies API`() = runTest {
        with(javaClass.classLoader){
            val token = getResource("bearer_token.txt").readText().trim()!! //trim to remove next lines
            val http = KtorHttpClient(TheMovieDbApiEndpoints.HOSTNAME)
            http.setBearerToken(token)
            val path = TheMovieDbApiEndpoints.Resources.Movie.Get(1234821).path
            http.get(path)
                .handle(
                    onSuccess = {
                        val res = it.body<MovieResponse>()
                        assertEquals(
                            expected = "Jurassic World Rebirth",
                            actual = res.title
                        )
                        println("Obtained ${res.title}")
                    },
                    onFailure = {
                        e("HttpError ${it.status.value}")
                        println("Obtained ${it.body<ErrorResponse>()}")
                    }
                )
        }
    }
}