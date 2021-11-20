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
import dnd.hackathon.second.healthyhoneytving.activity.main.model.Count
import dnd.hackathon.second.healthyhoneytving.activity.user.model.User

// TODO: 이게 맞는 방법인지는 항상 의문
object DataStore {
    private val users: MutableList<User> = mutableListOf()
    private val counts: MutableList<Count> = mutableListOf()
    private val comments: MutableList<Comment> = mutableListOf()

    var me = User()

    fun updateUsers(users: List<User>) {
        this.users.addAll(users)
    }

    fun updateCounts(counts: List<Count>) {
        this.counts.addAll(counts)
    }

    fun updateCommnets(comments: List<Comment>) {
        this.comments.addAll(comments)
    }

    fun getUsersFromNickname(nickname: String) = users.filter { user -> user.nickname == nickname }
    fun getUsersFromId(id: String) = users.filter { user -> user.id == id }
    fun getFirstUserFromId(id: String) = users.first { user -> user.id == id }

    // fun getCountFromFeedUid(feedUid: Int) = counts.first { count -> count.feedUid == feedUid }
}
