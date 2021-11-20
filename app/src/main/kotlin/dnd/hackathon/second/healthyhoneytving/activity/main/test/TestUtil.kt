/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [TestUtil.kt] created by Ji Sungbin on 21. 11. 20. 오전 7:19
 *  
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.activity.main.test

@Suppress("MemberVisibilityCanBePrivate")
object TestUtil {
    fun generateRandomString(length: Int) = List(length) { ('가'..'힣').random() }.joinToString("")
}
