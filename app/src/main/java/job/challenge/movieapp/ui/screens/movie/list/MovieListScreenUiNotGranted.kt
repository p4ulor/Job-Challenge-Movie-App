package job.challenge.movieapp.ui.screens.movie.list

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import job.challenge.movieapp.R
import job.challenge.movieapp.ui.components.EzText
import job.challenge.movieapp.ui.components.util.CenteredColumn
import job.challenge.movieapp.ui.theme.PreviewComposable


@Composable
fun MovieListScreenUiNotGranted(hasConnection: Boolean, isUserAuthenticated: Boolean) {
    CenteredColumn {
        if (!hasConnection) {
            EzText(
                R.string.no_internet_connection,
                textStyle = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )
        } else if (!isUserAuthenticated) {
            EzText(
                R.string.user_not_authenticated,
                textStyle = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )
            EzText(
                R.string.please_setup_the_bearer_token,
                textStyle = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun MovieListScreenUiNotGrantedConnectionPreview() = PreviewComposable {
    MovieListScreenUiNotGranted(hasConnection = false, isUserAuthenticated = true)
}

@Preview
@Composable
fun MovieListScreenUiNotGrantedAuthPreview() = PreviewComposable {
    MovieListScreenUiNotGranted(hasConnection = true, isUserAuthenticated = false)
}