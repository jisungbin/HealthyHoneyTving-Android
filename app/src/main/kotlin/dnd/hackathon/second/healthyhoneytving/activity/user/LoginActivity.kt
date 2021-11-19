/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [LoginActivity.kt] created by Ji Sungbin on 21. 11. 20. 오전 12:29
 *
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.activity.user

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import dagger.hilt.android.AndroidEntryPoint
import dnd.hackathon.second.healthyhoneytving.R
import dnd.hackathon.second.healthyhoneytving.activity.main.MainActivity
import dnd.hackathon.second.healthyhoneytving.activity.user.common.Toolbar
import dnd.hackathon.second.healthyhoneytving.activity.user.mvi.MviJoinSideEffect
import dnd.hackathon.second.healthyhoneytving.activity.user.mvi.MviJoinState
import dnd.hackathon.second.healthyhoneytving.activity.user.viewmodel.JoinViewModel
import dnd.hackathon.second.healthyhoneytving.mvi.BaseMviSideEffect
import dnd.hackathon.second.healthyhoneytving.theme.MaterialTheme
import dnd.hackathon.second.healthyhoneytving.theme.SystemUiController
import dnd.hackathon.second.healthyhoneytving.theme.colorBackgroundGray
import dnd.hackathon.second.healthyhoneytving.theme.colorError
import dnd.hackathon.second.healthyhoneytving.theme.colorTextGray
import dnd.hackathon.second.healthyhoneytving.theme.colors
import dnd.hackathon.second.healthyhoneytving.util.constant.DataConstant
import dnd.hackathon.second.healthyhoneytving.util.core.DataUtil
import dnd.hackathon.second.healthyhoneytving.util.extension.errorToast
import dnd.hackathon.second.healthyhoneytving.util.extension.toast
import org.orbitmvi.orbit.viewmodel.observe

// TODO: 가끔씩 클릭 이벤트가 아예 씹힘
@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private val vm: JoinViewModel by viewModels()

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
        val idFieldState = remember { mutableStateOf(TextFieldValue()) }
        val passwordFieldState = remember { mutableStateOf(TextFieldValue()) }
        val passwordFieldSubLabelState = remember { mutableStateOf("") }

        LaunchedEffect(Unit) {
            vm.observe(
                lifecycleOwner = this@LoginActivity,
                sideEffect = ::handleSideEffect,
                state = { state ->
                    handleState(
                        state = state,
                        updatePasswordFieldSubLabelMessage = {
                            passwordFieldSubLabelState.value =
                                getString(R.string.activity_login_not_match_password)
                        }
                    )
                }
            )
        }

        fun login() {
            val id = idFieldState.value.text
            val password = passwordFieldState.value.text
            vm.login(id, password)
        }

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorBackgroundGray)
                .padding(30.dp)
        ) {
            val (topbar, content, button) = createRefs()

            Toolbar(
                modifier = Modifier.constrainAs(topbar) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    height = Dimension.wrapContent
                    width = Dimension.fillToConstraints
                },
                title = stringResource(R.string.activity_register_title)
            )
            Fields(
                modifier = Modifier.constrainAs(content) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(topbar.bottom, 10.dp)
                    bottom.linkTo(button.top, 10.dp)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                },
                idFieldState = idFieldState,
                passwordFieldState = passwordFieldState,
                passwordFieldSubLabelState = passwordFieldSubLabelState,
                doneKeyboardAction = { login() }
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
                    login()
                }
            ) {
                Text(
                    text = stringResource(R.string.activity_start_button_login),
                    style = TextStyle(fontSize = 15.sp)
                )
            }
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    private fun Fields(
        modifier: Modifier,
        idFieldState: MutableState<TextFieldValue>,
        passwordFieldState: MutableState<TextFieldValue>,
        passwordFieldSubLabelState: MutableState<String>,
        doneKeyboardAction: () -> Unit
    ) {
        val focusManager = LocalFocusManager.current
        val (idFocus, passwordFocus) = FocusRequester.createRefs()

        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Field(
                label = stringResource(R.string.activity_register_id),
                textFieldState = idFieldState,
                focusRequester = idFocus,
                keyboardActions = {
                    passwordFocus.requestFocus()
                }
            )
            Field(
                label = stringResource(R.string.activity_register_password),
                textFieldState = passwordFieldState,
                subLabelState = passwordFieldSubLabelState,
                isPasswordField = true,
                imeAction = ImeAction.Done,
                focusRequester = passwordFocus,
                keyboardActions = {
                    focusManager.clearFocus()
                    doneKeyboardAction()
                }
            )
        }
    }

    @Composable
    private fun Field(
        label: String,
        textFieldState: MutableState<TextFieldValue>,
        subLabelState: MutableState<String> = mutableStateOf(""),
        isPasswordField: Boolean = false,
        focusRequester: FocusRequester,
        imeAction: ImeAction = ImeAction.Next,
        keyboardActions: () -> Unit
    ) {
        val shape = RoundedCornerShape(5.dp)
        val textField = textFieldState.value
        var passwordVisibility by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = label, color = colorTextGray, fontSize = 13.sp)
                Text(text = subLabelState.value, color = colorError, fontSize = 11.sp)
            }
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(color = Color.White, shape = shape)
                    .focusRequester(focusRequester),
                value = textField,
                onValueChange = { newTextFieldValue ->
                    if (newTextFieldValue.text.length <= 13) {
                        textFieldState.value = newTextFieldValue
                    }
                },
                shape = shape,
                keyboardOptions = KeyboardOptions(imeAction = imeAction),
                keyboardActions = KeyboardActions { keyboardActions() },
                singleLine = true,
                maxLines = 1,
                textStyle = TextStyle(fontSize = 15.sp),
                visualTransformation = if (isPasswordField) {
                    if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation()
                } else VisualTransformation.None,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    disabledBorderColor = colorTextGray,
                    focusedBorderColor = colors.primary,
                    unfocusedBorderColor = colorTextGray,
                    backgroundColor = Color.White,
                    textColor = Color.Black
                ),
                trailingIcon = {
                    if (isPasswordField) {
                        Icon(
                            modifier = Modifier.clickable {
                                passwordVisibility = !passwordVisibility
                            },
                            painter = painterResource(if (passwordVisibility) R.drawable.ic_round_visibility_off_24 else R.drawable.ic_round_visibility_24),
                            contentDescription = null
                        )
                    }
                }
            )
        }
    }

    private fun handleSideEffect(sideEffect: BaseMviSideEffect) {
        when (sideEffect) {
            is MviJoinSideEffect.SetupAutoLogin -> DataUtil.save(
                applicationContext,
                DataConstant.User.Id,
                sideEffect.user.id
            )
            is BaseMviSideEffect.Toast -> toast(sideEffect.message)
        }
    }

    private fun handleState(state: MviJoinState, updatePasswordFieldSubLabelMessage: () -> Unit) {
        if (!state.isException()) {
            if (state.loaded) {
                if (state.loginResult) {
                    finish()
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    updatePasswordFieldSubLabelMessage()
                }
            }
        } else {
            errorToast(this, state.exception!!)
        }
    }
}
