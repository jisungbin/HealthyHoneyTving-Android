/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [MviFeedUploadState.kt] created by Ji Sungbin on 21. 11. 20. 오전 11:20
 *  
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.activity.feed.mvi

import dnd.hackathon.second.healthyhoneytving.mvi.BaseMviState

data class MviFeedUploadState(
    override val loaded: Boolean = false,
    override val exception: Exception? = null,
    val uploadResult: Boolean = false
) : BaseMviState
