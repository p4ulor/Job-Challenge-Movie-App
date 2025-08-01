package job.challenge.movieapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons as ComposeMaterialIcons

/** Note: icons from [androidx.compose.material.icons.Icons] are [ImageVector]s */
val MaterialIcons = ComposeMaterialIcons.Default
/** To handle deprecated icons with the introduction of AutoMirrored */
val MaterialIconsExt = ComposeMaterialIcons.AutoMirrored.Default

val IconMediumSize = 36.dp
val PaddingAroundIcon = 10.dp
val IconSmallSize = 25.dp

/** Useful when using [MaterialIcons], which are [ImageVector]s. An alternative to [IconButton] */
@Composable
fun EzIcon(
    icon: ImageVector,
    size: Dp = IconMediumSize,
    padding: Dp = PaddingAroundIcon,
    onClick: () -> Unit
) = Icon(
    imageVector = icon,
    contentDescription = icon.name,
    Modifier
        .padding(padding)
        .size(size)
        .clickable {
            onClick()
        },
    tint = Color.White
)