/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [StateFlow.kt] created by Ji Sungbin on 21. 11. 19. 오후 10:46
 *  
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.util.extension

import kotlinx.coroutines.flow.MutableStateFlow

fun <T> MutableStateFlow<List<T>>.clear() {
    value = emptyList()
}

// predicate가 noninline이라 inline 필요 없음
fun <T> MutableStateFlow<List<T>>.removeAll(predicate: (T) -> Boolean) {
    val value = this.value.toMutableList()
    value.removeAll(predicate)
    notifyDataSetChanged(value)
}

// predicate가 noninline이라 inline 필요 없음
fun <T> MutableStateFlow<List<T>>.removeIf(predicate: (T) -> Boolean) {
    val value = this.value.toMutableList()
    value.removeIf(predicate)
    notifyDataSetChanged(value)
}

inline fun <T> MutableStateFlow<List<T>>.edit(action: MutableList<T>.() -> Unit) {
    val items = value.toMutableList()
    action(items)
    notifyDataSetChanged(items)
}

@PublishedApi
internal fun <T> MutableStateFlow<List<T>>.notifyDataSetChanged(value: List<T>) {
    this.value = value
}
