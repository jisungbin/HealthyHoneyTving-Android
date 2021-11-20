/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [MainActivity.kt] created by Ji Sungbin on 21. 11. 20. 오전 4:44
 *
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.activity.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import dagger.hilt.android.AndroidEntryPoint
import dnd.hackathon.second.healthyhoneytving.R
import dnd.hackathon.second.healthyhoneytving.activity.feed.viewmodel.FeedViewModel
import dnd.hackathon.second.healthyhoneytving.activity.main.composable.BottomBar
import dnd.hackathon.second.healthyhoneytving.activity.main.composable.Categorie
import dnd.hackathon.second.healthyhoneytving.activity.main.composable.HorizontalDivider
import dnd.hackathon.second.healthyhoneytving.activity.main.composable.LazyFeed
import dnd.hackathon.second.healthyhoneytving.activity.main.composable.Menu
import dnd.hackathon.second.healthyhoneytving.activity.main.composable.TopBar
import dnd.hackathon.second.healthyhoneytving.theme.MaterialTheme
import dnd.hackathon.second.healthyhoneytving.theme.SystemUiController
import dnd.hackathon.second.healthyhoneytving.theme.colorBackgroundGray
import dnd.hackathon.second.healthyhoneytving.util.core.NotificationUtil
import dnd.hackathon.second.healthyhoneytving.util.extension.doWhen
import dnd.hackathon.second.healthyhoneytving.util.extension.errorToast
import dnd.hackathon.second.healthyhoneytving.util.extension.toException

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val feedVm: FeedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        NotificationUtil.showNormalNotification(
            context = applicationContext,
            id = 1000,
            channelId = getString(R.string.app_name),
            title = "내 소중한 건강!",
            content = "스트레칭 하시는거 잊지 않으셨죠? :)",
            isOnGoing = false,
            showTimestamp = false,
            icon = R.drawable.ic_round_logo_500
        )
        SystemUiController(window).setSystemBarsColor(Color.White)
        setContent {
            LaunchedEffect(Unit) {
                feedVm.loadAllComments().doWhen(
                    onSuccess = {},
                    onFailure = { throwable ->
                        errorToast(this@MainActivity, throwable.toException("댓글 로드 실패"))
                    }
                )
            }

            MaterialTheme {
                Content()
            }
        }
    }

    @Composable
    private fun Content() {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorBackgroundGray)
        ) {
            val (content, bottomBar) = createRefs()

            Column(
                modifier = Modifier.constrainAs(content) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(bottomBar.top)

                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
            ) {
                TopBar()
                HorizontalDivider(thickness = 1.dp)
                Categorie()
                HorizontalDivider(thickness = 1.dp)
                Menu()
                LazyFeed()
            }
            BottomBar(
                modifier = Modifier.constrainAs(bottomBar) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)

                    width = Dimension.fillToConstraints
                    height = Dimension.value(60.dp)
                }
            )
        }
    }
}
