package job.challenge.movieapp.ui.animations

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween

/** [EaseInEaseOut](https://cubic-bezier.com/#.5,0,.5,1)*/
val EaseInEaseOut = CubicBezierEasing(.5f, 0f, .5f, 1f)

fun <T> smooth(durationMillis: Int = 300, delayMillis: Int = 0) = tween<T>(
    durationMillis = durationMillis,
    delayMillis = delayMillis,
    easing = EaseInEaseOut
)
