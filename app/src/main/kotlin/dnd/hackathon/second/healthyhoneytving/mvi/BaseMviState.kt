/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [MviBaseState.kt] created by Ji Sungbin on 21. 11. 20. 오전 12:39
 *
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.mvi

interface BaseMviState {
    val loaded: Boolean
    val exception: Exception?

    fun isException() = exception != null
}
