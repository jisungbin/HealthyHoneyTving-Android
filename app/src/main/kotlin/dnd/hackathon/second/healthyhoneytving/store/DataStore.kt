/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [DataStore.kt] created by Ji Sungbin on 21. 11. 20. 오전 4:49
 *
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.store

import dnd.hackathon.second.healthyhoneytving.activity.main.model.Comment
import dnd.hackathon.second.healthyhoneytving.activity.main.model.Feed
import dnd.hackathon.second.healthyhoneytving.activity.user.model.User
import dnd.hackathon.second.healthyhoneytving.util.operator.plusAssign
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

// TODO: 이게 맞는 방법인지는 항상 의문
@Suppress("ObjectPropertyName")
object DataStore {
    private val _users: MutableList<User> = mutableListOf()
    private val _feeds = MutableStateFlow(listOf<Feed>())
    private val _comments = MutableStateFlow(listOf<Comment>())

    val feeds = _feeds.asStateFlow()
    var me = User()

    fun updateUser(user: User) {
        this._users.add(user)
    }

    fun updateUsers(users: List<User>) {
        this._users.addAll(users)
    }

    fun updateFeeds(feeds: List<Feed>) {
        this._feeds += feeds
    }

    fun updateCommnets(comments: List<Comment>) {
        this._comments += comments
    }

    fun getUsersFromNickname(nickname: String) = _users.filter { user -> user.nickname == nickname }
    fun getUsersFromId(id: String) = _users.filter { user -> user.id == id }
    fun getFirstUserFromId(id: String) = _users.first { user -> user.id == id }
}
