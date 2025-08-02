package job.challenge.movieapp.ui.screens.movie.details

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Reviews
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import job.challenge.movieapp.R
import job.challenge.movieapp.android.viewmodels.MovieDetailsViewModel
import job.challenge.movieapp.android.viewmodels.utils.State
import job.challenge.movieapp.data.domain.Movie
import job.challenge.movieapp.data.utils.trimToDecimals
import job.challenge.movieapp.ui.components.BIG_POSTER_HEIGHT
import job.challenge.movieapp.ui.components.CoilImage
import job.challenge.movieapp.ui.components.EzText
import job.challenge.movieapp.ui.components.LoadingSpinner
import job.challenge.movieapp.ui.components.MaterialIcons
import job.challenge.movieapp.ui.components.MaterialIconsExt
import job.challenge.movieapp.ui.components.util.CenteredColumn
import job.challenge.movieapp.ui.components.util.CenteredRow
import job.challenge.movieapp.ui.components.util.LargePadding
import job.challenge.movieapp.ui.components.util.MediumPadding
import job.challenge.movieapp.ui.components.util.RoundRectangleShape
import job.challenge.movieapp.ui.components.util.SmoothHorizontalDivider
import job.challenge.movieapp.ui.theme.PreviewComposable

@Composable
fun MovieDetailsScreen(
    movieId: Int? = null,
    vm: MovieDetailsViewModel = hiltViewModel()
){
    val movie by vm.movie.collectAsState()

    LaunchedEffect(Unit) {
        movieId?.let {
            vm.getMovieById(it)
        }
    }

    MovieDetailsScreenUi(movie)
}

@Composable
fun MovieDetailsScreenUi(movie: State<Movie>){
    when (movie) {
        is State.Loading -> {
            LoadingSpinner()
        }

        is State.Success -> {
            CenteredColumn(
                Modifier
                    .fillMaxSize()
                    .padding(MediumPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    movie.value.title,
                    fontWeight = FontWeight.Bold,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    movie.value.originalTitle,
                    Modifier.alpha(0.7f),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelMedium
                )

                CoilImage(
                    movie.value.backdropPathUrl,
                    BIG_POSTER_HEIGHT,
                    Modifier.fillMaxWidth()
                )

                Surface (
                    Modifier.border(1.dp, MaterialTheme.colorScheme.outline, RoundRectangleShape),
                    color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
                ){
                    Text(
                        "${movie.value.overview}",
                        Modifier.padding(MediumPadding),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }

                CenteredRow {
                    MaterialIcons.CalendarMonth.apply {
                        Icon(this, this.name)
                    }
                    Text(
                        "${movie.value.releaseDate}",
                        style = MaterialTheme.typography.labelSmall,
                    )
                }

                CenteredRow {
                    Icon(
                        painter = painterResource(R.drawable.person_raised_hand_google_fonts),
                        contentDescription = "vote count",
                    )
                    Text(
                        "${movie.value.voteCount}",
                        style = MaterialTheme.typography.labelSmall,
                        maxLines = 1
                    )

                    MaterialIcons.Reviews.apply {
                        Icon(this, this.name)
                    }
                    Text(
                        "${movie.value.voteAverage.trimToDecimals(2)}",
                        style = MaterialTheme.typography.labelSmall,
                        maxLines = 1
                    )

                    MaterialIconsExt.TrendingUp.apply {
                        Icon(this, this.name)
                    }
                    Text(
                        "${movie.value.popularity.trimToDecimals(2)}",
                        style = MaterialTheme.typography.labelSmall,
                        maxLines = 1
                    )
                }

                CenteredRow {
                    Text(
                        "${stringResource(R.string.languages)}:",
                        style = MaterialTheme.typography.labelSmall,
                        maxLines = 1
                    )
                    Text(
                        "${movie.value.spokenLanguages.joinToString()}",
                        style = MaterialTheme.typography.labelSmall,
                    )
                }

                SmoothHorizontalDivider(modifier = Modifier.padding(vertical = MediumPadding))
            }
        }

        is State.Error -> {
            CenteredColumn(Modifier.fillMaxSize().padding(LargePadding)) {
                Text(
                    movie.message,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            }
        }

        else -> { // Should not occur
            CenteredColumn(Modifier.fillMaxSize().padding(LargePadding)) {
                EzText(
                    R.string.a_highly_unexpected_error_occued,
                    textStyle = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}


@Preview
@Composable
fun MovieDetailsScreenUiPreview() = PreviewComposable {
    val movie = State.Success(Movie(
        id = 1234821,
        title = "Jurassic World Rebirth",
        originalTitle = "Jurassic World Rebirth",
        backdropPathUrl = "https://image.tmdb.org/t/p/w500/njFixYzIxX8jsn6KMSEtAzi4avi.jpg",
        overview = "Five years after the events of Jurassic World Dominion, covert operations expert Zora Bennett is contracted to lead a skilled team on a top-secret mission to secure genetic material from the world's three most massive dinosaurs. When Zora's operation intersects with a civilian family whose boating expedition was capsized, they all find themselves stranded on an island where they come face-to-face with a sinister, shocking discovery that's been hidden from the world for decades.",
        releaseDate = "2025-07-01",
        voteAverage = 6.3f,
        voteCount = 856,
        popularity = 219.0006f,
        spokenLanguages = listOf("EN", "FR")
    ))
    MovieDetailsScreenUi(movie)
}