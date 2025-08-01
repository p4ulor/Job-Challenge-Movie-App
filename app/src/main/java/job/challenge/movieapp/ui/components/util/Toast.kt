package job.challenge.movieapp.ui.components.util

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.StringRes

/** Creates a Toast that uses the Main/UI thread (as required when using Toast) */
fun Context.toast(@StringRes text: Int, param: String = "") {
    Handler(Looper.getMainLooper()).post {
        Toast.makeText(this, this.getString(text, param), Toast.LENGTH_SHORT).show()
    }
}