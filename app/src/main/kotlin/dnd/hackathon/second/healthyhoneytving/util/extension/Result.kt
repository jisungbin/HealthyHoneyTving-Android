/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [Result.kt] created by Ji Sungbin on 21. 11. 19. 오후 10:46
 *
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.util.extension

inline fun <T> Result<T>.doWhen(
    onSuccess: (result: T) -> Unit,
    onFailure: (throwable: Throwable) -> Unit
) {
    if (isSuccess) {
        onSuccess(getOrNull()!!)
    } else {
        onFailure(exceptionOrNull()!!)
    }
}
