package job.challenge.movieapp.data.client

import io.ktor.client.statement.HttpResponse
import io.ktor.http.Headers
import io.ktor.http.HeadersBuilder
import io.ktor.http.isSuccess

/**
 * [R] a type of a [HttpResponse]
 * [D] a type from data.domain
 */
suspend fun <R: HttpResponse, D> R.handle(
    onSuccess: suspend (HttpResponse) -> D,
    onFailure: suspend (HttpResponse) -> D
) : D {
    return if (status.isSuccess()) {
        onSuccess(this)
    } else {
        onFailure(this)
    }
}

fun HeadersBuilder.addAll(headers: Headers){
    headers.forEach { key, values ->
        appendAll(key, values)
    }
}

typealias QueryParams = List<QueryParam>

/** [Pair.first] is the query key and [Pair.second] is the query value */
typealias QueryParam = Pair<String, String>
