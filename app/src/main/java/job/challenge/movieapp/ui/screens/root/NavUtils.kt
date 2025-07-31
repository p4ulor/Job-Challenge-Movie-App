package job.challenge.movieapp.ui.screens.root

import androidx.navigation.NavHostController

val NavHostController.currentRoute
    get() = currentBackStackEntry?.destination?.route

val NavHostController.previousRoute
    get() = previousBackStackEntry?.destination?.route