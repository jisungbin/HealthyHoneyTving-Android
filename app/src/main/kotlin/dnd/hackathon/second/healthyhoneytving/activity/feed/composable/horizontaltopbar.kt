/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [horizontaltopbar.kt] created by Ji Sungbin on 21. 11. 20. 오전 12:35
 *  
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.activity.feed.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dnd.hackathon.second.healthyhoneytving.R
import dnd.hackathon.second.healthyhoneytving.util.extension.getActivity
import dnd.hackathon.second.healthyhoneytving.util.extension.noRippleClickable

@Composable
fun HorizontalTopBar(modifier: Modifier, title: String) {
    val activity = getActivity()

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
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

@Composable
fun HorizontalTopBarCenter(modifier: Modifier, title: String) {
    val activity = getActivity()

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .clickable {
                    activity.finish()
                }
                .size(20.dp),
            painter = painterResource(R.drawable.ic_round_arrow_back_24),
            contentDescription = null
        )
        Text(
            text = title,
            color = Color.Black,
            style = TextStyle(fontSize = 15.sp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Icon(
            modifier = Modifier.noRippleClickable(onClick = {}), // TODO
            painter = painterResource(R.drawable.ic_round_menu_dot_24),
            contentDescription = null
        )
    }
}
