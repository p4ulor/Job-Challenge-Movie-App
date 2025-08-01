import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Subscriptions
import androidx.compose.ui.graphics.vector.ImageVector
import job.challenge.movieapp.ui.components.MaterialIcons

enum class Screen (val icon: ImageVector? = null) {
    MovieList(MaterialIcons.Subscriptions),
    MovieDetails,
    AccountSettings(MaterialIcons.AccountCircle);

    companion object {
        val HOME = MovieList
        fun from(string: String?) = Screen.entries.first { it.name == string }
    }
}