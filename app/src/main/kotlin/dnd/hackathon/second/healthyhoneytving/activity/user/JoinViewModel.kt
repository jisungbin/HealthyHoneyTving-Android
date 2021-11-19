/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [UserViewModel.kt] created by Ji Sungbin on 21. 11. 20. 오전 12:45
 *  
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.activity.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dnd.hackathon.second.healthyhoneytving.activity.user.model.User
import dnd.hackathon.second.healthyhoneytving.activity.user.mvi.MviJoinSideEffect
import dnd.hackathon.second.healthyhoneytving.activity.user.mvi.MviJoinState
import dnd.hackathon.second.healthyhoneytving.mvi.BaseMviToastSideEffect
import dnd.hackathon.second.healthyhoneytving.util.extension.doWhen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import kotlin.coroutines.resume

class JoinViewModel : ViewModel(), ContainerHost<MviJoinState, BaseMviToastSideEffect> {

    private val firestoreUsers = Firebase.firestore.collection("users")
    override val container = container<MviJoinState, BaseMviToastSideEffect>(MviJoinState())

    @OptIn(ExperimentalCoroutinesApi::class)
    fun register(user: User) = intent {
        findUserById(user.id).doWhen(
            onSuccess = { foundUsers ->
                if (foundUsers.isEmpty()) {
                    firestoreUsers.document(user.id).set(user)
                        .addOnSuccessListener {
                            viewModelScope.launch {
                                reduce {
                                    state.copy(
                                        loaded = true,
                                        registerResult = true,
                                        exception = null
                                    )
                                }
                                postSideEffect(BaseMviToastSideEffect.Toast("가입이 완료되었어요."))
                            }
                        }.addOnFailureListener { exception ->
                            viewModelScope.launch {
                                reduce {
                                    state.copy(exception = exception)
                                }
                            }
                        }
                } else {
                    postSideEffect(BaseMviToastSideEffect.Toast("이미 ${user.id}로 가입된 아이디가 있어요."))
                }
            },
            onFailure = { exception ->
                reduce {
                    state.copy(exception = Exception("유저 조회중 오류가 발생했어요.\n\n${exception.message}"))
                }
            }
        )
    }

    fun login(id: String, password: String) = intent {
        findUserById(id).doWhen(
            onSuccess = { users ->
                if (users.isNotEmpty()) {
                    val user = users.first()
                    val loginResult = user.id == id && user.password == password
                    reduce {
                        state.copy(
                            loaded = true,
                            loginResult = loginResult,
                            exception = null
                        )
                    }
                    if (loginResult) {
                        postSideEffect(MviJoinSideEffect.SetupAutoLogin(user))
                    }
                } else {
                    postSideEffect(BaseMviToastSideEffect.Toast("${id}로 가입된 아이디가 없어요."))
                }
            },
            onFailure = { exception ->
                reduce {
                    state.copy(exception = Exception("유저 조회중 오류가 발생했어요.\n\n${exception.message}"))
                }
            }
        )
    }

    private suspend fun findUserById(id: String) =
        suspendCancellableCoroutine<Result<List<User>>> { continuation ->
            firestoreUsers.whereEqualTo("id", id).get()
                .addOnSuccessListener { querySnapshot ->
                    val foundUsers =
                        querySnapshot.documents.mapNotNull { it.toObject(User::class.java) }
                    continuation.resume(Result.success(foundUsers))
                }
                .addOnFailureListener { exception ->
                    continuation.resume(Result.failure(exception))
                }
        }
}
