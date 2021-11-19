/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [BaseMviSideEffect.kt] created by Ji Sungbin on 21. 11. 20. 오전 12:40
 *
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.mvi

interface BaseMviSideEffect {
    data class Toast(val message: String) : BaseMviSideEffect
}
