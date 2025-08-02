package job.challenge.movieapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun getRandomContainerColor() = listOf(
    MaterialTheme.colorScheme.primaryContainer,
    MaterialTheme.colorScheme.secondaryContainer,
    MaterialTheme.colorScheme.tertiaryContainer,
    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f),
    MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.7f),
    MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.7f),
    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
    MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f),
    MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.3f),
    MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)
).random()