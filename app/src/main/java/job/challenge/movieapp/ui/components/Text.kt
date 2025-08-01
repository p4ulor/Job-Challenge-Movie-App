package job.challenge.movieapp.ui.components

import androidx.annotation.StringRes
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit

/** Because it's tiring to have to write [stringResource]. Also has utility [onTextOverflow] */
@Composable
fun EzText(
    @StringRes text: Int,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    maxLines: Int = Int.MAX_VALUE,
    textStyle: TextStyle = LocalTextStyle.current,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    onTextOverflow: () -> Unit = {}
) = Text(
    stringResource(text), modifier, color, fontSize, fontStyle, fontWeight, fontFamily,
    textAlign = textAlign,
    maxLines = maxLines,
    style = textStyle,
    overflow = overflow,
    onTextLayout = {
        if (it.didOverflowWidth) {
            onTextOverflow()
        }
    }
)