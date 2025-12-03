package org.examp.project.test2

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp


// eg1:
fun Modifier.firstBaseLineToTop(firstBaseLineToTop: Dp) =
    Modifier.layout { measurable, constraints ->
        // 1. 测量子元素
        //    这里我们使用父布局传来的原始约束
        val placeable: Placeable = measurable.measure(constraints)

        // 2. 决定我们自己的大小
        //    我们选择填满父布局给的最大空间
        val width = constraints.maxWidth
        val height = constraints.maxHeight

        // 3. 返回布局结果，并定义放置逻辑
        layout(width, height) {
            // 计算居中位置
            val x = (width - placeable.width) / 2
            val y = (height - placeable.height) / 2

            // 放置子元素
            placeable.placeRelative(x, y)
        }
    }

// eg2
fun Modifier.pupu(ss: Dp) = this.then(PupuLayout(ss))
data class PupuLayout(val param: Dp) : LayoutModifier {
    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {

        return layout(width = 1, height = 2) {

        }
    }
}

// LayoutComposable
@Composable
fun CustomLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(modifier = modifier, content = content) { measurables, constraints ->
        val placeable = measurables.map {
            it.measure(constraints)
        }
        layout(constraints.maxWidth, constraints.maxHeight) {

        }
    }
}


