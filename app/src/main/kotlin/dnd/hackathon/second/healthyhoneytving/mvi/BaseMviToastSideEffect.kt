/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [BaseMviToastSideEffect.kt] created by Ji Sungbin on 21. 11. 20. 오전 12:40
 *
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.mvi

interface BaseMviToastSideEffect {
    data class Toast(val message: String) : BaseMviToastSideEffect
}
