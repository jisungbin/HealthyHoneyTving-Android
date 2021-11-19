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
@JvmSynthetic
internal val jacksonMapper = ObjectMapper()
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
    .registerKotlinModule()

inline fun <reified T> String.toModel(): T = jacksonMapper.readValue(this, T::class.java)
    ?: throw Exception("문자열을 json 모델로 바꾸는데 오류가 발생했어요.\n\n($this)")

fun Any.toJsonString() = jacksonMapper.writeValueAsString(this)
    ?: throw Exception("json 모델을 문자열로 바꾸는데 오류가 발생했어요.\n\n($this)")
