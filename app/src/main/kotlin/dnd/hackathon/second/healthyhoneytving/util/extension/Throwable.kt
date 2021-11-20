/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [Throwable.kt] created by Ji Sungbin on 21. 11. 19. 오후 10:47
 *
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.util.extension

fun Throwable.toException(cause: String = "") =
    Exception("${if (cause != "") "$cause\n\n" else ""}$this")
