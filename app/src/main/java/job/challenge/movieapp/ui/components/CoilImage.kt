package job.challenge.movieapp.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Scale
import job.challenge.movieapp.R
import job.challenge.movieapp.ui.components.util.MediumPadding
import job.challenge.movieapp.ui.components.util.RoundRectangleShape

@Composable
fun CoilImagePoster(url: String, size: Dp) {
    val ctx = LocalContext.current

    AsyncImage(
        model = ImageRequest.Builder(ctx)
            .data(url)
            .scale(Scale.FIT)
            .crossfade(true)
            .build(),
        contentDescription = "poster",
        Modifier
            .padding(MediumPadding)
            .clip(RoundRectangleShape)
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundRectangleShape),
        error = painterResource(R.drawable.question_mark),
    )
}