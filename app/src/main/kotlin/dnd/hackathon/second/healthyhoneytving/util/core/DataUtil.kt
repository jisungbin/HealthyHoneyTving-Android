/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [DataUtil.kt] created by Ji Sungbin on 21. 11. 20. 오전 12:44
 *
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.util.core

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

object DataUtil {
    private const val DEFAULT_KEY = "encrypted_shared_preferences"
    private val masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private fun getEncryptedSharedPreferences(context: Context) =
        EncryptedSharedPreferences.create(
            masterKey,
            DEFAULT_KEY,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    fun save(context: Context, key: String, value: String?) {
        getEncryptedSharedPreferences(context).edit().putString(key, value).apply()
    }

    fun read(context: Context, key: String, _null: String?) =
        getEncryptedSharedPreferences(context).getString(key, _null)
}
