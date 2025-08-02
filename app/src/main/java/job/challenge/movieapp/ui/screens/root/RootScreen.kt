package job.challenge.movieapp.ui.screens.root

import androidx.activity.compose.BackHandler
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.automirrored.filled.MenuOpen
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import job.challenge.movieapp.android.activities.utils.getActivity
import job.challenge.movieapp.ui.components.EzText
import job.challenge.movieapp.ui.components.MaterialIcons
import job.challenge.movieapp.ui.components.MaterialIconsExt
import job.challenge.movieapp.ui.components.util.CenteredRow
import job.challenge.movieapp.ui.components.util.SystemNavigationBarHeight
import job.challenge.movieapp.ui.screens.movie.details.MovieDetailsScreen
import job.challenge.movieapp.ui.screens.movie.list.MovieListScreen
import job.challenge.movieapp.ui.screens.settings.SettingsScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootScreen() = Surface {
    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    var currentScreen by rememberSaveable { mutableStateOf(Screen.MovieList) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val drawerItems = listOf(Screen.MovieList, Screen.AccountSettings)

    val navigateTo: (Screen) -> Unit = {
        currentScreen = it
        navController.navigate(currentScreen.path)
    }

    Scaffold(
        Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(bottom = SystemNavigationBarHeight), // to only disable enableEdgeToEdge on the bottom bar
        topBar = {
            TopAppBar(
                title = {
                    CenteredRow {
                        EzText(
                            currentScreen.title,
                            Modifier.offset(x = -MaterialIconsExt.MenuOpen.defaultWidth), // offset to center the text
                            textAlign = TextAlign.Center
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                           drawerState.open()
                        }
                    }) {
                        Icon(
                            MaterialIconsExt.MenuOpen,
                            contentDescription = "Open navigation drawer"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) {
        Surface(Modifier.padding(it)) { // Important so that NavHost can make the screens automatically take in consideration the top bar
            ScreenWithDrawer(drawerState, drawerItems, currentScreen,
                onNewScreenSelected = { screen ->
                    navigateTo(screen)
                },
                screen = {
                    NavHost(
                        navController,
                        startDestination = Route.HOME.screen.name,
                        enterTransition = { EnterTransition.None }, // Improves performance, leave animations to the screens not the NavHost. Only they know when they are ready to display content
                        exitTransition = { ExitTransition.None } // Same reason as above
                    ) {
                        composable(Route.MovieList.path) {
                            MovieListScreen(
                                onNavToMovie = { movieId ->
                                    currentScreen = Screen.MovieDetails
                                    navController.navigate("${Screen.MovieDetails.path}/$movieId")
                                }
                            )
                        }
                        composable(Route.MovieDetails.path, Route.MovieDetails.navArgs) {backStackEntry ->
                            val movieId = backStackEntry.arguments?.getInt(NavArgs.Path.selectedMovie)
                            MovieDetailsScreen(movieId)
                        }
                        composable(Route.AccountSettings.path) { SettingsScreen() }
                    }

                    BackHandler { // This is after NavHost so it's BackHandler is overridden by this
                        with(navController.currentRoute) {
                            if (this == Route.HOME.screen.path) {
                                ctx.getActivity()?.moveTaskToBack(true) // minimize app, instead of the default of destroying activity
                            } else {
                                navController.popBackStack()
                                currentScreen = Screen.from(navController.currentRoute) ?: Route.HOME.screen
                            }
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun ScreenWithDrawer(
    drawerState: DrawerState,
    drawerItems: List<Screen>,
    currentScreen: Screen,
    onNewScreenSelected: (Screen) -> Unit,
    screen: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(drawerState) {
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    Spacer(Modifier.height(12.dp))
                    drawerItems.forEach { item ->
                        NavigationDrawerItem(
                            label = { EzText(item.title) },
                            selected = item.name == currentScreen?.name,
                            onClick = {
                                scope.launch { drawerState.close() }
                                onNewScreenSelected(item)
                            },
                            Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                            icon = { Icon(item.icon ?: MaterialIcons.QuestionMark, contentDescription = item.icon?.name) },
                        )
                    }
                }
            }
        },
        Modifier.fillMaxHeight(),
        drawerState = drawerState,
        content = {
            screen()
        }
    )
}
