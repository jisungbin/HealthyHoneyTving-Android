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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import dnd.hackathon.second.healthyhoneytving.activity.feed.composable.HorizontalTopBarCenter
import dnd.hackathon.second.healthyhoneytving.activity.feed.viewmodel.FeedViewModel
import dnd.hackathon.second.healthyhoneytving.activity.main.model.Feed
import dnd.hackathon.second.healthyhoneytving.activity.main.test.TestUtil
import dnd.hackathon.second.healthyhoneytving.theme.MaterialTheme
import dnd.hackathon.second.healthyhoneytving.theme.SystemUiController

@AndroidEntryPoint
class FeedDetailActivity : ComponentActivity() {

    private val vm: FeedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SystemUiController(window).setSystemBarsColor(Color.White)
        setContent {
            MaterialTheme {
                Content(TestUtil.Feeds.first())
            }
        }
    }

    @Composable
    private fun Content(feed: Feed) {
        Column(modifier = Modifier.fillMaxSize()) {
            HorizontalTopBarCenter(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(color = Color.White)
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                title = feed.title
            )
            FeedItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                feed = feed
            )
        }
    }
}
