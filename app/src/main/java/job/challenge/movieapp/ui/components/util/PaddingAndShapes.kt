package job.challenge.movieapp.ui.components.util

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * Gets the height of the system's navigation bar (recent items, home, back). Useful to setting
 * the height of the app's bottomBar when using [enableEdgeToEdge]. And this method seems to be
 * kinda blocking. In my Samsung it's 48.dp
 */
val SystemNavigationBarHeight
    @Composable
    get() = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

val TinyPadding = 2.dp
val SmallPadding = 4.dp
val SmallMediumPadding = 8.dp
val MediumPadding = 12.dp
val MediumLarge = 24.dp

val RoundRectangleShape: Shape = RoundedCornerShape(30f)