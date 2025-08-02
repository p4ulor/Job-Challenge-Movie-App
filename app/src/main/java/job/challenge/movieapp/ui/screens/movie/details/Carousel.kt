package job.challenge.movieapp.ui.screens.movie.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import job.challenge.movieapp.data.domain.Movie
import job.challenge.movieapp.data.domain.Movie.OtherMovies
import job.challenge.movieapp.ui.components.CoilImage
import job.challenge.movieapp.ui.components.SMALL_POSTER_HEIGHT
import job.challenge.movieapp.ui.components.util.RoundRectangleShape
import job.challenge.movieapp.ui.components.util.SmallPadding
import job.challenge.movieapp.ui.theme.PreviewComposable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieCarousel(movies: List<OtherMovies>, onNavToMovie: (Int) -> Unit) {
    HorizontalMultiBrowseCarousel(
        state = rememberCarouselState { movies.count() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp),
        preferredItemWidth = 186.dp
    ) { i ->
        val movie = movies[i]
        Box(Modifier.clickable {
            onNavToMovie(movie.id)
        }) {
            CoilImage(
                url = movie.backdropPathUrl,
                height = SMALL_POSTER_HEIGHT,
                modifier = Modifier.maskClip(RoundedCornerShape(100f)),
                addBorder = false
            )
            Text(
                movie.title,
                Modifier.align(Alignment.Center).padding(SmallPadding),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CarouselExample(movies: List<OtherMovies>) {
    HorizontalUncontainedCarousel(
        state = rememberCarouselState { movies.count() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp),
        itemWidth = 186.dp,
        itemSpacing = 18.dp, // key diff
        contentPadding = PaddingValues(horizontal = 26.dp)
    ) { i ->
        Box(Modifier.clip(RoundRectangleShape)) {
            CoilImage(movies[i].backdropPathUrl, SMALL_POSTER_HEIGHT)
            Text(
                movies[i].title,
                Modifier.align(Alignment.Center).padding(SmallPadding),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}
// [END android_compose_carousel_uncontained_basic]

@Preview
@Composable
fun CarouselExamples() = PreviewComposable {
    val movies = buildList {
        repeat(8) {
            add(
                OtherMovies(
                    id = 1234821,
                    title = "Jurassic World Rebirth",
                    backdropPathUrl = "https://image.tmdb.org/t/p/w500/njFixYzIxX8jsn6KMSEtAzi4avi.jpg",
                )
            )
        }
    }
    Column {
        CarouselExample(movies)
        MovieCarousel(movies, {})
    }
}