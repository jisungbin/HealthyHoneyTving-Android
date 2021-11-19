/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [StateFlow.kt] created by Ji Sungbin on 21. 11. 19. 오후 10:47
 *
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.util.operator

import kotlinx.coroutines.flow.MutableStateFlow

operator fun <T> MutableStateFlow<List<T>>.plusAssign(item: T) {
    this.value = this.value + listOf(item)
}

operator fun <T> MutableStateFlow<List<T>>.minusAssign(item: T) {
    this.value = this.value - listOf(item)
}
