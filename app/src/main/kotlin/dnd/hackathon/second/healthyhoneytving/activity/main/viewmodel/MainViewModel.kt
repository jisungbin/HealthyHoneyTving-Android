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
import dnd.hackathon.second.healthyhoneytving.activity.main.model.MainType
import dnd.hackathon.second.healthyhoneytving.activity.main.model.MenuType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val api: ApiService) : ViewModel() {
    private val _mainType = MutableStateFlow(MainType.Media)
    val mainType = _mainType.asStateFlow()

    private val _menuType = MutableStateFlow(MenuType.List)
    val menuType = _menuType.asStateFlow()

    private val _selectCategory = MutableStateFlow("")
    val selectCategory = _selectCategory.asStateFlow()

    fun updateMainType(type: String) {
        _mainType.value = type
    }

    fun updateMenuType(type: MenuType) {
        _menuType.value = type
    }

    fun updateSelectCategory(category: String) {
        _selectCategory.value = category
    }
}
