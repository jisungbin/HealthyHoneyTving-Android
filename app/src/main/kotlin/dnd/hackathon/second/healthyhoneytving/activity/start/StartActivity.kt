/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [SplashActivity.kt] created by Ji Sungbin on 21. 11. 19. 오후 10:50
 *
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.activity.start

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dagger.hilt.android.AndroidEntryPoint
import dnd.hackathon.second.healthyhoneytving.BuildConfig
import dnd.hackathon.second.healthyhoneytving.R
import dnd.hackathon.second.healthyhoneytving.activity.feed.viewmodel.FeedViewModel
import dnd.hackathon.second.healthyhoneytving.activity.main.MainActivity
import dnd.hackathon.second.healthyhoneytving.activity.user.LoginActivity
import dnd.hackathon.second.healthyhoneytving.activity.user.RegisterActivity
import dnd.hackathon.second.healthyhoneytving.activity.user.viewmodel.JoinViewModel
import dnd.hackathon.second.healthyhoneytving.store.DataStore
import dnd.hackathon.second.healthyhoneytving.theme.MaterialTheme
import dnd.hackathon.second.healthyhoneytving.theme.SystemUiController
import dnd.hackathon.second.healthyhoneytving.theme.colors
import dnd.hackathon.second.healthyhoneytving.util.constant.DataConstant
import dnd.hackathon.second.healthyhoneytving.util.core.DataUtil
import dnd.hackathon.second.healthyhoneytving.util.extension.doDelayed
import dnd.hackathon.second.healthyhoneytving.util.extension.doWhen
import dnd.hackathon.second.healthyhoneytving.util.extension.toast
import java.util.Calendar

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class StartActivity : ComponentActivity() {

    private val feedVm: FeedViewModel by viewModels()
    private val joinVm: JoinViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val builtDate = Calendar.getInstance().apply { timeInMillis = BuildConfig.TIMESTAMP }
        val builtTime = "${builtDate.get(Calendar.HOUR_OF_DAY)}h " +
            "${builtDate.get(Calendar.MINUTE)}m " +
            "${builtDate.get(Calendar.SECOND)}s"
        toast(builtTime)

        SystemUiController(window).setSystemBarsColor(colors.primary)
        setContent {
            MaterialTheme {
                Content()
            }
        }
    }

    @Composable
    private fun Content() {
        val autoLoginId = DataUtil.read(applicationContext, DataConstant.UserId, null)

        var rotate by remember { mutableStateOf(90f) }
        var iconOffsetX by remember { mutableStateOf(0.dp) }
        var titleOffsetY by remember { mutableStateOf(0.dp) }

        val animateRotate by animateFloatAsState(
            targetValue = rotate,
            animationSpec = tween(durationMillis = 1000)
        )
        val animateIconOffsetX by animateDpAsState(
            targetValue = iconOffsetX,
            animationSpec = tween(durationMillis = 1000)
        )
        val animateTitleOffsetY by animateDpAsState(
            targetValue = titleOffsetY,
            animationSpec = tween(durationMillis = 1000)
        )

        var showLabel by remember { mutableStateOf(false) }
        var showButtons by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            doDelayed(1000L) {
                rotate = 0F
                iconOffsetX = (-90).dp
                showLabel = true
                joinVm.loadAllUsers().doWhen(
                    onSuccess = {},
                    onFailure = { throwable ->
                        throw throwable
                    }
                )
                feedVm.loadAllFeeds().doWhen(
                    onSuccess = {},
                    onFailure = { throwable ->
                        throw throwable
                    }
                )
            }

            doDelayed(500L) {
                if (autoLoginId == null) {
                    titleOffsetY = (-150).dp
                    showButtons = true
                } else {
                    val me = DataStore.getFirstUserFromId(autoLoginId)
                    DataStore.me = me
                    finish()
                    startActivity(Intent(this@StartActivity, MainActivity::class.java))
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colors.primary)
                .padding(50.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier
                        .size(70.dp)
                        .rotate(animateRotate)
                        .offset(x = animateIconOffsetX, y = animateTitleOffsetY),
                    painter = painterResource(R.drawable.ic_round_logo_24),
                    contentDescription = null
                )

                AnimatedVisibility(
                    modifier = Modifier.offset(x = 35.dp, y = animateTitleOffsetY),
                    visible = showLabel,
                    enter = slideInHorizontally(
                        animationSpec = tween(durationMillis = 1000),
                        initialOffsetX = { fullWidth -> fullWidth / 2 }
                    ) + fadeIn()
                ) {
                    Text(
                        text = stringResource(R.string.app_name),
                        color = Color.White,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                AnimatedVisibility(
                    visible = showButtons,
                    enter = slideInVertically(
                        animationSpec = tween(durationMillis = 1000),
                        initialOffsetY = { fullHeight -> fullHeight / 2 },
                    ) + fadeIn()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        OutlinedButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            onClick = { startActivity(LoginActivity::class.java) }
                        ) {
                            Text(
                                text = stringResource(R.string.activity_start_button_login),
                                style = TextStyle(fontSize = 18.sp)
                            )
                        }
                        OutlinedButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            onClick = { startActivity(RegisterActivity::class.java) }
                        ) {
                            Text(
                                text = stringResource(R.string.activity_start_button_register),
                                style = TextStyle(fontSize = 18.sp)
                            )
                        }
                    }
                }
            }
        }
    }

    private fun <T> startActivity(activity: Class<T>) {
        startActivity(Intent(this, activity))
    }
}
