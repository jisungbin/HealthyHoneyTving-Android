/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [TestUtil.kt] created by Ji Sungbin on 21. 11. 20. 오전 7:19
 *  
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.activity.main.test

import dnd.hackathon.second.healthyhoneytving.activity.main.model.Comment
import dnd.hackathon.second.healthyhoneytving.store.DataStore
import kotlin.random.Random

@Suppress("MemberVisibilityCanBePrivate")
object TestUtil {
    val Comments = List(10) {
        Comment(
            feedUid = Random.nextInt(),
            ownerUid = DataStore.me.id,
            content = generateRandomString(10),
        )
    }

    object Category {
        val Healthy = listOf("식이요법", "스트레칭", "헬스")
        val Media = listOf("게임", "개그", "공포", "고양이")
    }

    fun generateRandomString(length: Int) = List(length) { ('가'..'힣').random() }.joinToString("")
}
