/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [date.kt] created by Ji Sungbin on 21. 11. 20. 오전 9:25
 *
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.util.extension

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toTimeString(): String {
    val date = Date().apply {
        time = this@toTimeString
    }
    return SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.KOREA).format(date)
}
