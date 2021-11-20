/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [feed.kt] created by Ji Sungbin on 21. 11. 20. 오전 6:14
 *  
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.activity.main.composable

import android.content.Intent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.coil.CoilImage
import dnd.hackathon.second.healthyhoneytving.R
import dnd.hackathon.second.healthyhoneytving.activity.feed.FeedDetailActivity
import dnd.hackathon.second.healthyhoneytving.activity.main.model.Feed
import dnd.hackathon.second.healthyhoneytving.activity.main.model.MenuType
import dnd.hackathon.second.healthyhoneytving.activity.main.viewmodel.MainViewModel
import dnd.hackathon.second.healthyhoneytving.store.DataStore
import dnd.hackathon.second.healthyhoneytving.theme.colorBackgroundGray
import dnd.hackathon.second.healthyhoneytving.util.constant.IntentConstant
import dnd.hackathon.second.healthyhoneytving.util.extension.composableActivityViewModel
import dnd.hackathon.second.healthyhoneytving.util.extension.getActivity
import dnd.hackathon.second.healthyhoneytving.util.extension.noRippleClickable
import dnd.hackathon.second.healthyhoneytving.util.extension.toTimeString
import kotlin.random.Random

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun LazyFeed() {
    val activity = getActivity()
    val vm: MainViewModel = composableActivityViewModel()
    val mainTypeState by vm.mainType.collectAsState()
    val selectCategory by vm.selectCategory.collectAsState()
    val menuTypeState = vm.menuType.collectAsState()
    val feeds =
        DataStore.feeds.filter { it.mainType == mainTypeState && it.tags.contains(selectCategory) }

    Crossfade(
        modifier = Modifier.background(color = colorBackgroundGray),
        targetState = menuTypeState.value
    ) { menuType ->
        when (menuType) {
            MenuType.List -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(feeds) { feed ->
                        FeedListItem(
                            modifier = Modifier
                                .animateItemPlacement()
                                .background(color = Color.White)
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .noRippleClickable(onClick = {
                                    activity.startActivity(
                                        Intent(activity, FeedDetailActivity::class.java).apply {
                                            putExtra(IntentConstant.FeedId, feed.feedUid)
                                        }
                                    )
                                }),
                            feed = feed
                        )
                    }
                }
            }
            MenuType.Grid -> {
                LazyVerticalGrid(
                    cells = GridCells.Fixed(2), modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(10.dp)
                ) {
                    val shape = RoundedCornerShape(5.dp)

                    items(feeds) { feed ->
                        FeedGridItem(
                            modifier = Modifier
                                .animateItemPlacement()
                                .wrapContentSize()
                                .background(color = Color.White, shape = shape)
                                .clip(shape)
                                .noRippleClickable(onClick = {
                                    activity.startActivity(
                                        Intent(activity, FeedDetailActivity::class.java).apply {
                                            putExtra(IntentConstant.FeedId, feed.feedUid)
                                        }
                                    )
                                }),
                            feed = feed
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FeedListItem(modifier: Modifier, feed: Feed, showDotMenu: Boolean = true) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(
                    space = 15.dp,
                    alignment = Alignment.CenterHorizontally
                )
            ) { // TODO
                Text(text = DataStore.me.nickname, style = TextStyle(fontSize = 20.sp))
                Text(
                    modifier = Modifier.padding(bottom = 2.dp),
                    text = feed.createdAt.toTimeString(),
                    color = Color.LightGray,
                    style = TextStyle(fontSize = 13.sp)
                )
            }
            if (showDotMenu) {
                Icon(
                    modifier = Modifier.noRippleClickable(onClick = {}), // TODO
                    painter = painterResource(R.drawable.ic_round_menu_dot_24),
                    contentDescription = null
                )
            }
        }
        CoilImage(
            modifier = Modifier.requiredHeightIn(max = 500.dp),
            imageModel = feed.previewImageUrl,
            contentScale = ContentScale.FillBounds
        )
        Row(
            modifier = Modifier
                .padding(16.dp)
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
                .padding(16.dp)
                .fillMaxWidth(),
            text = feed.description,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
            style = TextStyle(fontSize = 15.sp)
        )
    }
}

@Composable
private fun FeedGridItem(modifier: Modifier, feed: Feed) {
    Column(modifier = modifier) {
        CoilImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            imageModel = feed.previewImageUrl,
            contentScale = ContentScale.FillBounds
        )
        Row(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
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
                Text(
                    text = Random.nextInt(0, 30).toString(),
                    style = TextStyle(fontSize = 10.sp)
                )
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
                Text(
                    text = Random.nextInt(0, 30).toString(),
                    style = TextStyle(fontSize = 10.sp)
                )
            }
            Icon(
                modifier = Modifier.noRippleClickable(onClick = {}), // TODO
                painter = painterResource(R.drawable.ic_round_menu_dot_24),
                contentDescription = null
            )
        }
    }
}
