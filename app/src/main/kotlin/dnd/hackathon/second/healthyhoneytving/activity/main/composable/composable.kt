/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [composable.kt] created by Ji Sungbin on 21. 11. 20. 오전 6:13
 *  
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.activity.main.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dnd.hackathon.second.healthyhoneytving.R
import dnd.hackathon.second.healthyhoneytving.activity.main.model.MainType
import dnd.hackathon.second.healthyhoneytving.activity.main.model.MenuType
import dnd.hackathon.second.healthyhoneytving.activity.main.test.TestUtil
import dnd.hackathon.second.healthyhoneytving.activity.main.viewmodel.MainViewModel
import dnd.hackathon.second.healthyhoneytving.util.extension.composableActivityViewModel
import dnd.hackathon.second.healthyhoneytving.util.extension.noRippleClickable
import dnd.hackathon.second.healthyhoneytving.util.operator.times

@Composable
fun Categorie() {
    val vm: MainViewModel = composableActivityViewModel()
    val mainTypeState by vm.mainType.collectAsState()
    val selectCategory by vm.selectCategory.collectAsState()
    val categories =
        (if (mainTypeState == MainType.Media) TestUtil.Category.Media else TestUtil.Category.Healthy) * 3

    if (selectCategory == "") {
        vm.updateSelectCategory(categories.first())
    }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 5.dp)
    ) {
        itemsIndexed(categories) { index, category ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .noRippleClickable(onClick = {
                            vm.updateSelectCategory(category)
                        }),
                    text = category,
                    style = TextStyle(fontSize = 20.sp),
                    color = calcColorAnimationState(
                        input = category,
                        target = categories
                    ).value,
                    textAlign = TextAlign.Center
                )
                if (index < categories.lastIndex) {
                    VerticalDivider(height = 20.dp, thickness = 1.dp)
                }
            }
        }
    }
}

@Composable
fun Menu() {
    val vm: MainViewModel = composableActivityViewModel()
    val menuTypeState by vm.menuType.collectAsState()

    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.noRippleClickable(onClick = {}), // TODO
            horizontalArrangement = Arrangement.spacedBy(
                space = 2.dp,
                alignment = Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "ALL")
            Icon(
                painter = painterResource(R.drawable.ic_baseline_arrow_drop_down_24),
                contentDescription = null
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(
                space = 10.dp,
                alignment = Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.noRippleClickable(onClick = { vm.updateMenuType(MenuType.Grid) }),
                painter = painterResource(R.drawable.ic_round_menu_grid_24),
                contentDescription = null,
                tint = calcColorAnimationState(
                    input = MenuType.Grid,
                    target = menuTypeState
                ).value
            )
            Icon(
                modifier = Modifier.noRippleClickable(onClick = { vm.updateMenuType(MenuType.List) }),
                painter = painterResource(R.drawable.ic_round_menu_list_24),
                contentDescription = null,
                tint = calcColorAnimationState(
                    input = MenuType.List,
                    target = menuTypeState
                ).value
            )
        }
    }
}
