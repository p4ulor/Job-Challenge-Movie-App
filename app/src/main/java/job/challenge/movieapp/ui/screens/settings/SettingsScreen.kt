package job.challenge.movieapp.ui.screens.settings

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import job.challenge.movieapp.R
import job.challenge.movieapp.android.viewmodels.SettingsViewModel
import job.challenge.movieapp.data.local.preferences.UserPreferences
import job.challenge.movieapp.i
import job.challenge.movieapp.ui.animations.ScaleIn
import job.challenge.movieapp.ui.components.EzIcon
import job.challenge.movieapp.ui.components.EzText
import job.challenge.movieapp.ui.components.IconSmallSize
import job.challenge.movieapp.ui.components.LoadingSpinner
import job.challenge.movieapp.ui.components.MaterialIcons
import job.challenge.movieapp.ui.components.util.CenteredColumn
import job.challenge.movieapp.ui.components.util.MediumPadding
import job.challenge.movieapp.ui.components.util.toast
import job.challenge.movieapp.ui.theme.PreviewComposable

@Composable
fun SettingsScreen(
    vm: SettingsViewModel = hiltViewModel()
) {
    val prefs by vm.prefs.collectAsState()
    var isScreenLoaded by rememberSaveable { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        vm.loadUserPrefs()
        isScreenLoaded = true
    }

    ScaleIn(isScreenLoaded) {
        if (prefs == null) {
            LoadingSpinner()
        } else {
            SettingsScreenUi(
                storedToken = prefs?.tmdbToken,
                onSetBearerToken = {
                    vm.saveUserPrefs(UserPreferences(it))
                }
            )
        }
    }
}

@Composable
fun SettingsScreenUi(storedToken: String?, onSetBearerToken: (newToken: String) -> Unit){
    CenteredColumn {
        val ctx = LocalContext.current
        var token by remember { mutableStateOf(storedToken) }
        var isVisible by remember { mutableStateOf(false) }

        EzText(
            R.string.setup_token,
            textStyle = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.size(MediumPadding))

        OutlinedTextField(
            value = token ?: "",
            onValueChange = { token = it },
            Modifier
                .fillMaxWidth()
                .padding(MediumPadding),
            label = { EzText(R.string.bearer_token, textAlign = TextAlign.Center) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done // Input Method Editor Action
            ),
            singleLine = true,
            visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                Row {
                    EzIcon(
                        if (isVisible) MaterialIcons.VisibilityOff else MaterialIcons.Visibility,
                        IconSmallSize
                    ) {
                        isVisible = !isVisible
                    }
                }
            }
        )

        Spacer(Modifier.size(MediumPadding))

        Button(
            onClick = {
                token?.let {
                    if (it.isNotEmpty()) {
                        i("Saving bearerToken Key")
                        onSetBearerToken(it)
                        ctx.toast(R.string.saved_bearer_token)
                    }
                }
            }
        ) {
            EzText(R.string.save, textAlign = TextAlign.Center)
        }
    }
}

@Preview
@Composable
fun SettingsScreenUiPreview() = PreviewComposable {
    SettingsScreenUi("some token", {})
}
