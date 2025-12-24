package org.examp.project.presentation.fourthtab

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey

object Test4Tab : Screen {
    override val key: ScreenKey
        get() = super.key

    @Composable
    override fun Content() = Test4Screen()

}

@Composable
fun Test4Screen() {
    Text("Test4Screen")
}