package job.challenge.movieapp.ui.screens.movie.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.JoinFull
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Outbox
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import job.challenge.movieapp.android.viewmodels.MovieListViewModel
import job.challenge.movieapp.ui.components.util.CenteredColumn
import androidx.hilt.navigation.compose.hiltViewModel
import job.challenge.movieapp.R
import job.challenge.movieapp.data.domain.MovieList
import job.challenge.movieapp.ui.components.EzText
import job.challenge.movieapp.ui.components.LoadingSpinner
import job.challenge.movieapp.ui.components.MaterialIcons
import job.challenge.movieapp.ui.theme.PreviewComposable
import kotlinx.coroutines.launch

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
    }

    if (isScreenGranted) {
        MovieListScreenUi(moviesList)
    } else {
        MovieListScreenUiNotGranted(hasConnection, isUserAuthenticated)
    }
}

@Composable
fun MovieListScreenUi(moviesList: MovieList?) {
    CenteredColumn {
        if (moviesList == null) {
            LoadingSpinner()
        } else {
            Text("$moviesList")
        }
    }
}

@Preview
@Composable
fun MovieListScreenUiLoadingPreview() = PreviewComposable {
    val movies = null
    MovieListScreenUi(movies)
}

@Preview
@Composable
fun MovieListScreenUiPreview() = PreviewComposable {
    val movies = MovieList("many")
    MovieListScreenUi(movies)
}