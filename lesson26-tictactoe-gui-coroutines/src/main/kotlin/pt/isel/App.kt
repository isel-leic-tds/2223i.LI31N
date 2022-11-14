package pt.isel
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.*
import kotlin.system.exitProcess

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "TicTacToe",
        state = WindowState(
            position = WindowPosition(Alignment.Center),
            size = DpSize.Unspecified
        )
    ) {
        MaterialTheme {
            val scope = rememberCoroutineScope()
            val gameState = remember { GameState(scope) }

            GameMenu(gameState)
            Column {
                StopWatchView(gameState.watch.formattedTime )
                GameView(gameState)
            }
        }
    }
}

@Composable
fun FrameWindowScope.GameMenu(gameState: GameState) {
    MenuBar {
        Menu("Game") {
            Item("New", onClick = gameState::newGame)
            Item("Quit", onClick = { exitProcess(0) } )
        }
    }
}
