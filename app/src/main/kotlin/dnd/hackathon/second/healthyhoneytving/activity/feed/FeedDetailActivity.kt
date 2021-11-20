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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dagger.hilt.android.AndroidEntryPoint
import dnd.hackathon.second.healthyhoneytving.R
import dnd.hackathon.second.healthyhoneytving.activity.feed.composable.HorizontalTopBarCenter
import dnd.hackathon.second.healthyhoneytving.activity.feed.mvi.MviFeedUploadState
import dnd.hackathon.second.healthyhoneytving.activity.feed.viewmodel.FeedViewModel
import dnd.hackathon.second.healthyhoneytving.activity.main.composable.FeedListItem
import dnd.hackathon.second.healthyhoneytving.activity.main.model.Comment
import dnd.hackathon.second.healthyhoneytving.activity.main.model.Feed
import dnd.hackathon.second.healthyhoneytving.mvi.BaseMviSideEffect
import dnd.hackathon.second.healthyhoneytving.store.DataStore
import dnd.hackathon.second.healthyhoneytving.theme.MaterialTheme
import dnd.hackathon.second.healthyhoneytving.theme.SystemUiController
import dnd.hackathon.second.healthyhoneytving.theme.colorTextGray
import dnd.hackathon.second.healthyhoneytving.theme.colors
import dnd.hackathon.second.healthyhoneytving.util.constant.IntentConstant
import dnd.hackathon.second.healthyhoneytving.util.extension.errorToast
import dnd.hackathon.second.healthyhoneytving.util.extension.noRippleClickable
import dnd.hackathon.second.healthyhoneytving.util.extension.toast
import org.orbitmvi.orbit.viewmodel.observe
import kotlin.random.Random

@AndroidEntryPoint
class FeedDetailActivity : ComponentActivity() {

    private val vm: FeedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.observe(lifecycleOwner = this, state = ::handleState, sideEffect = ::handleSideEffect)
        SystemUiController(window).setSystemBarsColor(Color.White)
        setContent {
            MaterialTheme {
                Content(
                    modifier = Modifier
                        .background(color = Color.White)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    feed = DataStore.getFirstFeedFromUid(
                        intent.getIntExtra(IntentConstant.FeedId, 0)
                    )
                )
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun Content(modifier: Modifier, feed: Feed) {
        val comments by DataStore.comments.collectAsState()
        val commentsFromFeedUid = comments.filter { comment -> comment.feedUid == feed.feedUid }
            .filter { comment -> comment.content.isNotBlank() }

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
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .animateItemPlacement(),
                    feed = feed,
                    showDotMenu = false
                )
            }

            items(commentsFromFeedUid) { comment ->
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
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    val shape = RoundedCornerShape(5.dp)

                    CommentField(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(color = Color.White, shape = shape)
                            .animateItemPlacement(),
                        shape = shape,
                        feed = feed
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    FeedVote(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .animateItemPlacement()
                    )
                    Spacer(modifier = Modifier.height(50.dp))
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

    @Composable
    private fun CommentField(modifier: Modifier, shape: Shape, feed: Feed) {
        var field by remember { mutableStateOf(TextFieldValue()) }

        OutlinedTextField(
            modifier = modifier,
            value = field,
            maxLines = 4,
            textStyle = TextStyle(fontSize = 15.sp),
            onValueChange = { newField -> field = newField },
            shape = shape,
            placeholder = {
                Text(
                    text = stringResource(R.string.activity_feed_detail_write_comment),
                    style = TextStyle(fontSize = 15.sp),
                    color = colorTextGray
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                disabledBorderColor = colorTextGray,
                focusedBorderColor = colors.primary,
                unfocusedBorderColor = colorTextGray,
                backgroundColor = Color.White,
                textColor = Color.Black
            ),
            trailingIcon = {
                Text(
                    modifier = Modifier.noRippleClickable(onClick = {
                        val comment = Comment(feedUid = feed.feedUid, content = field.text)
                        vm.uploadComment(feed = feed, comment = comment)
                    }),
                    text = stringResource(R.string.activity_feed_detail_commented),
                    color = colors.primary,
                    style = TextStyle(fontSize = 15.sp),
                )
            }
        )
    }

    @Composable
    private fun FeedVote(modifier: Modifier) {
        var isGood by remember { mutableStateOf(Random.nextBoolean()) }

        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.activity_feed_detail_vote),
                color = Color.Black,
                style = TextStyle(fontSize = 18.sp)
            )
            Row(
                modifier = Modifier.padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Icon(
                    modifier = Modifier.noRippleClickable(onClick = { isGood = true }), // TODO
                    painter = painterResource(
                        if (isGood) R.drawable.ic_filled_like_24 else R.drawable.ic_outlined_like_24
                    ),
                    contentDescription = null,
                    tint = colors.primary
                )
                Icon(
                    modifier =
                    Modifier.noRippleClickable(onClick = { isGood = false }), // TODO
                    painter = painterResource(
                        if (!isGood) R.drawable.ic_filled_hate_24 else R.drawable.ic_outlined_hate_24
                    ),
                    contentDescription = null,
                    tint = colors.primary
                )
            }
        }
    }

    private fun handleSideEffect(sideEffect: BaseMviSideEffect) {
        when (sideEffect) {
            is BaseMviSideEffect.Toast -> toast(sideEffect.message)
        }
    }

    private fun handleState(state: MviFeedUploadState) {
        if (!state.isException()) {
            if (state.loaded && state.uploadResult) {
                toast(getString(R.string.activity_feed_detail_toast_commented))
            }
        } else {
            errorToast(this, state.exception!!)
        }
    }
}
