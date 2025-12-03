package org.examp.project.test1

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import composemultiplatformdemo.composeapp.generated.resources.Res
import composemultiplatformdemo.composeapp.generated.resources.compose_multiplatform
import org.examp.project.Greeting
import org.jetbrains.compose.resources.painterResource

object Test1Tab : Screen {
    override val key: ScreenKey
        get() = super.key

    @Composable
    override fun Content() = Test1Screen()

}

@Composable
fun Test1Screen() {
    val coroutineTest = remember { CoroutineTest() }
    Column {
        AnimateContentSizeDemo()
        Button(onClick = {
            coroutineTest.shart()
        }){
            Text("开始协程")
        }
    }

}

@Composable
fun demo() {
    var showContent by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .safeContentPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(onClick = { showContent = !showContent }) {
            Text("Click me!")
        }
        AnimatedVisibility(showContent) {
            val greeting = remember { Greeting().greet() }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(painterResource(Res.drawable.compose_multiplatform), null)
                Text("Compose: $greeting")
            }
        }
    }
}

@Composable
fun AnimateContentSizeDemo() {
    var expend by remember { mutableStateOf(false) }
    Column(modifier = Modifier.padding(16.dp)) {
        Text("AnimateContentSizeDemo")
        Spacer(Modifier.height(16.dp))
        Button(onClick = {
            expend = !expend
        }) {
            Text(
                if (expend) {
                    "Shrink"
                } else {
                    "Expend"
                }
            )
        }
        Spacer(Modifier.height(16.dp))
        Box(modifier = Modifier.background(Color.LightGray).animateContentSize()) {
            Text(
                text = "当接触一个新知识时，想要直观体验一下效果的，就会找一些现成的项目里面试试水。\n" +
                        "开始的时候，我就在 官方样例 找了几个项目，试着编译一下，有些项目是编译各种问题，有些项目编译成功了，可是业务逻辑也比较多，不利于理解项目结构。\n" +
                        "后面又试了Kotlin Multiplatform插件的模板，但是只有安卓和iOS。\n" +
                        "在各种各种项目的试水过程中，最终找到了一个我认为对新手最友好的Hello World —— Kotlin Multiplatform Wizard ，可以一键创建好 四端项目，清晰明了。接下来我就讲一下创建运行过程。\n" +
                        "\n" +
                        "作者：droidHZ\n" +
                        "链接：https://juejin.cn/post/7439680667359412260\n" +
                        "来源：稀土掘金\n" +
                        "著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。",
                fontSize = 16.sp,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(16.dp),
                maxLines = if (expend) {
                    Int.MAX_VALUE
                } else {
                    2
                }
            )
        }
    }
}