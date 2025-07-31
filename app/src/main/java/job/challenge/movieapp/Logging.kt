package job.challenge.movieapp

import android.util.Log

private val versionName = BuildConfig.VERSION_NAME
val TAG = "movie-app(${versionName})"

inline fun <reified T> T.i(s: String) =
    Log.i("$TAG ${T::class.simpleName}", "$TAG - $s")

inline fun <reified T> T.e(s: String) =
    Log.e("$TAG ${T::class.simpleName}", "$TAG - $s")

inline fun <reified T> T.d(s: String) = runIfInDebug {
    Log.d("$TAG ${T::class.simpleName}", "$TAG - $s")
}

fun i(s: String) = Log.i(TAG, s)
fun e(s: String) = Log.e(TAG, s)
fun d(s: String) = runIfInDebug { Log.d(TAG, s) }

fun runIfInDebug(block: () -> Unit){
    if (BuildConfig.DEBUG) {
        block()
    }
}