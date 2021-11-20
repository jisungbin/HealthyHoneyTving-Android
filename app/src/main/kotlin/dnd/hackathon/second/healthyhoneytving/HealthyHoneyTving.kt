/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [HealthyHoneyTving.kt] created by Ji Sungbin on 21. 11. 19. 오후 10:56
 *
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import dnd.hackathon.second.healthyhoneytving.util.core.NotificationUtil
import io.github.jisungbin.logeukes.Logeukes

@HiltAndroidApp
class HealthyHoneyTving : Application() {
    override fun onCreate() {
        super.onCreate()

        NotificationUtil.createChannel(
            context = applicationContext,
            name = getString(R.string.app_name),
            description = getString(R.string.app_name)
        )

        /*Erratum.setup(
            application = this,
            registerExceptionActivityIntent = { _, throwable, lastActivity ->
                Intent(lastActivity, ExceptionActivity::class.java).apply {
                    putExtra(ErratumExceptionActivity.EXTRA_EXCEPTION_STRING, throwable.toString())
                    putExtra(
                        ErratumExceptionActivity.EXTRA_LAST_ACTIVITY_INTENT,
                        lastActivity.intent
                    )
                }
            }
        )*/

        if (BuildConfig.DEBUG) {
            Logeukes.setup()
        }
    }
}
