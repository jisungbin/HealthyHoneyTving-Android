/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [List.kt] created by Ji Sungbin on 21. 11. 20. 오전 7:39
 *
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.util.operator

operator fun <T> List<T>.times(time: Int): List<T> {
    val list = mutableListOf<T>()
    repeat(time) {
        list += this
    }
    return list
}
