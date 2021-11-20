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
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dagger.hilt.android.AndroidEntryPoint
import dnd.hackathon.second.healthyhoneytving.activity.feed.composable.HorizontalTopBarCenter
import dnd.hackathon.second.healthyhoneytving.activity.feed.viewmodel.FeedViewModel
import dnd.hackathon.second.healthyhoneytving.activity.main.composable.FeedListItem
import dnd.hackathon.second.healthyhoneytving.activity.main.model.Comment
import dnd.hackathon.second.healthyhoneytving.activity.main.model.Feed
import dnd.hackathon.second.healthyhoneytving.activity.main.test.TestUtil
import dnd.hackathon.second.healthyhoneytving.store.DataStore
import dnd.hackathon.second.healthyhoneytving.theme.MaterialTheme
import dnd.hackathon.second.healthyhoneytving.theme.SystemUiController
import dnd.hackathon.second.healthyhoneytving.theme.colorTextGray

@AndroidEntryPoint
class FeedDetailActivity : ComponentActivity() {

    private val vm: FeedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SystemUiController(window).setSystemBarsColor(Color.White)
        setContent {
            MaterialTheme {
                Content(
                    modifier = Modifier
                        .background(color = Color.White)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    feed = TestUtil.Feeds.first()
                )
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun Content(modifier: Modifier, feed: Feed) {
        LazyColumn(modifier = modifier) {
            stickyHeader {
                HorizontalTopBarCenter(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(color = Color.White)
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    title = feed.title
                )
            }

            item {
                FeedListItem(
                    modifier = Modifier.padding(bottom = 16.dp),
                    feed = feed,
                    showDotMenu = false
                )
            }

            val comments = TestUtil.Comments
            itemsIndexed(comments) { index, comment ->
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .animateItemPlacement(),
                ) {
                    CommentItem(
                        modifier = Modifier,
                        comment = comment
                    )
                    if (index < comments.lastIndex) {
                        Spacer(modifier = Modifier.height(15.dp))
                    }
                }
            }
        }
    }

    @Composable
    private fun CommentItem(modifier: Modifier, comment: Comment) {
        val nickname = DataStore.me.nickname

        Text(
            modifier = modifier,
            color = colorTextGray,
            style = TextStyle(fontSize = 15.sp),
            text = with(AnnotatedString.Builder("$nickname ${comment.content}")) {
                addStyle(
                    style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Black),
                    start = 0,
                    end = nickname.length
                )
                toAnnotatedString()
            }
        )
    }
}
