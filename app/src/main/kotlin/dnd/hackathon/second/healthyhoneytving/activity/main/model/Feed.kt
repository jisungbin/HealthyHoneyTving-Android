/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [Feed.kt] created by Ji Sungbin on 21. 11. 20. 오전 5:48
 *
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.activity.main.model


// 링크 정보 {링크, 하위 태그 배열, 유저 UID, 제목, 좋아요, 싫어요, 링크UID}
data class Feed(
    val content: String,
    val tags: List<String>,

)
