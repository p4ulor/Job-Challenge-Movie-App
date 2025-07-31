package job.challenge.movieapp.android.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import job.challenge.movieapp.i
import job.challenge.movieapp.ui.screens.root.RootScreen
import job.challenge.movieapp.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        i("onCreate")

        setContent {
            enableEdgeToEdge()
            AppTheme {
                RootScreen()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        i("Stopped")
    }

    override fun onDestroy() {
        super.onDestroy()
        i("Destroyed")
    }
}