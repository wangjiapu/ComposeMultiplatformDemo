package org.examp.project.test2

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import composemultiplatformdemo.composeapp.generated.resources.Res
import composemultiplatformdemo.composeapp.generated.resources.aa
import org.jetbrains.compose.resources.painterResource

@Composable
fun SnapFlashDemo() {
    // 1. 状态：控制动画是否激活
    var isFlashing by remember { mutableStateOf(false) }

    // 2. 动画：控制透明度
    val alpha by animateFloatAsState(
        targetValue = if (isFlashing) 0.0f else 1.0f,
        animationSpec = tween(durationMillis = 100), // 极短的动画时间
        finishedListener = {
            // 3. 动画结束后，立即重置状态，为下一次闪烁做准备
            if (it == 0.0f) { // 如果是从可见到不可见的动画结束了
                isFlashing = false
            }
        },
        label = "flashAnimation"
    )

    Box(
        modifier = Modifier
            .size(100.dp)
            .background(Color.Red.copy(alpha = alpha))
            .clickable {
                // 4. 点击时触发闪烁
                isFlashing = true
            }
    )

    // 5. (可选) 自动重复闪烁
    /*
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000) // 每隔1秒闪烁一次
            isFlashing = true
        }
    }
    */
}


@Composable
fun BasicSpringAnimationDemo() {
    // 1. 定义一个状态，用于控制动画的目标值
    var isExpanded by remember { mutableStateOf(false) }

    // 2. 使用 animateDpAsState 创建弹簧动画，用于控制尺寸变化
    val targetSize = if (isExpanded) 200.dp else 100.dp
    val animatedSize by animateDpAsState(
        targetValue = targetSize,
        animationSpec = spring(
            dampingRatio = 0.2f, // 阻尼系数，越小弹性越强
            stiffness = 200f     // 刚度，越大动画越快
        ),
        label = "sizeAnimation"
    )

    // 3. 使用 animateColorAsState 创建弹簧动画，用于控制颜色变化
    val targetColor = if (isExpanded) Color.Blue else Color.Red
    val animatedColor by animateColorAsState(
        targetValue = targetColor,
        animationSpec = spring(
            dampingRatio = 0.5f,
            stiffness = 100f
        ),
        label = "colorAnimation"
    )

    Column(
        modifier = Modifier
            .clickable { isExpanded = !isExpanded }
            .size(300.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 4. 将动画值应用到 UI 上
        Box(
            modifier = Modifier
                .size(animatedSize)
                .background(animatedColor)
        )
        Text(text = if (isExpanded) "点击收缩" else "点击展开")
    }
}

sealed class SwitchState {
    object OPEN : SwitchState()
    object CLOSE : SwitchState()
}

@Composable
fun TransitionDemo() {

    var selectedState: SwitchState by remember { mutableStateOf(SwitchState.CLOSE) }
    val transition = updateTransition(selectedState, label = "switch_transition")
    val selectBarPadding by transition.animateDp(
        transitionSpec = { tween(1000) },
        label = ""
    ) {
        when (it) {
            SwitchState.OPEN -> 0.dp
            SwitchState.CLOSE -> 40.dp
        }
    }

    val textAlpha by transition.animateFloat(
        transitionSpec = { tween(1000) },
        label = ""
    ) {
        when (it) {
            SwitchState.OPEN -> 0f
            SwitchState.CLOSE -> 1f
        }
    }

    Box(
        modifier = Modifier
            .size(150.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                selectedState = if (selectedState == SwitchState.CLOSE) {
                    SwitchState.OPEN
                } else {
                    SwitchState.CLOSE
                }
            }
    ) {

        Image(
            painter = painterResource(Res.drawable.aa),
            contentDescription = "aa",
            contentScale = ContentScale.FillBounds
        )
        Text(
            text = "点我",
            fontSize = 30.sp,
            fontWeight = FontWeight.W900,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.Center)
                .alpha(textAlpha)
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter).fillMaxWidth()
                .height(40.dp)
                .padding(top = selectBarPadding)
                .background(Color(0xFF5FB878))
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.Center)
                    .alpha(1 - textAlpha)
            ) {
                Text(
                    text = "已选择",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W900,
                    color = Color.White
                )

            }
        }
    }

}

