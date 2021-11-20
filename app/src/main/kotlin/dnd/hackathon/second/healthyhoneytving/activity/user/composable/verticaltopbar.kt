/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [horizontaltopbar.kt] created by Ji Sungbin on 21. 11. 20. 오전 12:35
 *  
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.activity.user.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dnd.hackathon.second.healthyhoneytving.R
import dnd.hackathon.second.healthyhoneytving.util.extension.getActivity

@Composable
fun VerticalTopBar(modifier: Modifier, title: String) {
    val activity = getActivity()

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(20.dp)) {
        Icon(
            modifier = Modifier.clickable {
                activity.finish()
            },
            painter = painterResource(R.drawable.ic_round_arrow_back_24),
            contentDescription = null
        )
        Text(
            text = title,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            style = TextStyle(fontSize = 25.sp)
        )
    }
}
