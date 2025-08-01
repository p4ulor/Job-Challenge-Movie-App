package job.challenge.movieapp.ui.animations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.runtime.Composable

@Composable //TODO use this
fun ScaleIn(isLoaded: Boolean, content: @Composable AnimatedVisibilityScope.() -> Unit){
    AnimatedVisibility(
        visible = isLoaded,
        enter = fadeIn(smooth()) + scaleIn(),
        exit = ExitTransition.None // improves performance when navigating
    ) {
        content()
    }
}