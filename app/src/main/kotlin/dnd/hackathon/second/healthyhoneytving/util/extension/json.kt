/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [json.kt] created by Ji Sungbin on 21. 11. 19. 오후 10:46
 *
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.util.extension

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

@PublishedApi
internal val mapper by lazy {
    ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .registerKotlinModule()
}

inline fun <reified T> String.toModel(): T = mapper.readValue(this, T::class.java)
    ?: throw Exception("문자열을 json 모델로 바꾸는데 오류가 발생했어요.\n\n($this)")

fun Any.toJsonString() = mapper.writeValueAsString(this)
    ?: throw Exception("json 모델을 문자열로 바꾸는데 오류가 발생했어요.\n\n($this)")
