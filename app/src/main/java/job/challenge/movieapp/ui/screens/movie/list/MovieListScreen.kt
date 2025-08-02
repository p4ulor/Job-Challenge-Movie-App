package job.challenge.movieapp.ui.screens.movie.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.automirrored.filled.ArrowLeft
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.automirrored.filled.ArrowRightAlt
import androidx.compose.material.icons.filled.ArrowRightAlt
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import job.challenge.movieapp.ui.components.EzText
import job.challenge.movieapp.ui.components.LoadingSpinner
import job.challenge.movieapp.ui.components.MaterialIcons
import job.challenge.movieapp.ui.components.MaterialIconsExt
import job.challenge.movieapp.ui.components.util.CenteredRow
import job.challenge.movieapp.ui.components.util.LargePadding
import job.challenge.movieapp.ui.components.util.MediumPadding
import job.challenge.movieapp.ui.components.util.SmallMediumPadding
import job.challenge.movieapp.ui.components.util.SmallPadding
import job.challenge.movieapp.ui.theme.PreviewComposable

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
        MovieListScreenUi(moviesList, onNewPage = {
            vm.getNowPlaying(page = it)
        })
    } else {
        MovieListScreenUiNotGranted(hasConnection, isUserAuthenticated)
    }
}

private const val MAX_MOVIES_PER_ROW = 2

@Composable
fun MovieListScreenUi(moviesList: State<MovieList>, onNewPage: (Int) -> Unit) {
    when (moviesList) {
        is State.Loading -> {
            LoadingSpinner()
        }

        is State.Success -> {
            CenteredColumn {
                Column(
                    Modifier.fillMaxWidth().weight(1f),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LazyColumn( // Not using LazyVerticalGrid with columns = GridCells.Fixed(2) because it doesn't allow centering the columns vertically
                        contentPadding = PaddingValues(SmallMediumPadding),
                        verticalArrangement = Arrangement.spacedBy(
                            MediumPadding,
                            Alignment.CenterVertically
                        ),
                    ) {
                        items(moviesList.value.results.chunked(MAX_MOVIES_PER_ROW)) { moviePair ->
                            CenteredRow {
                                MovieListItem(moviePair.first())
                                moviePair.getOrNull(1)?.let { secondMovie ->
                                    MovieListItem(secondMovie)
                                } ?: MovieListItem(moviePair.first(), Modifier.alpha(0f)) // So that if the list is odd, the last item doesn't occupy the whole row width
                            }
                        }
                    }
                }

                CenteredRow(Modifier.padding(bottom = SmallMediumPadding)) {
                    Button(onClick = {
                        onNewPage(moviesList.value.page - 1)
                    },
                        enabled = moviesList.value.hasPrev()
                    ) {
                        MaterialIconsExt.ArrowLeft.apply {
                            Icon(this, this.name)
                        }
                    }

                    Text((moviesList.value.page).toString(), Modifier.padding(horizontal = SmallPadding), fontWeight = FontWeight.Bold)

                    Button(onClick = {
                        onNewPage(moviesList.value.page + 1)
                    },
                        enabled = moviesList.value.hasNext()
                    ) {
                        MaterialIconsExt.ArrowRight.apply {
                            Icon(this, this.name)
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
            repeat(3) { index ->
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
                        title = "Anime Anime Anime Anime Anime Anime Anime Anime Anime",
                        originalTitle = "Anime Anime Anime Anime Anime Anime",
                        releaseDate = "2025-06-06",
                        posterPathUrl = "https://image.tmdb.org//t/p/w500/aFRDH3P7TX61FVGpaLhKr6QiOC1.jpg",
                        voteAverage = 8.068f
                    )
                )

                add(
                    MovieList.MovieListItem(
                        id = index,
                        title = "Box",
                        originalTitle = "Anime Anime Anime Anime Anime Anime",
                        releaseDate = "2025-06-06",
                        posterPathUrl = "https://image.tmdb.org//t/p/w500/aFRDH3P7TX61FVGpaLhKr6QiOC1.jpg",
                        voteAverage = 8.068f
                    )
                )
            }
        },
        page = 1,
        totalPages = 2
    )
    MovieListScreenUi(State.Success(movies), {})
}

@Preview
@Composable
fun MovieListScreenUiLoadingPreview() = PreviewComposable {
    MovieListScreenUi(State.Loading, {})
}

@Preview
@Composable
fun MovieListScreenUiErrorPreview() = PreviewComposable {
    MovieListScreenUi(
        State.Error("HTTP Error: Invalid API key: You must be granted a valid key."), {}
    )
}