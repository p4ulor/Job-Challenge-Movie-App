package job.challenge.movieapp.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import job.challenge.movieapp.R
import job.challenge.movieapp.android.viewmodels.utils.State
import job.challenge.movieapp.ui.components.util.CenteredColumn
import job.challenge.movieapp.ui.components.util.LargePadding
import job.challenge.movieapp.ui.theme.PreviewComposable

/**
 * Helps reduce repetitive code in composables when the state of the data is Loading or Error. And
 * allows for easier scalability
 */
@Composable
fun <T : Any> ScreenUiNonSuccessCommon(value: State<T>){
    when (value) {
        is State.Loading -> {
            LoadingSpinner()
        }

        is State.Error -> {
            CenteredColumn(Modifier.fillMaxSize().padding(LargePadding)) {
                Text(
                    value.message,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            }
        }

        is State.Success -> {
            CenteredColumn(Modifier.fillMaxSize().padding(LargePadding)) {
                EzText(
                    R.string.this_screen_should_not_be_called_on_success,
                    textStyle = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}

@Preview
@Composable
fun MovieListScreenUiLoadingPreview() = PreviewComposable {
    ScreenUiNonSuccessCommon(State.Loading)
}

@Preview
@Composable
fun MovieListScreenUiErrorPreview() = PreviewComposable {
    ScreenUiNonSuccessCommon(State.Error("HTTP Error: Invalid API key: You must be granted a valid key."))
}