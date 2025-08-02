package job.challenge.movieapp.ui.screens.root

import androidx.annotation.StringRes
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Subscriptions
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import job.challenge.movieapp.R
import job.challenge.movieapp.ui.components.MaterialIcons
import java.util.Locale

sealed class Route private constructor(
    val path: String,
    val screen: Screen,
    val navArgs: List<NamedNavArgument>
) {

    constructor(screen: Screen, navArgs: List<NamedNavArgument> = emptyList()) :
        this(path = screen.path, screen, navArgs)

    data object MovieList : Route(Screen.MovieList)
    data object MovieDetails : Route(
        path = "${Screen.MovieDetails.path}/{${NavArgs.Path.selectedMovie}}",
        screen = Screen.MovieDetails,
        navArgs =
        listOf(
            navArgument(NavArgs.Path.selectedMovie) {
                type = NavType.IntType
                defaultValue = -1
            }
        )
    )
    data object AccountSettings : Route(Screen.AccountSettings)

    companion object {
        val HOME = Route.MovieList
    }
}

data object NavArgs {
    data object Path {
        const val selectedMovie = "selectedMovie"
    }
}

enum class Screen (@StringRes val title: Int, val icon: ImageVector? = null) {
    MovieList(R.string.movie_list, MaterialIcons.Subscriptions),
    MovieDetails(R.string.movie_details),
    AccountSettings(R.string.account_settings, MaterialIcons.AccountCircle);

    val path = this.name.lowercase(Locale.getDefault())

    companion object {
        fun from(string: String?) = Screen.entries.first { it.name == string }
    }
}