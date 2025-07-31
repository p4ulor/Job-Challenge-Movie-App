package job.challenge.movieapp.data.domain

import job.challenge.movieapp.data.repositories.ErrorResponse

class NoBearerTokenSet: Exception("No bearer token set")

class HttpError(
    val body: ErrorResponse,
    errorCode: Int
): Exception("HTTP error occured with code $errorCode")
