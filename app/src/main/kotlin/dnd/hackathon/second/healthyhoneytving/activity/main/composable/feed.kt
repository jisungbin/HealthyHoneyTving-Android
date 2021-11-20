/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [feed.kt] created by Ji Sungbin on 21. 11. 20. 오전 6:14
 *  
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.activity.main.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dnd.hackathon.second.healthyhoneytving.R
import dnd.hackathon.second.healthyhoneytving.activity.main.model.Feed
import dnd.hackathon.second.healthyhoneytving.activity.main.test.TestUtil
import dnd.hackathon.second.healthyhoneytving.theme.colors
import dnd.hackathon.second.healthyhoneytving.util.extension.noRippleClickable
import dnd.hackathon.second.healthyhoneytving.util.extension.toTimeString
import kotlin.random.Random

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyFeed() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(TestUtil.Feeds) { feed ->
            Feed(modifier = Modifier.animateItemPlacement(), feed = feed)
        }
    }
}

@Composable
private fun Feed(modifier: Modifier, feed: Feed) {
    Column(
        modifier = modifier
            .background(color = Color.White)
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    space = 15.dp,
                    alignment = Alignment.CenterHorizontally
                )
            ) {
                Text(text = /*DataStore.getFirstUserFromId(feed.ownerUid).nickname*/ "안녕")
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
        // CoilImage(modifier = Modifier.wrapContentSize(), imageModel = feed.previewImageUrl)
        Box(
            modifier = Modifier
                .size(250.dp)
                .background(color = colors.secondary)
        )
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Row(
                modifier = Modifier.noRippleClickable(onClick = {}),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    space = 5.dp,
                    alignment = Alignment.CenterHorizontally
                )
            ) { // TODO
                Icon(
                    painter = painterResource(R.drawable.ic_round_favorite_border_24),
                    contentDescription = null
                )
                Text(text = Random.nextInt(0, 30).toString(), style = TextStyle(fontSize = 15.sp))
            }
            Row(
                modifier = Modifier
                    .noRippleClickable(onClick = {})
                    .padding(start = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    space = 5.dp,
                    alignment = Alignment.CenterHorizontally
                )
            ) { // TODO
                Icon(
                    painter = painterResource(R.drawable.ic_round_commnet_24),
                    contentDescription = null
                )
                Text(text = Random.nextInt(0, 30).toString(), style = TextStyle(fontSize = 15.sp))
            }
        }
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = /*feed.description*/ TestUtil.generateRandomString(100),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}
