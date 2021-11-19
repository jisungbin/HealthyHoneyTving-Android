/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [ExceptionActivity.kt] created by Ji Sungbin on 21. 11. 19. 오후 10:58
 *  
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.activity.exception

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.firebase.firestore.CollectionReference
import dagger.hilt.android.AndroidEntryPoint
import dnd.hackathon.second.healthyhoneytving.R
import dnd.hackathon.second.healthyhoneytving.di.qualifier.FirestoreException
import dnd.hackathon.second.healthyhoneytving.theme.SystemUiController
import dnd.hackathon.second.healthyhoneytving.util.constant.ExceptionConstant
import io.github.jisungbin.erratum.ErratumExceptionActivity
import java.util.Date
import javax.inject.Inject

@AndroidEntryPoint
class ExceptionActivity : ErratumExceptionActivity() {

    @Inject
    @FirestoreException
    lateinit var firestoreException: CollectionReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firestoreException
            .document(Date().toString())
            .set(mapOf("data" to exceptionString!!))
        SystemUiController(window).setSystemBarsColor(Color.White)
        setContent {
            Content()
        }
    }

    @Composable
    private fun Content() {
        val content = ExceptionConstant.ObjectContents.random()
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.crying))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),
            verticalArrangement = Arrangement.spacedBy(
                space = 30.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                modifier = Modifier.size(300.dp),
                iterations = LottieConstants.IterateForever,
                composition = composition,
            )
            Text(
                text = stringResource(
                    R.string.activity_exception_occurred,
                    content,
                    exceptionString!!
                ),
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Button(onClick = {
                finish()
                openLastActivity()
            }) {
                Text(
                    text = stringResource(
                        R.string.activity_exception_button_retry_with_avoid,
                        content
                    )
                )
            }
        }
    }
}
