/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [common.kt] created by Ji Sungbin on 21. 11. 20. 오전 8:12
 *  
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.activity.main.composable

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun <T> calcColorAnimationState(
    input: T,
    target: T,
    trueColor: Color = Color.Black,
    falseColor: Color = Color.LightGray
) = animateColorAsState(if (input == target) trueColor else falseColor)

@Suppress("RedundantExplicitType")
@Composable
fun HorizontalDivider(width: Dp = Dp.Unspecified, thickness: Dp = 2.dp) {
    var modifier: Modifier = Modifier
    modifier = if (width != Dp.Unspecified) {
        modifier.width(width)
    } else {
        modifier.fillMaxWidth()
    }
    Box(
        modifier = modifier
            .height(thickness)
            .background(color = Color.LightGray),
    )
}

@Suppress("RedundantExplicitType")
@Composable
fun VerticalDivider(height: Dp = Dp.Unspecified, thickness: Dp = 2.dp) {
    var modifier: Modifier = Modifier
    modifier = if (height != Dp.Unspecified) {
        modifier.height(height)
    } else {
        modifier.fillMaxHeight()
    }
    Box(
        modifier = modifier
            .width(thickness)
            .background(color = Color.LightGray),
    )
}
