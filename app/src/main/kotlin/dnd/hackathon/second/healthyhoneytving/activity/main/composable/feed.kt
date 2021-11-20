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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dnd.hackathon.second.healthyhoneytving.R
import dnd.hackathon.second.healthyhoneytving.activity.main.model.Feed
import dnd.hackathon.second.healthyhoneytving.store.DataStore
import dnd.hackathon.second.healthyhoneytving.theme.colors
import dnd.hackathon.second.healthyhoneytving.util.extension.noRippleClickable
import dnd.hackathon.second.healthyhoneytving.util.extension.toTimeString
import kotlin.random.Random

@Composable
fun LazyFeed() {
}

@Composable
private fun Feed(feed: Feed) {
    val ownerNickname = DataStore.getFirstUserFromId(feed.ownerUid).nickname

    @Composable
    fun CommonRow(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = 5.dp,
                alignment = Alignment.CenterHorizontally
            )
        ) {
            content()
        }
    }

    Column(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        CommonRow {
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
        // CoilImage(modifier = Modifier.wrapContentSize(), imageModel = feed.previewImageUrl)
        Box(
            modifier = Modifier
                .size(250.dp)
                .background(color = colors.secondary)
        )
        CommonRow(modifier = Modifier.noRippleClickable(onClick = {})) { // TODO
            Icon(
                painter = painterResource(R.drawable.ic_round_favorite_border_24),
                contentDescription = null
            )
            Text(text = Random.nextInt(0, 30).toString(), style = TextStyle(fontSize = 10.sp))
        }
        CommonRow(
            modifier = Modifier
                .noRippleClickable(onClick = {})
                .padding(start = 10.dp)
        ) { // TODO
            Icon(
                painter = painterResource(R.drawable.ic_round_commnet_24),
                contentDescription = null
            )
            Text(text = Random.nextInt(0, 30).toString(), style = TextStyle(fontSize = 10.sp))
        }
        Text(
            text = with(AnnotatedString.Builder("$ownerNickname ${feed.description}")) {
                addStyle(
                    SpanStyle(fontWeight = FontWeight.Bold, color = Color.Black),
                    0,
                    ownerNickname.length
                )
                toAnnotatedString()
            },
            color = Color.Gray,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}
