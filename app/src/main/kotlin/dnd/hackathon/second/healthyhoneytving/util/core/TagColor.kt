/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [TagColor.kt] created by Ji Sungbin on 21. 11. 20. 오전 5:50
 *  
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.util.core

import android.content.Context
import dnd.hackathon.second.healthyhoneytving.util.constant.DataConstant

object TagColor {
    private val RandomColor get() = (Math.random() * 16777215).toInt() or (0xFF shl 24)
    private val tagColors: HashMap<String, Int> = hashMapOf()

    operator fun invoke(context: Context, tag: String): Int {
        var color = tagColors[tag]
        if (color == null) {
            val savedColor = DataUtil.read(context, DataConstant.TagColor(tag), null)?.toInt()
            if (savedColor != null) {
                color = savedColor
                tagColors[tag] = color
            } else {
                color = RandomColor
                DataUtil.save(context, DataConstant.TagColor(tag), color.toString())
            }
        }
        return color
    }
}
