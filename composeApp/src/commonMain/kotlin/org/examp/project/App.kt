package org.examp.project

import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.ui.tooling.preview.Preview

import org.examp.project.presentation.firsttab.Test1Tab
import org.examp.project.presentation.secondtab.Test2Tab
import org.examp.project.presentation.thirdtab.Test3Tab
import org.examp.project.presentation.fourthtab.Test4Tab

@Composable
@Preview
fun App() {
    MaterialTheme {
        entry()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun entry() {
    Navigator(screen = bottomTabConfigs.first().screen) { navigator ->
        Scaffold(
            topBar = {
                TopAppBar(
                    modifier = Modifier.height(45.dp),
                    title = { Text("CMP 示例") },
                    colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            },
            bottomBar = {
                NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                    // 遍历 TabConfig，生成底部 Tab
                    bottomTabConfigs.forEach { tabConfig ->
                        NavigationBarItem(
                            // 选中状态：当前 Screen 的 key 与 TabConfig 的 screen key 匹配
                            selected = navigator.lastItem.key == tabConfig.screen.key,
                            // 点击切换页面（替换导航栈，避免重复创建）
                            onClick = { navigator.replaceAll(tabConfig.screen) },
                            label = {
                                Text(
                                    text = tabConfig.title,
                                    style = MaterialTheme.typography.labelSmall,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            // 是否始终显示标签（与旧版 TabOptions 的效果一致）
                            alwaysShowLabel = true,
                            icon = {
                                Text(text = tabConfig.index.toString())
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            // 显示当前选中的页面
            CurrentScreen()
        }
    }
}


data class TabConfig(
    val index: Int,
    val title: String,
    val screen: Screen,
    val badgeCount: Int = 0 // 新增：未读角标配置（旧版 TabOptions 无，可扩展）
)

val bottomTabConfigs = listOf(
    TabConfig(
        index = 0, // 第 1 个 Tab
        title = "测试",
        screen = Test1Tab,
        badgeCount = 3 // 未读角标：3
    ),
    TabConfig(
        index = 1, // 第 2 个 Tab
        title = "测试",
        screen = Test2Tab,
        // badgeCount 默认为 0，不显示角标
    ),
    TabConfig(
        index = 2, // 第 3 个 Tab
        title = "测试",
        screen = Test3Tab,
        badgeCount = 99 // 未读角标：99+
    ),
    TabConfig(
        index = 3, // 第 4 个 Tab
        title = "测试",
        screen = Test4Tab,
    )
).sortedBy { it.index }