package job.challenge.movieapp.android.viewmodels.utils

/** Useful class to represent the state of data mutations */
sealed class State<out T: Any> {
    data class Success<out T : Any>(val value: T) : State<T>()
    data object Loading : State<Nothing>()
    data class Error(val message: String) : State<Nothing>()
    data object None : State<Nothing>()
}