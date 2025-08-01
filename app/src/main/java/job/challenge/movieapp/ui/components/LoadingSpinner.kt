package job.challenge.movieapp.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import job.challenge.movieapp.ui.components.util.CenteredColumn
import job.challenge.movieapp.ui.theme.PreviewComposable

@Composable
fun LoadingSpinner(){
    CenteredColumn {
        CircularProgressIndicator(Modifier.size(100.dp))
    }
}

@Preview
@Composable
fun LoadingSpinnerPreview() = PreviewComposable {
    LoadingSpinner()
}