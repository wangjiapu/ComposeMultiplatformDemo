package org.examp.project.test3

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey

object Test3Tab : Screen {
    override val key: ScreenKey
        get() = super.key

    @Composable
    override fun Content() = Test3Screen()

}

@Composable
fun Test3Screen() {
    Text("Test3Screen")
}