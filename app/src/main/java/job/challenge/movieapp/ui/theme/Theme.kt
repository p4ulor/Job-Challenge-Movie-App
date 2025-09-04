package job.challenge.movieapp.ui.theme

import android.os.Build
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import job.challenge.movieapp.ui.animations.smooth
import job.challenge.movieapp.ui.components.MaterialIcons
import job.challenge.movieapp.ui.components.util.CenteredColumn
import job.challenge.movieapp.ui.components.util.CenteredRow
import job.challenge.movieapp.ui.components.util.RoundRectangleShape
import kotlinx.coroutines.delay


private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFF1F7FF),
    onPrimary = Color(0xFF000000),
    primaryContainer = Color(0xFF290E90),
    onPrimaryContainer = Color(0xFFFFFFFF),

    secondary = Color(0xFF6797C6),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFF1070FF),
    onSecondaryContainer = Color(0xFFFFFFFF),

    tertiaryContainer = Color(0xFFFDBE4D),
    onTertiaryContainer = Color(0xFF000000),

    surface = Color(0xff030929),
    onSurface = Color(0xFFFFFFFF),

    error = Color(0xFFB00020),
    onError = Color(0xFFFFA8A8),
    errorContainer = Color(0xFFB00020),
    onErrorContainer = Color(0xFF2E000B),

    outline = Color(0xFFDFF1FF),
    outlineVariant = Color(0xFFDFDFDF), // For HorizontalDivider

    scrim = Color(0xFF737373),
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF0A175E),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFF290E90),
    onPrimaryContainer = Color(0xFFFFFFFF),

    secondary = Color(0xFF6797C6),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFF1070FF),
    onSecondaryContainer = Color(0xFFFFFFFF),

    tertiaryContainer = Color(0xFFFDBE4D),
    onTertiaryContainer = Color(0xFF000000),

    surface = Color(0xFFEAEAEA),
    onSurface = Color(0xFF000000),

    error = Color(0xFFB00020),
    onError = Color(0xFFFFA8A8),
    errorContainer = Color(0xFFB00020),
    onErrorContainer = Color(0xFFFFFFFF),

    outline = Color(0xFF36424B),
    outlineVariant = Color(0xFF242424), // For HorizontalDivider

    scrim = Color(0xFF737373),
)

@Composable
private fun SampleComposable(){
    Box {
        Column(
            Modifier
                .padding(10.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text("HorizontalDivider", style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
            HorizontalDivider()
            HorizontalDivider(thickness = 10.dp)

            var isVisible by remember { mutableStateOf(false) }

            OutlinedTextField(
                value = "Input",
                onValueChange = {},
                label = { Text("Label") },
                trailingIcon = { // Add the toggle button
                    val image = if (isVisible) MaterialIcons.VisibilityOff else MaterialIcons.Visibility
                    IconButton(onClick = { isVisible = !isVisible }) {
                        Icon(imageVector = image, "description")
                    }
                }
            )

            Button(onClick = {}) {
                Text("Button")
            }
            OutlinedButton(onClick = {}) {
                Text("Outlined Button")
            }
            TextButton(onClick = {}) {
                Text("Text Button")
            }

            CenteredRow {
                var isChecked by remember { mutableStateOf(true) }
                RadioButton(selected = isChecked, onClick = { isChecked = !isChecked })
                Text("Radio Button")
            }

            CenteredRow {
                var isChecked by remember { mutableStateOf(true) }
                Checkbox(checked = isChecked, onCheckedChange = { isChecked = !isChecked })
                Text("Checkbox")
                Checkbox(checked = !isChecked, onCheckedChange = { isChecked = !isChecked })
            }

            var isChecked by remember { mutableStateOf(true) }
            CenteredRow {
                Switch(checked = isChecked, onCheckedChange = { isChecked = !isChecked })
                Switch(checked = false, onCheckedChange = { isChecked = !isChecked })
            }

            var slider by remember { mutableFloatStateOf(0.5f) }
            Slider(value = slider, onValueChange = { slider = it })

            val animatedProgress = rememberInfiniteTransition("").animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = smooth(3000),
                    repeatMode = RepeatMode.Reverse
                )
            )
            LinearProgressIndicator(
                progress = { animatedProgress.value },
            )

            var counter by remember { mutableIntStateOf(1) }
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
                Text(counter.toString())
                LaunchedEffect(Unit) {
                    while(true) {
                        if(counter==3) counter = 0
                        else counter++
                        delay(1000)
                    }
                }
            }

            CenteredRow {
                CardContainer(MaterialTheme.colorScheme.primaryContainer, counter.toString())
                CardContainer(MaterialTheme.colorScheme.secondaryContainer, counter.toString())
                CardContainer(MaterialTheme.colorScheme.tertiaryContainer, counter.toString())
                CardContainer(MaterialTheme.colorScheme.errorContainer, counter.toString())

                Box {
                    Box(
                        Modifier.size(33.dp, 62.dp)
                            .blur(6.dp, BlurredEdgeTreatment.Unbounded)
                        //.border(2.dp, Color.Black, RoundRectangleShape)
                    ) {
                        Box(
                            Modifier.size(33.dp, 63.dp)
                                .clip(RoundRectangleShape)
                                .background(Color(0xE1000000))
                        ) {

                        }
                    }

                    Box(
                        Modifier.size(30.dp, 60.dp)
                            .clip(RoundRectangleShape)
                            .background(Color.White)
                        //.border(1.dp, Color.Black, RoundRectangleShape)
                    ) { CircularProgressIndicator(Modifier.padding(10.dp)) }
                }
            }

            CenteredRow {
                Card(elevation = CardDefaults.outlinedCardElevation(defaultElevation = 5.dp)) { // ridiculous weak shadow...
                    Column(Modifier.padding(8.dp)) {
                        Text("Card Title", style = MaterialTheme.typography.titleMedium)
                        Text("Elevation", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
                Card(
                    Modifier.shadow( // ridiculous weak shadow...
                        15.dp,
                        RoundRectangleShape,
                        ambientColor = Color.Black,
                        spotColor = Color.Black
                    ).zIndex(1f)) {
                    Column(Modifier.padding(8.dp)) {
                        Text("Card", style = MaterialTheme.typography.titleMedium)
                        Text("Shadow", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }

            Icon(
                Icons.Filled.Visibility,
                "Visibility",
                Modifier.size(50.dp),
                //MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
private fun CardContainer(containerColor: Color, text: String){
    Card(
        Modifier.widthIn(0.dp, 30.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        shape = RoundRectangleShape
    ) {
        CenteredColumn(Modifier) {
            CircularProgressIndicator(Modifier.padding(10.dp))
            Text(text)
        }
    }
}

@Preview
@Composable
private fun DarkColorSchemePreview() = PreviewComposable(enableDarkTheme = true){
    SampleComposable()
}

@Preview
@Composable
private fun LightColorSchemePreview() = PreviewComposable(enableDarkTheme = false) {
    SampleComposable()
}

/** Util to reduce repetitive code. Previews should be made on top of a Surface */
@Composable
fun PreviewComposable(
    enableDarkTheme: Boolean = true,
    content: @Composable () -> Unit
) = AppTheme(enableDarkTheme) {
    Surface(Modifier.fillMaxSize().wrapContentSize()) { content() }
}

/** https://developer.android.com/develop/ui/compose/designsystems/material3 */
@Composable
fun AppTheme(
    enableDarkTheme: Boolean = isSystemInDarkTheme(),
    useSystemDefault: Boolean = false, //only available on Android 12+
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        useSystemDefault && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (enableDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        enableDarkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}