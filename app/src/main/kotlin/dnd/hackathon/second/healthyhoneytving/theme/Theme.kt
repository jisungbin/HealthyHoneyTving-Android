/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [Theme.kt] created by Ji Sungbin on 21. 11. 19. 오후 10:50
 *
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.theme

import androidx.compose.material.Typography
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import dnd.hackathon.second.healthyhoneytving.R

val defaultFontFamily = FontFamily(Font(R.font.notosanskr))

val colorBackgroundGray = Color(0xFFF7F8F9)
val colorTextGray = Color(0xFFC5C8CE)

val colorError = Color(0xFFF86D64)

val colors = lightColors().copy(
    primary = Color(0xFF6C75F1),
    primaryVariant = Color(0xFF485BEA),
    secondary = Color(0xFF9B9EF2)
)

@PublishedApi
@JvmSynthetic
internal val typography = Typography(defaultFontFamily = defaultFontFamily)

@Composable
inline fun MaterialTheme(crossinline content: @Composable () -> Unit) {
    androidx.compose.material.MaterialTheme(
        colors = colors,
        typography = typography
    ) {
        content()
    }
}
