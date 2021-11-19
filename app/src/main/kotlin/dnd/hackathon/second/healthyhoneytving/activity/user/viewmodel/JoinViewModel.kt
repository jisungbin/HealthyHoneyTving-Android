/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [UserViewModel.kt] created by Ji Sungbin on 21. 11. 20. 오전 12:45
 *  
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.activity.user.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.CollectionReference
import dagger.hilt.android.lifecycle.HiltViewModel
import dnd.hackathon.second.healthyhoneytving.activity.user.model.User
import dnd.hackathon.second.healthyhoneytving.activity.user.mvi.MviJoinSideEffect
import dnd.hackathon.second.healthyhoneytving.activity.user.mvi.MviJoinState
import dnd.hackathon.second.healthyhoneytving.di.qualifier.FirestoreUser
import dnd.hackathon.second.healthyhoneytving.mvi.BaseMviSideEffect
import dnd.hackathon.second.healthyhoneytving.util.extension.doWhen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject
import kotlin.coroutines.resume

@HiltViewModel
class JoinViewModel @Inject constructor(
    @FirestoreUser private val firebaseUser: CollectionReference
) : ViewModel(), ContainerHost<MviJoinState, BaseMviSideEffect> {

    override val container = container<MviJoinState, BaseMviSideEffect>(MviJoinState())

    @OptIn(ExperimentalCoroutinesApi::class)
    fun register(user: User) = intent {
        findUserById(user.id).doWhen(
            onSuccess = { foundUsers ->
                if (foundUsers.isEmpty()) {
                    firebaseUser.document(user.id).set(user)
                        .addOnSuccessListener {
                            viewModelScope.launch {
                                reduce {
                                    state.copy(
                                        loaded = true,
                                        registerResult = true,
                                        exception = null
                                    )
                                }
                                postSideEffect(BaseMviSideEffect.Toast("가입이 완료되었어요."))
                            }
                        }.addOnFailureListener { exception ->
                            viewModelScope.launch {
                                reduce {
                                    state.copy(exception = exception)
                                }
                            }
                        }
                } else {
                    postSideEffect(BaseMviSideEffect.Toast("이미 ${user.id}로 가입된 아이디가 있어요."))
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
                        postSideEffect(BaseMviSideEffect.Toast("환영합니다! \uD83E\uDD73"))
                        postSideEffect(MviJoinSideEffect.SetupAutoLogin(user))
                    }
                } else {
                    postSideEffect(BaseMviSideEffect.Toast("${id}로 가입된 아이디가 없어요."))
                }
            },
            onFailure = { exception ->
                reduce {
                    state.copy(exception = Exception("유저 조회중 오류가 발생했어요.\n\n${exception.message}"))
                }
            }
        )
    }

    suspend fun findUserByNickname(nickname: String) =
        suspendCancellableCoroutine<Result<List<User>>> { continuation ->
            firebaseUser.whereEqualTo("nickname", nickname).get()
                .addOnSuccessListener { querySnapshot ->
                    val foundUsers =
                        querySnapshot.documents.mapNotNull { it.toObject(User::class.java) }
                    continuation.resume(Result.success(foundUsers))
                }
                .addOnFailureListener { exception ->
                    continuation.resume(Result.failure(exception))
                }
        }

    suspend fun findUserById(id: String) =
        suspendCancellableCoroutine<Result<List<User>>> { continuation ->
            firebaseUser.whereEqualTo("id", id).get()
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
