/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [FeedCreateActivity.kt] created by Ji Sungbin on 21. 11. 20. 오전 11:08
 *
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.activity.feed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dagger.hilt.android.AndroidEntryPoint
import dnd.hackathon.second.healthyhoneytving.R
import dnd.hackathon.second.healthyhoneytving.activity.feed.composable.FeedTopBar
import dnd.hackathon.second.healthyhoneytving.activity.feed.mvi.MviFeedUploadState
import dnd.hackathon.second.healthyhoneytving.activity.feed.viewmodel.FeedViewModel
import dnd.hackathon.second.healthyhoneytving.activity.main.composable.calcColorAnimationState
import dnd.hackathon.second.healthyhoneytving.activity.main.model.Feed
import dnd.hackathon.second.healthyhoneytving.activity.main.model.SnsType
import dnd.hackathon.second.healthyhoneytving.activity.main.model.toKorean
import dnd.hackathon.second.healthyhoneytving.mvi.BaseMviSideEffect
import dnd.hackathon.second.healthyhoneytving.theme.MaterialTheme
import dnd.hackathon.second.healthyhoneytving.theme.SystemUiController
import dnd.hackathon.second.healthyhoneytving.theme.colorBackgroundGray
import dnd.hackathon.second.healthyhoneytving.theme.colorTextGray
import dnd.hackathon.second.healthyhoneytving.theme.colors
import dnd.hackathon.second.healthyhoneytving.util.extension.errorToast
import dnd.hackathon.second.healthyhoneytving.util.extension.noRippleClickable
import dnd.hackathon.second.healthyhoneytving.util.extension.toast

@AndroidEntryPoint
class FeedUploadActivity : ComponentActivity() {

    private val vm: FeedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SystemUiController(window).setSystemBarsColor(colorBackgroundGray)
        setContent {
            MaterialTheme {
                Content()
            }
        }
    }

    @Composable
    private fun Content() {
        val context = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorBackgroundGray)
                .padding(30.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            val linkFieldState = remember { mutableStateOf(TextFieldValue()) }
            val titleFieldState = remember { mutableStateOf(TextFieldValue()) }
            val descriptionFieldState = remember { mutableStateOf(TextFieldValue()) }
            val tagFieldState = remember { mutableStateOf(TextFieldValue()) }
            val selectSnsState = remember { mutableStateOf("") }

            fun upload() {
                val link = linkFieldState.value.text
                val tags = tagFieldState.value.text.split("#")
                val title = titleFieldState.value.text
                val description = descriptionFieldState.value.text
                val selectSns = selectSnsState.value

                if (
                    link.isNotBlank() && tags.isNotEmpty() && title.isNotBlank() &&
                    description.isNotBlank() && selectSns != ""
                ) { // TODO: 링크 형식 채크 필요
                    val feed = Feed(
                        tags = tags,
                        link = link,
                        title = title,
                        description = description,
                        previewImageUrl = "", // TODO
                    )
                    // TODO: upload
                } else {
                    toast(context, getString(R.string.activity_feed_upload_toast_input_all_fields))
                }
            }

            FeedTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                title = stringResource(R.string.activity_feed_upload)
            )
            Fields(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                linkFieldState = linkFieldState,
                titleFieldState = titleFieldState,
                descriptionFieldState = descriptionFieldState,
                tagFieldState = tagFieldState,
                selectSnsState = selectSnsState
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp),
                onClick = {
                    upload()
                }
            ) {
                Text(
                    text = stringResource(R.string.activity_feed_upload),
                    style = TextStyle(fontSize = 15.sp)
                )
            }
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    private fun Fields(
        modifier: Modifier,
        linkFieldState: MutableState<TextFieldValue>,
        titleFieldState: MutableState<TextFieldValue>,
        descriptionFieldState: MutableState<TextFieldValue>,
        tagFieldState: MutableState<TextFieldValue>,
        selectSnsState: MutableState<String>
    ) {
        val focusManager = LocalFocusManager.current
        val (linkFocus, tagFocus, titleFocus, descriptionFocus) = FocusRequester.createRefs()

        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Field(
                label = stringResource(R.string.activity_feed_upload_media_link),
                textFieldState = linkFieldState,
                focusRequester = linkFocus,
                keyboardActions = {
                    tagFocus.requestFocus()
                }
            )
            Field(
                label = stringResource(R.string.activity_feed_upload_tags),
                textFieldState = tagFieldState,
                focusRequester = tagFocus,
                keyboardActions = {
                    titleFocus.requestFocus()
                }
            )
            Field(
                label = stringResource(R.string.activity_feed_upload_title),
                textFieldState = titleFieldState,
                focusRequester = titleFocus,
                keyboardActions = {
                    descriptionFocus.requestFocus()
                }
            )
            Field(
                label = stringResource(R.string.activity_feed_upload_description),
                textFieldState = descriptionFieldState,
                imeAction = ImeAction.Done,
                focusRequester = descriptionFocus,
                maxLines = 3,
                keyboardActions = {
                    focusManager.clearFocus()
                }
            )
            SnsPicker(selectSnsState = selectSnsState)
        }
    }

    @Composable
    private fun SnsPicker(selectSnsState: MutableState<String>) {
        val shape = RoundedCornerShape(10.dp)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(text = "SNS 채널 선택", color = colorTextGray, style = TextStyle(fontSize = 13.sp))
            LazyColumn(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(color = colorTextGray, shape = shape)
                    .border(color = colorTextGray, shape = shape, width = 2.dp)
                    .clip(shape),
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                items(SnsType.asList()) { snsName ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .noRippleClickable(onClick = {
                                selectSnsState.value = snsName
                            })
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = snsName.toKorean())
                        Icon(
                            painter = painterResource(R.drawable.ic_round_check_24),
                            contentDescription = null,
                            tint = calcColorAnimationState(
                                input = selectSnsState.value,
                                target = snsName,
                                trueColor = colors.primary,
                                falseColor = colorTextGray
                            ).value
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun Field(
        label: String,
        textFieldState: MutableState<TextFieldValue>,
        maxLines: Int = 1,
        focusRequester: FocusRequester,
        imeAction: ImeAction = ImeAction.Next,
        keyboardActions: () -> Unit
    ) {
        val shape = RoundedCornerShape(5.dp)

        val textField = textFieldState.value

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(text = label, color = colorTextGray, style = TextStyle(fontSize = 13.sp))
            OutlinedTextField(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(color = Color.White, shape = shape)
                    .focusRequester(focusRequester),
                value = textField,
                onValueChange = { newTextFieldValue ->
                    textFieldState.value = newTextFieldValue
                },
                shape = shape,
                keyboardOptions = KeyboardOptions(imeAction = imeAction),
                keyboardActions = KeyboardActions { keyboardActions() },
                singleLine = maxLines == 1,
                maxLines = maxLines,
                textStyle = TextStyle(fontSize = 15.sp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    disabledBorderColor = colorTextGray,
                    focusedBorderColor = colors.primary,
                    unfocusedBorderColor = colorTextGray,
                    backgroundColor = Color.White,
                    textColor = Color.Black
                )
            )
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
                finish()
                toast(getString(R.string.activity_feed_upload_toast_success_upload))
            }
        } else {
            errorToast(this, state.exception!!)
        }
    }
}
