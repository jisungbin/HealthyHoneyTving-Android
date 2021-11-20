/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [feed.kt] created by Ji Sungbin on 21. 11. 20. 오전 6:14
 *  
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.activity.main.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.coil.CoilImage
import dnd.hackathon.second.healthyhoneytving.R
import dnd.hackathon.second.healthyhoneytving.activity.main.model.Feed
import dnd.hackathon.second.healthyhoneytving.store.DataStore
import dnd.hackathon.second.healthyhoneytving.util.extension.noRippleClickable
import dnd.hackathon.second.healthyhoneytving.util.extension.toTimeString

@Composable
fun LazyFeed() {
}

@Composable
private fun Feed(feed: Feed) {
    Column(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    space = 5.dp,
                    alignment = Alignment.CenterHorizontally
                )
            ) {
                Text(text = DataStore.getFirstUserFromId(feed.ownerUid).nickname)
                Text(
                    text = feed.createdAt.toTimeString(),
                    color = Color.LightGray,
                    style = TextStyle(fontSize = 10.sp)
                )
            }
            Icon(
                modifier = Modifier.noRippleClickable(onClick = {}), // TODO
                painter = painterResource(R.drawable.ic_round_menu_dot_24),
                contentDescription = null
            )
        }
        CoilImage(modifier = Modifier.wrapContentSize(), imageModel = feed.previewImageUrl)
    }
}
