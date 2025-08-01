package job.challenge.movieapp.ui.screens.root

import Screen
import androidx.activity.compose.BackHandler
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.automirrored.filled.MenuOpen
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import job.challenge.movieapp.R
import job.challenge.movieapp.android.activities.utils.getActivity
import job.challenge.movieapp.ui.components.EzText
import job.challenge.movieapp.ui.components.MaterialIcons
import job.challenge.movieapp.ui.components.MaterialIconsExt
import job.challenge.movieapp.ui.components.util.SystemNavigationBarHeight
import job.challenge.movieapp.ui.screens.movie.details.MovieDetailsScreen
import job.challenge.movieapp.ui.screens.movie.list.MovieListScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootScreen() = Surface {
    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    var currentScreen by rememberSaveable { mutableStateOf(Screen.MovieList) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    var selectedItem by rememberSaveable { mutableStateOf<ImageVector?>(null) }
    val drawerItems = listOf(
        MaterialIcons.AccountCircle,
        //MaterialIcons.Favorite
    )

    val navigateTo: (Screen) -> Unit = {
        currentScreen = it
        navController.navigate(currentScreen.name)
    }

    Scaffold(
        Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(bottom = SystemNavigationBarHeight), // Only disable enableEdgeToEdge to the bottom bar
        topBar = {
            TopAppBar(
                title = { EzText(R.string.app_name) },
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
        Surface(Modifier.padding(it)) {
            NavHost(
                navController,
                startDestination = Screen.MovieList.name,
                Modifier, // Important so that NavHost can make the screens automatically take in consideration the bottom bar
                enterTransition = { EnterTransition.None }, // Improves performance, leave animations to the screens not the NavHost. Only they know when they are ready to display content
                exitTransition = { ExitTransition.None } // Same reason as above
            ) {
                composable(route = Screen.MovieList.name) {
                    ScreenWithDrawer(drawerState, drawerItems, selectedItem,
                    onNewItemSelected = {
                        selectedItem = it
                    }) {
                        MovieListScreen()
                    }
                }
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

@Composable
fun ScreenWithDrawer(
    drawerState: DrawerState,
    drawerItems: List<ImageVector>,
    selectedItem: ImageVector?,
    onNewItemSelected: (ImageVector) -> Unit,
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
                            label = { Text(item.name.substringAfterLast(".")) },
                            selected = item.name == selectedItem?.name,
                            onClick = {
                                scope.launch { drawerState.close() }
                                onNewItemSelected(item)
                            },
                            Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                            icon = { Icon(item, contentDescription = null) },
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
