/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [Feed.kt] created by Ji Sungbin on 21. 11. 20. 오전 5:48
 *
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.activity.main.model

data class Feed(
    val tags: List<String> = emptyList(),
    val link: String = "",
    val title: String = "",
    val desscription: String,
    val previewImageUrl: String = "",
    val like: Int = 0,
    val hate: Int = 0,
    val ownerUid: String = "",
    val feedUid: Int = 0
)
