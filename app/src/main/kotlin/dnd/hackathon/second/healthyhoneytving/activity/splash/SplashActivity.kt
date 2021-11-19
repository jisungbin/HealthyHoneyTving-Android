/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [SplashActivity.kt] created by Ji Sungbin on 21. 11. 19. 오후 10:50
 *
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.activity.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dnd.hackathon.second.healthyhoneytving.R
import dnd.hackathon.second.healthyhoneytving.theme.MaterialTheme
import dnd.hackathon.second.healthyhoneytving.theme.SystemUiController
import dnd.hackathon.second.healthyhoneytving.theme.colors
import dnd.hackathon.second.healthyhoneytving.util.extension.doDelayed

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SystemUiController(window).setSystemBarsColor(colors.primary)
        setContent {
            MaterialTheme {
                Content()
            }
        }
    }

    @Composable
    private fun Content() {
        var rotate by remember { mutableStateOf(90f) }
        var offsetX by remember { mutableStateOf(0.dp) }

        val animateRotate by animateFloatAsState(
            targetValue = rotate,
            animationSpec = tween(durationMillis = 1000)
        )
        val animateOffsetX by animateDpAsState(
            targetValue = offsetX,
            animationSpec = tween(durationMillis = 1000)
        )

        var showLabel by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            doDelayed(1000L) {
                rotate = 0F
                offsetX = (-90).dp
                showLabel = true
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colors.primary),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                modifier = Modifier
                    .size(70.dp)
                    .rotate(animateRotate)
                    .offset(x = animateOffsetX),
                painter = painterResource(R.drawable.ic_round_logo_24),
                contentDescription = null
            )

            AnimatedVisibility(
                visible = showLabel,
                enter = slideInHorizontally(initialOffsetX = { it / 2 })
            ) {
                Text(
                    modifier = Modifier.offset(x = 35.dp),
                    text = stringResource(R.string.app_name),
                    color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
