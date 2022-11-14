package pt.isel
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.window.*
import pt.isel.ttt.*
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
            val (board, setBoard) = remember { mutableStateOf<Board>(BoardRun()) }
            GameMenu(board, setBoard)
            GameView(board, setBoard)
        }
    }
}

@Composable
fun FrameWindowScope.GameMenu(board: Board, updateBoard: (Board) -> Unit) {
    MenuBar {
        Menu("Game") {
            Item("New", onClick = { updateBoard(BoardRun()) } )
            Item("Quit", onClick = { exitProcess(0) } )
        }
    }
}

@Composable
fun GameView(board: Board, updateBoard: (Board) -> Unit) {
        val dialogMessage = remember { mutableStateOf("") }
        val dialogVisible = remember { mutableStateOf(false) }
        BoardView(board) { pos ->
            if(board is BoardRun) {
                val newBoard = board.play(pos, board.player.turn())
                updateBoard(newBoard)
            } else {
                dialogVisible.value = true
                dialogMessage.value = when(board) {
                    is BoardDraw -> "Game finished with a draw!"
                    is BoardWin -> "Player ${board.winner} wan the game!"
                    else -> "Error unknown Game status!"
                }
            }
        }
        DialogMessage(dialogMessage.value, dialogVisible.value) {
            dialogVisible.value = false
        }
}

@Composable
fun DialogMessage(msg: String, visible: Boolean, action: () -> Unit) {
    if(visible) {
         Dialog(
            onCloseRequest = action,
            title = "Message",
            state = DialogState(height = Dp.Unspecified, width = 350.dp)
        ) {
            Text(msg)
        }
    }
}

@Composable
fun BoardView(board: Board, action: (Position) -> Unit) {
    Column {
        repeat(BOARD_SIZE) { lin ->
            Row {
                repeat(BOARD_SIZE) { col ->
                    val mov = board.moves.find { it.pos == Position(lin, col) }
                    Cell(lin, col, mov?.player, action)
                }
            }
        }
    }
}

@Composable
fun Cell(lin: Int, col: Int, player: Player?, action: (Position) -> Unit ) {
    val symbol = player?.symbol ?: ' '
    val modifier = Modifier
            .size(width = 100.dp, height = 100.dp)
            .border(width = 2.dp, Color.Gray)
            .let {
                if(player == null) it.clickable { action(Position(lin, col)) }
                else it
            }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(symbol.toString(), fontSize = 6.em)
    }
}
