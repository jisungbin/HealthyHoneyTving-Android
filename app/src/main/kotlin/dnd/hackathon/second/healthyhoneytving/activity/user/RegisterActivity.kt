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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dnd.hackathon.second.healthyhoneytving.R
import dnd.hackathon.second.healthyhoneytving.activity.user.common.Toolbar
import dnd.hackathon.second.healthyhoneytving.activity.user.viewmodel.JoinViewModel
import dnd.hackathon.second.healthyhoneytving.theme.MaterialTheme
import dnd.hackathon.second.healthyhoneytving.theme.SystemUiController
import dnd.hackathon.second.healthyhoneytving.theme.colorBackgroundGray
import dnd.hackathon.second.healthyhoneytving.theme.colorError
import dnd.hackathon.second.healthyhoneytving.theme.colorTextGray
import dnd.hackathon.second.healthyhoneytving.theme.colors
import dnd.hackathon.second.healthyhoneytving.util.extension.doWhen
import dnd.hackathon.second.healthyhoneytving.util.extension.errorToast
import dnd.hackathon.second.healthyhoneytving.util.extension.toException
import dnd.hackathon.second.healthyhoneytving.util.extension.toast
import kotlinx.coroutines.launch

class RegisterActivity : ComponentActivity() {
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
        val passwordConfirmFieldState = remember { mutableStateOf(TextFieldValue()) }
        val nicknameFieldState = remember { mutableStateOf(TextFieldValue()) }
        val subLabelState = remember { mutableStateOf("") }
        val isNicknameUseableState = remember { mutableStateOf<Boolean?>(null) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorBackgroundGray)
                .padding(30.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Toolbar(title = stringResource(R.string.activity_register_title))
            Fields(
                idFieldState = idFieldState,
                passwordFieldState = passwordFieldState,
                passwordConfirmFieldState = passwordConfirmFieldState,
                nicknameFieldState = nicknameFieldState,
                subLabel = subLabelState.value,
                isNicknameUseableState = isNicknameUseableState
            )
            Button(modifier = Modifier.fillMaxWidth(), onClick = { /*TODO*/ }) {
                Text(text = "가입하기")
            }
        }
    }

    @Composable
    private fun Fields(
        idFieldState: MutableState<TextFieldValue>,
        passwordFieldState: MutableState<TextFieldValue>,
        passwordConfirmFieldState: MutableState<TextFieldValue>,
        nicknameFieldState: MutableState<TextFieldValue>,
        subLabel: String,
        isNicknameUseableState: MutableState<Boolean?>
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Field(label = "아이디", textFieldState = idFieldState)
            Field(
                label = "비밀번호",
                textFieldState = passwordFieldState,
                isPasswordField = true
            )
            Field(
                label = "비밀번호 확인",
                textFieldState = passwordConfirmFieldState,
                isPasswordField = true,
                subLabel = subLabel,
                subLabelColor = colorError
            )
            Field(
                label = "닉네임",
                textFieldState = nicknameFieldState,
                subLabel = subLabel,
                subLabelColor = colorError,
                isNicknameField = true,
                isNicknameUseableState = isNicknameUseableState
            )
        }
    }

    @Composable
    private fun Field(
        label: String,
        textFieldState: MutableState<TextFieldValue>,
        subLabel: String = "",
        subLabelColor: Color = Color.Unspecified,
        isPasswordField: Boolean = false,
        isNicknameField: Boolean = false,
        isNicknameUseableState: MutableState<Boolean?> = mutableStateOf(null)
    ) {
        val context = LocalContext.current
        val vm: JoinViewModel = viewModel()
        val shape = RoundedCornerShape(5.dp)
        val coroutineScope = rememberCoroutineScope()

        var passwordVisibility by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(text = label, color = colorTextGray, fontSize = 13.sp)
                Text(text = subLabel, color = subLabelColor, fontSize = 10.sp)
            }
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                value = textFieldState.value,
                onValueChange = { textFieldState.value = it },
                shape = shape,
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                colors = TextFieldDefaults.textFieldColors(
                    disabledIndicatorColor = colorTextGray,
                    focusedIndicatorColor = colors.primary,
                    unfocusedIndicatorColor = colorTextGray,
                    backgroundColor = Color.White,
                    textColor = Color.Black
                ),
                trailingIcon = {
                    when {
                        isPasswordField -> {
                            Icon(
                                modifier = Modifier.clickable {
                                    passwordVisibility = !passwordVisibility
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
                                        vm.findUserByNickname(textFieldState.value.text).doWhen(
                                            onSuccess = { users ->
                                                isNicknameUseableState.value = users.isEmpty()
                                                if (!isNicknameUseableState.value!!) {
                                                    toast(
                                                        context,
                                                        context.getString(R.string.activity_register_toast_already_using_nickname)
                                                    )
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
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}
