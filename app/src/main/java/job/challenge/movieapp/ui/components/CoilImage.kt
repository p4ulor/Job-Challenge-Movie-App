package job.challenge.movieapp.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Scale
import coil3.size.Size
import job.challenge.movieapp.R
import job.challenge.movieapp.ui.components.util.MediumPadding
import job.challenge.movieapp.ui.components.util.RoundRectangleShape
import job.challenge.movieapp.ui.components.util.addIfTrue

val SMALL_POSTER_HEIGHT = 126.dp

val BIG_POSTER_HEIGHT = 380.dp

@Composable
fun CoilImage(url: String, height: Dp, modifier: Modifier = Modifier, addBorder: Boolean = true) {
    val ctx = LocalContext.current

    AsyncImage(
        model = ImageRequest.Builder(ctx)
            .data(url)
            .size(Size.ORIGINAL)
            .scale(Scale.FIT)
            .crossfade(true)
            .build(),
        contentDescription = "poster",
        modifier = modifier
            .padding(MediumPadding)
            .heightIn(max = height)
            .clip(RoundRectangleShape)
            .addIfTrue(condition = addBorder) {
                Modifier.border(1.dp, MaterialTheme.colorScheme.outline, RoundRectangleShape)
            },
        contentScale = ContentScale.Fit,
        error = painterResource(R.drawable.question_mark),
    )
}