package job.challenge.movieapp.ui.screens.movie.list

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import job.challenge.movieapp.android.viewmodels.MovieListViewModel
import job.challenge.movieapp.ui.components.util.CenteredColumn
import androidx.hilt.navigation.compose.hiltViewModel
import job.challenge.movieapp.R
import job.challenge.movieapp.android.viewmodels.utils.State
import job.challenge.movieapp.data.domain.MovieList
import job.challenge.movieapp.data.utils.trimToDecimals
import job.challenge.movieapp.i
import job.challenge.movieapp.ui.components.CoilImagePoster
import job.challenge.movieapp.ui.components.EzText
import job.challenge.movieapp.ui.components.LoadingSpinner
import job.challenge.movieapp.ui.components.MaterialIcons
import job.challenge.movieapp.ui.components.MaterialIconsExt
import job.challenge.movieapp.ui.components.util.CenteredRow
import job.challenge.movieapp.ui.components.util.LargePadding
import job.challenge.movieapp.ui.components.util.MediumPadding
import job.challenge.movieapp.ui.components.util.RoundRectangleShape
import job.challenge.movieapp.ui.components.util.SmallMediumPadding
import job.challenge.movieapp.ui.theme.PreviewComposable
import job.challenge.movieapp.ui.theme.getRandomContainerColor
import kotlin.math.pow

@Composable
fun MovieListScreen(
    vm: MovieListViewModel = hiltViewModel()
) {
    val hasConnection by vm.networkObserver.hasConnection.collectAsState(initial = false)
    val isUserAuthenticated by vm.isUserAuthenticated.collectAsState()
    val moviesList by vm.movieList.collectAsState()
    var isScreenGranted by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        vm.loadUserPrefs()
    }

    LaunchedEffect (hasConnection, isUserAuthenticated) {
        isScreenGranted = hasConnection && isUserAuthenticated
        if (isScreenGranted) {
            vm.getNowPlaying()
        }
    }

    if (isScreenGranted) {
        MovieListScreenUi(moviesList)
    } else {
        MovieListScreenUiNotGranted(hasConnection, isUserAuthenticated)
    }
}

private const val MAX_MOVIES_PER_ROW = 2
private val MAX_POSTER_HEIGHT = 128.dp

@Composable
fun MovieListScreenUi(moviesList: State<MovieList>) {
    when (moviesList) {
        is State.Loading -> LoadingSpinner()

        is State.Success -> {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyVerticalGrid( // Instead of this we could have used LazyColumn and items(moviesList.chunked(2)) to center the items vertically
                    columns = GridCells.Fixed(MAX_MOVIES_PER_ROW),
                    contentPadding = PaddingValues(SmallMediumPadding),
                    horizontalArrangement = Arrangement.spacedBy(
                        space = MediumPadding,
                        alignment = Alignment.CenterHorizontally
                    ),
                    verticalArrangement = Arrangement.spacedBy(
                        MediumPadding,
                        Alignment.CenterVertically
                    ),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(moviesList.value.results) {item ->
                        Surface(
                            Modifier.fillMaxWidth()
                                .fillMaxHeight()
                                .clip(RoundRectangleShape)
                                .border(1.dp, MaterialTheme.colorScheme.outline, RoundRectangleShape),
                            color = getRandomContainerColor()
                        ) {
                            CenteredColumn(Modifier.fillMaxWidth().padding(MediumPadding)) {
                                Text(item.title, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                                Text(item.originalTitle, Modifier.alpha(0.7f), textAlign = TextAlign.Center, style = MaterialTheme.typography.labelSmall)
                                CoilImagePoster(item.posterPathUrl, MAX_POSTER_HEIGHT)
                                CenteredRow {
                                    MaterialIcons.CalendarMonth.apply {
                                        Icon(this, this.name)
                                    }
                                    Text(
                                        "${item.releaseDate}",
                                        style = MaterialTheme.typography.labelSmall,
                                    )
                                    MaterialIconsExt.TrendingUp.apply {
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
                }
            }
        }

        is State.Error -> {
            CenteredColumn(Modifier.fillMaxSize().padding(LargePadding)) {
                Text(
                    moviesList.message,
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
@Composable // The images will shown when running it on a real device
fun MovieListScreenUiPreview() = PreviewComposable {
    val movies = MovieList(
        results = buildList {
            repeat(10) { index ->
                add(
                    MovieList.MovieListItem(
                        id = index,
                        title = "How to Train Your Dragon",
                        originalTitle = "How to Train Your Dragon",
                        releaseDate = "2025-06-06",
                        posterPathUrl = "https://image.tmdb.org/t/p/w1280/53dsJ3oEnBhTBVMigWJ9tkA5bzJ.jpg",
                        voteAverage = 8.068f
                    )
                )

                add(
                    MovieList.MovieListItem(
                        id = index,
                        title = "Anime Anime Anime Anime Anime Anime",
                        originalTitle = "Anime Anime Anime Anime Anime Anime",
                        releaseDate = "2025-06-06",
                        posterPathUrl = "https://image.tmdb.org//t/p/w500/aFRDH3P7TX61FVGpaLhKr6QiOC1.jpg",
                        voteAverage = 8.068f
                    )
                )
            }
        },
        page = 1,
        totalPages = 1
    )
    MovieListScreenUi(State.Success(movies))
}

@Preview
@Composable
fun MovieListScreenUiLoadingPreview() = PreviewComposable {
    val movies = null
    MovieListScreenUi(State.Loading)
}

@Preview
@Composable
fun MovieListScreenUiErrorPreview() = PreviewComposable {
    MovieListScreenUi(State.Error("HTTP Error: Invalid API key: You must be granted a valid key."))
}