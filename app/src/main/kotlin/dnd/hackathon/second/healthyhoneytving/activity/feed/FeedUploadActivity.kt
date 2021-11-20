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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import dagger.hilt.android.AndroidEntryPoint
import dnd.hackathon.second.healthyhoneytving.R
import dnd.hackathon.second.healthyhoneytving.activity.feed.mvi.MviFeedUploadState
import dnd.hackathon.second.healthyhoneytving.activity.feed.viewmodel.FeedViewModel
import dnd.hackathon.second.healthyhoneytving.activity.user.composable.TopBar
import dnd.hackathon.second.healthyhoneytving.mvi.BaseMviSideEffect
import dnd.hackathon.second.healthyhoneytving.theme.MaterialTheme
import dnd.hackathon.second.healthyhoneytving.theme.colorBackgroundGray
import dnd.hackathon.second.healthyhoneytving.theme.colorTextGray
import dnd.hackathon.second.healthyhoneytving.theme.colors
import dnd.hackathon.second.healthyhoneytving.util.extension.errorToast
import dnd.hackathon.second.healthyhoneytving.util.extension.toast

@AndroidEntryPoint
class FeedUploadActivity : ComponentActivity() {

    private val vm: FeedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
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
                .padding(30.dp)
        ) {
            val (topbar, content, button) = createRefs()

            val linkFieldState = remember { mutableStateOf(TextFieldValue()) }
            val titleFieldState = remember { mutableStateOf(TextFieldValue()) }
            val descriptionFieldState = remember { mutableStateOf(TextFieldValue()) }

            fun upload() {
            }

            TopBar(
                modifier = Modifier.constrainAs(topbar) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    height = Dimension.wrapContent
                    width = Dimension.fillToConstraints
                },
                title = stringResource(R.string.activity_feed_upload_title)
            )
            Fields(
                modifier = Modifier
                    .constrainAs(content) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(topbar.bottom, 10.dp)
                        bottom.linkTo(button.top, 10.dp)
                        height = Dimension.fillToConstraints
                        width = Dimension.fillToConstraints
                    }
                    .verticalScroll(rememberScrollState()),
                linkFieldState = linkFieldState,
                titleFieldState = titleFieldState,
                descriptionFieldState = descriptionFieldState,
            )
            Button(
                modifier = Modifier.constrainAs(button) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.value(45.dp)
                },
                onClick = {
                    upload()
                }
            ) {
                Text(
                    text = stringResource(R.string.activity_feed_upload_title),
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
    ) {
        val focusManager = LocalFocusManager.current
        val (linkFocus, titleFocus, descriptionFocus) = FocusRequester.createRefs()

        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Field(
                label = "미디어 링크",
                textFieldState = linkFieldState,
                focusRequester = linkFocus,
                keyboardActions = {
                    titleFocus.requestFocus()
                }
            )
            Field(
                label = "제목",
                textFieldState = titleFieldState,
                focusRequester = titleFocus,
                keyboardActions = {
                    descriptionFocus.requestFocus()
                }
            )
            Field(
                label = "간단한 설명",
                textFieldState = descriptionFieldState,
                imeAction = ImeAction.Done,
                focusRequester = descriptionFocus,
                maxLines = 3,
                keyboardActions = {
                    focusManager.clearFocus()
                }
            )
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
