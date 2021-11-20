/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [SnsType.kt] created by Ji Sungbin on 21. 11. 20. 오전 8:19
 *  
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.activity.main.model

object SnsType {
    const val Youtube = "YOUTUBE"
    const val TikTok = "TIKTOK"
    const val Instagrem = "INSTAGRAM"
    const val Etc = "ETC"

    fun asList() = listOf(Youtube, TikTok, Instagrem, Etc)
}

fun String.toKorean() = when (this) {
    SnsType.Youtube -> "유튜브"
    SnsType.TikTok -> "틱톡"
    SnsType.Instagrem -> "인스타그램"
    SnsType.Etc -> "기타"
    else -> throw IndexOutOfBoundsException("알 수 없는 SNS: $this")
}
