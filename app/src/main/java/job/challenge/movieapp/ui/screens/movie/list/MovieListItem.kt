package job.challenge.movieapp.ui.screens.movie.list

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Reviews
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import job.challenge.movieapp.data.domain.MovieList
import job.challenge.movieapp.data.utils.trimToDecimals
import job.challenge.movieapp.ui.components.CoilImage
import job.challenge.movieapp.ui.components.MaterialIcons
import job.challenge.movieapp.ui.components.MaterialIconsExt
import job.challenge.movieapp.ui.components.SMALL_POSTER_HEIGHT
import job.challenge.movieapp.ui.components.util.CenteredColumn
import job.challenge.movieapp.ui.components.util.CenteredRow
import job.challenge.movieapp.ui.components.util.MediumPadding
import job.challenge.movieapp.ui.components.util.RoundRectangleShape
import job.challenge.movieapp.ui.theme.PreviewComposable
import job.challenge.movieapp.ui.theme.getRandomContainerColor

@Composable
fun RowScope.MovieListItem(item: MovieList.MovieListItem, modifier: Modifier = Modifier) {
    Surface(
        modifier
            .weight(1f) // So that items in the row are distributed equally
            .clip(RoundRectangleShape)
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundRectangleShape),
        color = getRandomContainerColor()
    ) {
        CenteredColumn(Modifier.padding(MediumPadding)) {
            Text(
                item.title,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
            Text(
                item.originalTitle,
                Modifier.alpha(0.7f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelSmall
            )
            CoilImage(item.posterPathUrl, SMALL_POSTER_HEIGHT)
            CenteredRow(Modifier) {
                MaterialIcons.CalendarMonth.apply {
                    Icon(this, this.name)
                }
                Text(
                    "${item.releaseDate}",
                    style = MaterialTheme.typography.labelSmall,
                )
                MaterialIcons.Reviews.apply {
                    Icon(this, this.name)
                }
                Text(
                    "${item.voteAverage.trimToDecimals(2)}",
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 1
                )
            }
        }
    }
}

@Preview
@Composable
fun MovieListItemPreview() = PreviewComposable {
    CenteredRow {
        MovieListItem(MovieList.MovieListItem(
            id = 1,
            title = "Title",
            originalTitle = "originalTitle",
            releaseDate = "2025-06-06",
            posterPathUrl = "https://image.tmdb.org/t/p/w1280/53dsJ3oEnBhTBVMigWJ9tkA5bzJ.jpg",
            voteAverage = 8.068f
        ))
    }
}