/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [MainActivity.kt] created by Ji Sungbin on 21. 11. 20. 오전 4:44
 *
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.activity.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import dnd.hackathon.second.healthyhoneytving.activity.main.composable.Categorie
import dnd.hackathon.second.healthyhoneytving.activity.main.composable.Divider
import dnd.hackathon.second.healthyhoneytving.activity.main.composable.Menu
import dnd.hackathon.second.healthyhoneytving.activity.main.composable.Topbar
import dnd.hackathon.second.healthyhoneytving.activity.main.composable.feed.LazyFeed
import dnd.hackathon.second.healthyhoneytving.activity.main.model.MenuType
import dnd.hackathon.second.healthyhoneytving.theme.MaterialTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Content()
            }
        }
    }

    @Composable
    private fun Content() {
        var menuType by remember { mutableStateOf(MenuType.List) }

        Column(modifier = Modifier.fillMaxSize()) {
            Topbar(modifier = Modifier)
            Divider()
            Categorie()
            Divider()
            Menu()
            Divider(thickness = 2.dp)
            LazyFeed()
        }
    }
}
