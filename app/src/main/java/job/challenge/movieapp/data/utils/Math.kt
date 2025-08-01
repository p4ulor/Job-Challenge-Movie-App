package job.challenge.movieapp.data.utils

import kotlin.math.pow

fun Float.trimToDecimals(ammount: Int): Float {
    val multiplier = 10.0.pow(ammount).toFloat()
    return (this * multiplier).toInt() / multiplier
}
