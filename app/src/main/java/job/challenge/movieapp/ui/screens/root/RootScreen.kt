package job.challenge.movieapp.ui.screens.root

import Screen
import androidx.activity.compose.BackHandler
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import job.challenge.movieapp.android.activities.utils.getActivity
import job.challenge.movieapp.ui.screens.movie.MovieDetailsScreen
import job.challenge.movieapp.ui.screens.movie.MovieListScreen

@Composable
fun RootScreen() = Surface {
    val ctx = LocalContext.current
    val navController = rememberNavController()
    var currentScreen by rememberSaveable { mutableStateOf(Screen.MovieList) }

    val navigateTo: (Screen) -> Unit = {
        currentScreen = it
        navController.navigate(currentScreen.name)
    }

    Scaffold(
        Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0.dp), // For enableEdgeToEdge to work
    ) {
        Surface(Modifier.padding(it)) {
            NavHost(
                navController,
                startDestination = Screen.MovieList.name,
                Modifier.padding(it), // Important so that NavHost can make the screens automatically take in consideration the bottom bar
                enterTransition = { EnterTransition.None }, // Improves performance, leave animations to the screens not the NavHost. Only they know when they are ready to display content
                exitTransition = { ExitTransition.None } // Same reason as above
            ) {
                composable(route = Screen.MovieList.name) { MovieListScreen() }
                composable(route = Screen.MovieDetails.name) { MovieDetailsScreen() }
            }

            BackHandler { // Should be placed after NavHost, so it's BackHandler is overridden by this
                with(navController.currentRoute) {
                    if (this == Screen.HOME.name) {
                        ctx.getActivity()?.moveTaskToBack(true) // minimize app, instead of the default of destroying activity
                    } else {
                        navigateTo(Screen.from(navController.previousRoute))
                    }
                }
            }
        }
    }
}
