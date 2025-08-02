package job.challenge.movieapp.ui.screens

import androidx.annotation.StringRes
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Subscriptions
import androidx.compose.ui.graphics.vector.ImageVector
import job.challenge.movieapp.R
import job.challenge.movieapp.ui.components.MaterialIcons

enum class Screen (@StringRes val title: Int, val icon: ImageVector? = null) {
    MovieList(R.string.movie_list, MaterialIcons.Subscriptions),
    MovieDetails(R.string.movie_details),
    AccountSettings(R.string.account_settings, MaterialIcons.AccountCircle);

    companion object {
        val HOME = MovieList
        fun from(string: String?) = Screen.entries.first { it.name == string }
    }
}