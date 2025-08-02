package job.challenge.movieapp.data.domain

import job.challenge.movieapp.data.repositories.ErrorResponse

open class PresetException(message: String) : Exception(message)

class NoBearerTokenSet : PresetException("No bearer token set")

class HttpError(
    body: ErrorResponse,
    errorCode: Int
): PresetException("HTTP Error - ${body.status_message}")
