/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [UserStore.kt] created by Ji Sungbin on 21. 11. 20. 오전 5:47
 *
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [UserStore.kt] created by Ji Sungbin on 21. 11. 20. 오전 4:49
 *
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.activity.user.viewmodel

import dnd.hackathon.second.healthyhoneytving.activity.user.model.User

// TODO: 이게 맞는 방법인지는 항상 의문
object UserStore {
    @Suppress("ObjectPropertyName")
    private val _users: MutableList<User> = mutableListOf()
    val users get(): List<User> = _users

    var me = User()

    fun updateUsers(users: List<User>) {
        _users.addAll(users)
    }

    fun getFromNickname(nickname: String) = users.filter { user -> user.nickname == nickname }
    fun getFromId(id: String) = users.filter { user -> user.id == id }
}
