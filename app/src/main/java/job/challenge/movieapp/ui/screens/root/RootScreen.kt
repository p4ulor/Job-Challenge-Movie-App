package job.challenge.movieapp.ui.screens.root

import Screens
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController

@Composable
fun RootScreen() = Surface {
    val ctx = LocalContext.current
    val navController = rememberNavController()
    var currentScreen by rememberSaveable { mutableStateOf(Screens.MovieList) }

    Scaffold(
        Modifier.fillMaxSize()
    ) {
        Surface(Modifier.padding(it)) {
            Text(
                text = "Hello!",
            )
        }
    }
}
