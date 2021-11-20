/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [FeedDetailActivity.kt] created by Ji Sungbin on 21. 11. 20. 오전 11:11
 *  
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.activity.feed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.coil.CoilImage
import dagger.hilt.android.AndroidEntryPoint
import dnd.hackathon.second.healthyhoneytving.R
import dnd.hackathon.second.healthyhoneytving.activity.feed.viewmodel.FeedViewModel
import dnd.hackathon.second.healthyhoneytving.activity.main.model.Feed
import dnd.hackathon.second.healthyhoneytving.theme.MaterialTheme
import dnd.hackathon.second.healthyhoneytving.util.extension.noRippleClickable
import dnd.hackathon.second.healthyhoneytving.util.extension.toTimeString
import kotlin.random.Random

@AndroidEntryPoint
class FeedDetailActivity : ComponentActivity() {

    private val vm: FeedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Content()
            }
        }
    }

    @Composable
    private fun Content() {
    }

    @Composable
    private fun FeedListItem(modifier: Modifier, feed: Feed) {
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
                ) { // TODO
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
            CoilImage(
                modifier = Modifier.requiredHeightIn(max = 500.dp),
                imageModel = feed.previewImageUrl,
                contentScale = ContentScale.FillBounds
            )
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space = 20.dp, alignment = Alignment.Start)
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
                    modifier = Modifier.noRippleClickable(onClick = {}),
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
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                text = feed.description,
                textAlign = TextAlign.Start
            )
        }
    }
}
