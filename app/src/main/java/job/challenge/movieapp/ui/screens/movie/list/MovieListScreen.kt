package job.challenge.movieapp.ui.screens.movie.list

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import job.challenge.movieapp.android.viewmodels.MovieListViewModel
import job.challenge.movieapp.ui.components.util.CenteredColumn
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MovieListScreen(
    viewModel: MovieListViewModel = hiltViewModel()
) {

    MovieListScreenUi()
}

@Composable
fun MovieListScreenUi() {
    CenteredColumn {
        Text("MovieListScreen")
    }
}

@Preview
@Composable
fun MovieListScreenUiPreview() {

}