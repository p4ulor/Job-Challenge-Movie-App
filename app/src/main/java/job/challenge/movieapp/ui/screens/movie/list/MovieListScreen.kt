package job.challenge.movieapp.ui.screens.movie.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.automirrored.filled.ArrowLeft
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import job.challenge.movieapp.android.viewmodels.MovieListViewModel
import job.challenge.movieapp.android.viewmodels.utils.State
import job.challenge.movieapp.data.domain.Movie
import job.challenge.movieapp.data.domain.MovieList
import job.challenge.movieapp.ui.animations.ScaleIn
import job.challenge.movieapp.ui.components.MaterialIconsExt
import job.challenge.movieapp.ui.components.ScreenUiNonSuccessCommon
import job.challenge.movieapp.ui.components.util.CenteredColumn
import job.challenge.movieapp.ui.components.util.CenteredRow
import job.challenge.movieapp.ui.components.util.MediumPadding
import job.challenge.movieapp.ui.components.util.SmallMediumPadding
import job.challenge.movieapp.ui.components.util.SmallPadding
import job.challenge.movieapp.ui.theme.PreviewComposable
import kotlinx.coroutines.delay

@Composable
fun MovieListScreen(
    onNavToMovie: (Int) -> Unit,
    vm: MovieListViewModel = hiltViewModel()
) {
    val hasConnection by vm.hasConnection.collectAsState(initial = false)
    val isUserAuthenticated by vm.isUserAuthenticated.collectAsState(initial = false)
    val moviesList by vm.movieList.collectAsState()
    var isScreenGranted by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        vm.loadUserPrefs()
    }

    LaunchedEffect (hasConnection, isUserAuthenticated) {
        isScreenGranted = hasConnection == true && isUserAuthenticated == true
    }

    if (isScreenGranted) {
        (moviesList as? State.Success)?.let { movieListSuccess ->
            MovieListScreenUi(
                movieListSuccess,
                onNewPage = {
                    vm.getNowPlaying(page = it)
                },
                onNavToMovie = onNavToMovie
            )
        } ?: ScreenUiNonSuccessCommon(moviesList)
    } else {
        MovieListScreenUiNotGranted(hasConnection, isUserAuthenticated)
    }
}

private const val MAX_MOVIES_PER_ROW = 2

/**
 * [onNewPage] a callback that should receive the page number
 * [onNavToMovie] callback that should receive the [Movie.id]
 */
@Composable
fun MovieListScreenUi(moviesList: State.Success<MovieList>, onNewPage: (Int) -> Unit, onNavToMovie: (Int) -> Unit) {
    var isScreenLoaded by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(50) // Just enough to make the animation appear
        isScreenLoaded = true
    }

    ScaleIn(isScreenLoaded) {
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
                            moviePair.first().let {
                                MovieListItem(it, Modifier.clickable {
                                    onNavToMovie(it.id)
                                })
                            }

                            moviePair.getOrNull(1)?.let { secondMovie ->
                                MovieListItem(secondMovie, Modifier.clickable {
                                    onNavToMovie(secondMovie.id)
                                })
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
    MovieListScreenUi(State.Success(movies), {}, {})
}
