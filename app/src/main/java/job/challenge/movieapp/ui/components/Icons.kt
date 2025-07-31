package job.challenge.movieapp.ui.components

import androidx.compose.ui.graphics.vector.ImageVector
import job.challenge.movieapp.R
import androidx.compose.material.icons.Icons as ComposeMaterialIcons

sealed class Icon private constructor() {
    data class App(val resourcesIcon: ResourcesIcon) : Icon()
    data class Material(val materialIcon: ImageVector) : Icon()

    val asAppIcon get() = this as? App
    val asMaterialIcon get() = this as? Material
}

/**
 * Drawable resources that are either .xml (can be created from an .svg, a vector image) or .png
 * To convert .svg to .xml, you can use [Android's Asset Studio](https://stackoverflow.com/a/56276118/28417805)
 * Or use https://svg2vector.com/ to convert .svg from https://lucide.dev/icons/
 * to android .xml. All with stroke width 1.5px, size 300px (scaled up by opening the .svg in Gimp
 * and saving it as .png)
 * @param [resourceId] a .png or .xml
 */
enum class ResourcesIcon(val resourceId: Int, val useOriginalColors: Boolean = false) {
    Settings(R.drawable.settings_vector),
}

/** Note: icons from [androidx.compose.material.icons.Icons] are [ImageVector]s */
val MaterialIcons = ComposeMaterialIcons.Default
/** To handle deprecated icons with the introduction of AutoMirrored */
val MaterialIconsExt = ComposeMaterialIcons.AutoMirrored.Default