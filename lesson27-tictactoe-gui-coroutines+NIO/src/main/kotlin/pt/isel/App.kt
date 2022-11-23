package pt.isel
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.*
import pt.isel.ttt.Board
import pt.isel.ttt.BoardRun
import pt.isel.ttt.deserializeToBoard
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
            val gameState = remember { GameState(
                scope,
                FileStorageAsync("out", BoardSerializer) { BoardRun() }
            )}

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
    val (newGameDialog, setNewGameDialog) = remember { mutableStateOf(false) }
    if(newGameDialog)
        DialogInput({ setNewGameDialog(false) }) {
            gameState.newGame(it)
            setNewGameDialog(false)
        }
    MenuBar {
        Menu("Game") {
            Item("New", onClick = { setNewGameDialog(true) })
            Item("Quit", onClick = { exitProcess(0) } )
        }
    }
}

object BoardSerializer : StringSerializer<Board> {
    override fun write(obj: Board) = obj.serialize()
    override fun parse(input: String) = input.deserializeToBoard()
}
