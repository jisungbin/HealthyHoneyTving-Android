/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [composable.kt] created by Ji Sungbin on 21. 11. 20. 오전 6:13
 *  
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.activity.main.composable

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dnd.hackathon.second.healthyhoneytving.activity.main.model.MainType
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

    @Composable
    fun calcColorAnimationState(category: String) =
        animateColorAsState(if (category == selectCategory) Color.Black else Color.LightGray)

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
                    color = calcColorAnimationState(category).value,
                    textAlign = TextAlign.Center
                )
                if (index < categories.lastIndex) {
                    VerticalDivider(height = 20.dp)
                }
            }
        }
    }
}

@Composable
fun Menu() {
}

@Composable
fun HorizontalDivider(width: Dp = Dp.Unspecified, thickness: Dp = 2.dp) {
    var modifier: Modifier = Modifier
    modifier = if (width != Dp.Unspecified) {
        modifier.width(width)
    } else {
        modifier.fillMaxWidth()
    }
    Box(
        modifier = modifier
            // .runIf(width != Dp.Unspecified) { this.width(width) }
            .height(thickness)
            .background(color = Color.LightGray),
    )
}

@Composable
fun VerticalDivider(height: Dp = Dp.Unspecified, thickness: Dp = 2.dp) {
    Box(
        modifier = Modifier
            .apply { if (height != Dp.Unspecified) this.height(height) else this.fillMaxHeight() }
            .width(thickness)
            .background(color = Color.LightGray),
    )
}
