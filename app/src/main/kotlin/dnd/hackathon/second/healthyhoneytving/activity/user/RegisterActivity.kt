/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [RegisterActivity.kt] created by Ji Sungbin on 21. 11. 20. 오전 12:29
 *
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.activity.user

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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import dnd.hackathon.second.healthyhoneytving.activity.user.common.Toolbar
import dnd.hackathon.second.healthyhoneytving.activity.user.model.User
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
import dnd.hackathon.second.healthyhoneytving.util.extension.doWhen
import dnd.hackathon.second.healthyhoneytving.util.extension.errorToast
import dnd.hackathon.second.healthyhoneytving.util.extension.toException
import dnd.hackathon.second.healthyhoneytving.util.extension.toast
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.viewmodel.observe

// TODO: 가끔씩 클릭 이벤트가 아예 씹힘
@AndroidEntryPoint
class RegisterActivity : ComponentActivity() {

    private val vm: JoinViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.observe(lifecycleOwner = this, state = ::handleState, sideEffect = ::handleSideEffect)
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
        val passwordConfirmFieldState = remember { mutableStateOf(TextFieldValue()) }
        val nicknameFieldState = remember { mutableStateOf(TextFieldValue()) }
        val passwordFieldSubLabelState = remember { mutableStateOf("") }
        val nicknameFieldSubLabelState = remember { mutableStateOf("") }
        var nicknameFieldSubLabalColorState by remember { mutableStateOf(Color.Unspecified) }
        val isNicknameUseableState = remember { mutableStateOf<Boolean?>(null) }

        if (isNicknameUseableState.value == true) {
            nicknameFieldSubLabelState.value =
                stringResource(R.string.activity_register_useable_nickname)
            nicknameFieldSubLabalColorState = colors.primary
        } else if (isNicknameUseableState.value == false) {
            nicknameFieldSubLabelState.value =
                stringResource(R.string.activity_register_already_using_nickname)
            nicknameFieldSubLabalColorState = colorError
        }

        fun register() {
            val id = idFieldState.value.text
            val password = passwordFieldState.value.text
            val passwordConfirm = passwordConfirmFieldState.value.text
            val nickname = nicknameFieldState.value.text
            val isNicknameUseable = isNicknameUseableState.value
            if (password == passwordConfirm) {
                if (isNicknameUseable == null) {
                    toast(getString(R.string.activity_register_toast_check_nickname_duplicate))
                } else if (isNicknameUseable) {
                    val user = User(nickname = nickname, id = id, password = password)
                    vm.register(user)
                }
            } else {
                passwordFieldSubLabelState.value =
                    getString(R.string.activity_register_password_not_match_confirm)
            }
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
                passwordConfirmFieldState = passwordConfirmFieldState,
                nicknameFieldState = nicknameFieldState,
                passwordFieldSubLabelState = passwordFieldSubLabelState,
                nicknameFieldSubLabel = nicknameFieldSubLabelState,
                nicknameFieldSubLabalColor = nicknameFieldSubLabalColorState,
                isNicknameUseableState = isNicknameUseableState,
                doneKeyboardAction = { register() }
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
                    register()
                }
            ) {
                Text(
                    text = stringResource(R.string.activity_register_button_reigster),
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
        passwordConfirmFieldState: MutableState<TextFieldValue>,
        nicknameFieldState: MutableState<TextFieldValue>,
        passwordFieldSubLabelState: MutableState<String>,
        nicknameFieldSubLabel: MutableState<String>,
        nicknameFieldSubLabalColor: Color,
        isNicknameUseableState: MutableState<Boolean?>,
        doneKeyboardAction: () -> Unit
    ) {
        val passwordVisibilityState = remember { mutableStateOf(false) }

        val focusManager = LocalFocusManager.current
        val (idFocus, passwordFocus, passwordConfirmFocus, nicknameFocus) = FocusRequester.createRefs()

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
                passwordVisibilityState = passwordVisibilityState,
                isPasswordField = true,
                focusRequester = passwordFocus,
                keyboardActions = {
                    passwordConfirmFocus.requestFocus()
                }
            )
            Field(
                label = stringResource(R.string.activity_register_confirm_password),
                textFieldState = passwordConfirmFieldState,
                isPasswordField = true,
                passwordVisibilityState = passwordVisibilityState,
                subLabelState = passwordFieldSubLabelState,
                subLabelColor = colorError,
                focusRequester = passwordConfirmFocus,
                keyboardActions = {
                    nicknameFocus.requestFocus()
                }
            )
            Field(
                label = stringResource(R.string.activity_register_nickname),
                textFieldState = nicknameFieldState,
                subLabelState = nicknameFieldSubLabel,
                subLabelColor = nicknameFieldSubLabalColor,
                isNicknameField = true,
                isNicknameUseableState = isNicknameUseableState,
                imeAction = ImeAction.Done,
                focusRequester = nicknameFocus,
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
        subLabelColor: Color = Color.Unspecified,
        isPasswordField: Boolean = false,
        passwordVisibilityState: MutableState<Boolean> = mutableStateOf(false),
        isNicknameField: Boolean = false,
        isNicknameUseableState: MutableState<Boolean?> = mutableStateOf(null),
        focusRequester: FocusRequester,
        imeAction: ImeAction = ImeAction.Next,
        keyboardActions: () -> Unit
    ) {
        val context = LocalContext.current
        val shape = RoundedCornerShape(5.dp)
        val coroutineScope = rememberCoroutineScope()

        val textField = textFieldState.value
        val passwordVisibility = passwordVisibilityState.value

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
                Text(text = subLabelState.value, color = subLabelColor, fontSize = 11.sp)
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
                    when {
                        isPasswordField -> {
                            Icon(
                                modifier = Modifier.clickable {
                                    passwordVisibilityState.value = !passwordVisibility
                                },
                                painter = painterResource(if (passwordVisibility) R.drawable.ic_round_visibility_off_24 else R.drawable.ic_round_visibility_24),
                                contentDescription = null
                            )
                        }
                        isNicknameField -> {
                            Button(
                                modifier = Modifier.padding(5.dp),
                                onClick = {
                                    coroutineScope.launch {
                                        vm.findUserByNickname(textField.text).doWhen(
                                            onSuccess = { users ->
                                                isNicknameUseableState.value = users.isEmpty()
                                                if (!isNicknameUseableState.value!!) {
                                                    subLabelState.value =
                                                        getString(R.string.activity_register_already_using_nickname)
                                                }
                                            },
                                            onFailure = { throwable ->
                                                errorToast(context, throwable.toException())
                                            }
                                        )
                                    }
                                }
                            ) {
                                Text(
                                    text = stringResource(R.string.activity_register_button_check_nickname_duplicate),
                                    style = TextStyle(fontSize = 14.sp)
                                )
                            }
                        }
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

    private fun handleState(state: MviJoinState) {
        if (!state.isException()) {
            if (state.loaded && state.registerResult) {
                // TODO: 가입 성공
            }
        } else {
            errorToast(this, state.exception!!)
        }
    }
}
