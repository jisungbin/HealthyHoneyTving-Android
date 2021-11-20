/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [scaffold.kt] created by Ji Sungbin on 21. 11. 20. 오전 6:17
 *  
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.activity.main.composable

import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.BottomAppBar
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import dnd.hackathon.second.healthyhoneytving.R
import dnd.hackathon.second.healthyhoneytving.activity.feed.FeedUploadActivity
import dnd.hackathon.second.healthyhoneytving.activity.main.model.MainType
import dnd.hackathon.second.healthyhoneytving.activity.main.viewmodel.MainViewModel
import dnd.hackathon.second.healthyhoneytving.util.extension.composableActivityViewModel
import dnd.hackathon.second.healthyhoneytving.util.extension.getActivity
import dnd.hackathon.second.healthyhoneytving.util.extension.noRippleClickable

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .background(color = Color.White)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(40.dp),
                painter = painterResource(R.drawable.ic_round_logo_transparent_24),
                contentDescription = null
            )
            Text(
                text = stringResource(R.string.app_name),
                style = TextStyle(fontSize = 18.sp),
                fontWeight = FontWeight.Bold
            )
        }
        Icon(
            modifier = Modifier,
            painter = painterResource(R.drawable.ic_round_search_24),
            contentDescription = null
        )
    }
}

@Composable
fun BottomBar(modifier: Modifier) {
    val activity = getActivity()
    val vm: MainViewModel = composableActivityViewModel()
    val mainTypeState by vm.mainType.collectAsState()

    BottomAppBar(
        modifier = modifier.background(color = Color.White),
        backgroundColor = Color.White,
        contentPadding = PaddingValues(0.dp),
        elevation = 16.dp
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (media, fab, healthy) = createRefs()

            BottomBarItem(
                modifier = Modifier
                    .constrainAs(media) {
                        start.linkTo(parent.start)
                        end.linkTo(fab.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)

                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
                    .noRippleClickable(onClick = {
                        vm.updateMainType(MainType.Media)
                    }),
                iconRes = R.drawable.ic_round_media_24,
                title = stringResource(R.string.activity_main_composable_scaffold_bottombar_media),
                tint = calcColorAnimationState(
                    input = MainType.Media,
                    target = mainTypeState
                ).value
            )
            FloatingActionButton(
                modifier = Modifier.constrainAs(fab) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
                onClick = {
                    activity.startActivity(Intent(activity, FeedUploadActivity::class.java))
                },
                elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_round_add_24),
                    contentDescription = null,
                    tint = Color.White
                )
            }
            BottomBarItem(
                modifier = Modifier
                    .constrainAs(healthy) {
                        start.linkTo(fab.end)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)

                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
                    .noRippleClickable(onClick = {
                        vm.updateMainType(MainType.Healthy)
                    }),
                iconRes = R.drawable.ic_round_healthy_24,
                title = stringResource(R.string.activity_main_composable_scaffold_bottombar_healthy),
                tint = calcColorAnimationState(
                    input = MainType.Healthy,
                    target = mainTypeState
                ).value
            )
        }
    }
}

@Composable
private fun BottomBarItem(
    modifier: Modifier,
    @DrawableRes iconRes: Int,
    title: String,
    tint: Color
) {
    Column(
        modifier = modifier.background(color = Color.White),
        verticalArrangement = Arrangement.spacedBy(
            space = 2.dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(painter = painterResource(iconRes), contentDescription = null, tint = tint)
        Text(text = title, color = tint, style = TextStyle(fontSize = 10.sp))
    }
}
