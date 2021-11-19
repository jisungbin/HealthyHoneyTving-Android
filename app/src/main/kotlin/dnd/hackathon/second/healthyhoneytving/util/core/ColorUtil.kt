/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [ColorUtil.kt] created by Ji Sungbin on 21. 11. 20. 오전 5:49
 *  
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.util.core

object ColorUtil {
    val getRandom get() = (Math.random() * 16777215).toInt() or (0xFF shl 24)
}
