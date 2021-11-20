/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [Web.kt] created by Ji Sungbin on 21. 11. 19. 오후 10:47
 *
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.util.core

import android.content.Context
import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import dnd.hackathon.second.healthyhoneytving.R
import dnd.hackathon.second.healthyhoneytving.util.extension.toast

object Web {
    fun open(context: Context, link: String) {
        try {
            val builder = CustomTabsIntent.Builder()
            builder.build()
                .intent
                .addCategory(Intent.CATEGORY_BROWSABLE)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            val customTabIntent = builder.build()
            customTabIntent.launchUrl(context, link.toUri())
        } catch (exception: Exception) {
            exception.printStackTrace()
            toast(context, context.getString(R.string.util_web_toast_non_install_browser))
        }
    }

    fun parseYoutubeThumbnailAddress(youtubeAddress: String) = try {
        "https://img.youtube.com/vi/${youtubeAddress.split("watch?v=")[1]}/0.jpg"
    } catch (ignored: Exception) {
        ""
    }
}
