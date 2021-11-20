/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [FirebaseModules.kt] created by Ji Sungbin on 21. 11. 20. 오전 5:00
 *  
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.di.module

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.ViewModelScoped
import dnd.hackathon.second.healthyhoneytving.di.qualifier.FirestoreComment
import dnd.hackathon.second.healthyhoneytving.di.qualifier.FirestoreException
import dnd.hackathon.second.healthyhoneytving.di.qualifier.FirestoreFeed
import dnd.hackathon.second.healthyhoneytving.di.qualifier.FirestoreUser

@Module
@InstallIn(ViewModelComponent::class)
object FirebaseModuleViewModel {
    @Provides
    @FirestoreUser
    @ViewModelScoped
    fun provideFirestoreUser(): CollectionReference = Firebase.firestore.collection("users")

    @Provides
    @FirestoreFeed
    @ViewModelScoped
    fun provideFirestoreFeed(): CollectionReference = Firebase.firestore.collection("feeds")

    @Provides
    @FirestoreComment
    @ViewModelScoped
    fun provideFirestoreComment(): CollectionReference = Firebase.firestore.collection("comments")
}

@Module
@InstallIn(ActivityComponent::class)
object FirebaseModuleActivity {
    @Provides
    @FirestoreException
    @ActivityScoped
    fun provideFirestoreException(): CollectionReference =
        Firebase.firestore.collection("exceptions")
}
