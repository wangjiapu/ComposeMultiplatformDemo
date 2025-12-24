package org.examp.project.presentation.secondtab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey


object Test2Tab : Screen {
    override val key: ScreenKey
        get() = super.key

    @Composable
    override fun Content() = Test2Screen()

}

@Composable
fun Test2Screen() {
    Column(
        modifier = Modifier
            .padding(45.dp)
            .firstBaseLineToTop(2.dp)
            .fillMaxSize()
    ) {
        Button(
            modifier = Modifier.height(45.dp).width(120.dp),
            onClick = {

            }
        ) {
            Text("低级动画")
        }

        Spacer(modifier = Modifier.height(20.dp))
        SnapFlashDemo()
        TransitionDemo()
        BasicSpringAnimationDemo()
    }
}
