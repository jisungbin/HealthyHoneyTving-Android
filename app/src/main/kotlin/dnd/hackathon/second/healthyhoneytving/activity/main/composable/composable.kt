/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [composable.kt] created by Ji Sungbin on 21. 11. 20. 오전 6:13
 *  
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.activity.main.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dnd.hackathon.second.healthyhoneytving.theme.colorBackgroundGray

@Composable
fun Categorie() {
}

@Composable
fun Menu() {
}

@Composable
fun Divider(thickness: Dp = 0.5.dp) {
    androidx.compose.material.Divider(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        color = colorBackgroundGray,
        thickness = thickness
    )
}
