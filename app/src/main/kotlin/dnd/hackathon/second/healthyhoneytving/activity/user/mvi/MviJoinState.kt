/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [MviJoinState.kt] created by Ji Sungbin on 21. 11. 20. 오전 12:44
 *
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.activity.user.mvi

import dnd.hackathon.second.healthyhoneytving.mvi.BaseMviState

data class MviJoinState(
    override val loaded: Boolean = false,
    override val exception: Exception? = null,
    val loginResult: Boolean = false,
    val registerResult: Boolean = false,
) : BaseMviState
