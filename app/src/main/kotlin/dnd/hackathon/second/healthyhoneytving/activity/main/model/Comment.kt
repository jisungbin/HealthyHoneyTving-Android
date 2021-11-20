/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [Comment.kt] created by Ji Sungbin on 21. 11. 20. 오전 9:22
 *
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.activity.main.model

import java.util.Date
import kotlin.random.Random

data class Comment(
    val feedUid: Int = 0,
    val ownerUid: String = "",
    val commentUid: Int = Random.nextInt(),
    val content: String = "",
    val createdAt: Long = Date().time
)
