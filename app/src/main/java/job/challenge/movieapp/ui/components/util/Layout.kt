package job.challenge.movieapp.ui.components.util

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun CenteredColumn(
    modifier: Modifier = Modifier.fillMaxSize(),
    verticalSpacing: Dp = SmallMediumPadding,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(verticalSpacing, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        content()
    }
}

@Composable
fun CenteredRow(
    modifier: Modifier = Modifier.fillMaxWidth(),
    horizontalSpacing: Dp = SmallMediumPadding,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier,
        horizontalArrangement = Arrangement.spacedBy(horizontalSpacing, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}