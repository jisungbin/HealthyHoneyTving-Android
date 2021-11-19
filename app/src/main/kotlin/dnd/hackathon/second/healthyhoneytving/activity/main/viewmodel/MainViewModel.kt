/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [MainViewModel.kt] created by Ji Sungbin on 21. 11. 20. 오전 5:59
 *
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.activity.main.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dnd.hackathon.second.healthyhoneytving.activity.main.business.ApiService
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val api: ApiService) : ViewModel()
