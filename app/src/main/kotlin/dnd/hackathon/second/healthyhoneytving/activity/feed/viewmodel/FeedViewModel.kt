/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [FeedViewModel.kt] created by Ji Sungbin on 21. 11. 20. 오전 11:16
 *  
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.activity.feed.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.CollectionReference
import dagger.hilt.android.lifecycle.HiltViewModel
import dnd.hackathon.second.healthyhoneytving.activity.feed.mvi.MviFeedUploadState
import dnd.hackathon.second.healthyhoneytving.activity.main.model.Comment
import dnd.hackathon.second.healthyhoneytving.activity.main.model.Feed
import dnd.hackathon.second.healthyhoneytving.di.qualifier.FirestoreComment
import dnd.hackathon.second.healthyhoneytving.di.qualifier.FirestoreFeed
import dnd.hackathon.second.healthyhoneytving.mvi.BaseMviSideEffect
import dnd.hackathon.second.healthyhoneytving.store.DataStore
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject
import kotlin.coroutines.resume

@HiltViewModel
class FeedViewModel @Inject constructor(
    @FirestoreFeed private val firestoreFeed: CollectionReference,
    @FirestoreComment private val firestoreComment: CollectionReference
) : ViewModel(), ContainerHost<MviFeedUploadState, BaseMviSideEffect> {

    override val container = container<MviFeedUploadState, BaseMviSideEffect>(MviFeedUploadState())

    fun uploadFeed(feed: Feed) = intent {
        firestoreFeed.document(feed.feedUid.toString())
            .set(feed)
            .addOnSuccessListener {
                viewModelScope.launch {
                    reduce {
                        state.copy(
                            loaded = true,
                            uploadResult = true,
                            exception = null
                        )
                    }
                    DataStore.updateFeed(feed)
                }
            }
            .addOnFailureListener { exception ->
                viewModelScope.launch {
                    reduce {
                        state.copy(exception = exception)
                    }
                }
            }
    }

    fun uploadComment(feed: Feed, comment: Comment) = intent {
        firestoreFeed.document(feed.feedUid.toString())
            .set(comment)
            .addOnSuccessListener {
                viewModelScope.launch {
                    reduce {
                        state.copy(
                            loaded = true,
                            uploadResult = true,
                            exception = null
                        )
                    }
                    DataStore.updateCommnet(comment)
                }
            }
            .addOnFailureListener { exception ->
                viewModelScope.launch {
                    reduce {
                        state.copy(exception = exception)
                    }
                }
            }
    }

    suspend fun loadAllFeeds() = suspendCancellableCoroutine<Result<Unit>> { continuation ->
        firestoreFeed.get()
            .addOnSuccessListener { querySnapshot ->
                val feeds = querySnapshot.documents.mapNotNull { it.toObject(Feed::class.java) }
                DataStore.updateFeeds(feeds)
                continuation.resume(Result.success(Unit))
            }
            .addOnFailureListener { exception ->
                continuation.resume(Result.failure(exception))
            }
    }

    suspend fun loadAllComments() = suspendCancellableCoroutine<Result<Unit>> { continuation ->
        firestoreFeed.get()
            .addOnSuccessListener { querySnapshot ->
                val comments =
                    querySnapshot.documents.mapNotNull { it.toObject(Comment::class.java) }
                DataStore.updateCommnets(comments)
                continuation.resume(Result.success(Unit))
            }
            .addOnFailureListener { exception ->
                continuation.resume(Result.failure(exception))
            }
    }
}
