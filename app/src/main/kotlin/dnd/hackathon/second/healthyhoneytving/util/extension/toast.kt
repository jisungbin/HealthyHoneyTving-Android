/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [toast.kt] created by Ji Sungbin on 21. 11. 19. 오후 10:47
 *
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.util.extension

import android.app.Activity
import android.content.Context
import android.widget.Toast

fun Activity.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    runOnUiThread {
        toast(this, message, length)
    }
}

fun toast(context: Context, message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, message, length).show()
}

fun errorToast(context: Context, exception: Exception) {
    Toast.makeText(
        context,
        "실행중에 에러가 발생했어요 \uD83D\uDE22\n\n${exception.message}",
        Toast.LENGTH_LONG
    ).show()
}
