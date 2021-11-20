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
import dnd.hackathon.second.healthyhoneytving.activity.main.model.Feed
import dnd.hackathon.second.healthyhoneytving.store.DataStore
import java.util.Date
import kotlin.random.Random

@Suppress("MemberVisibilityCanBePrivate")
object TestUtil {
    val Feeds = List(50) {
        Feed(
            tags = listOf("토이스토리", "버즈", "갤럭시버즈"),
            link = "https://namu.wiki/w/%EB%B2%84%EC%A6%88%20%EB%9D%BC%EC%9D%B4%ED%8A%B8%EC%9D%B4%EC%96%B4",
            title = generateRandomString(10),
            description = "토이스토리 버즈는 갤럭시 버즈다.",
            previewImageUrl = "https://ww.namu.la/s/d59dae12dc57f40708d7d769a582aa97c07fcb3a6690040f6c87e8a623146f44dc1eedcfb58b49fb1fd8730b390b4a24aa06d3c86398b2f6abc3e4086a96255692a314afc716371e1f13dfce6a4a96b53ccb5dee53f68430e788a3e719adeb5c",
            createdAt = Date().time,
            ownerUid = DataStore.me.id,
            feedUid = Random.nextInt()
        )
    }

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
