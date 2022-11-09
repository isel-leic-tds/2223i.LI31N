package pt.isel
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.window.*
import pt.isel.ttt.*

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
            val board: MutableState<Board> = remember { mutableStateOf(BoardRun()) }
            TttMenu(board)
            GameView(board)
        }
    }
}
/**
 * Menu of the game.
 */
@Composable
fun FrameWindowScope.TttMenu(board: MutableState<Board>) {
    MenuBar {
        Menu("Game") {
            Item("New", onClick = { board.value = BoardRun() } )
            Item("Quit", onClick = {  } )
        }
    }
}
@Composable fun GameView(board: MutableState<Board>) {
        val dialogMessage = remember { mutableStateOf("") }
        val dialogVisible = remember { mutableStateOf(false) }
        BoardView(board) { pos ->
            val local = board.value
            if(local is BoardRun) {
                val newBoard = local.play(pos, local.player.turn())
                board.value = newBoard
            } else {
                dialogVisible.value = true
                dialogMessage.value = when(local) {
                    is BoardDraw -> "Game finished with a draw!"
                    is BoardWin -> "Player ${local.winner} wan the game!"
                    else -> "Error unknown Game status!"
                }
            }
        }
        DialogMessage(dialogMessage, dialogVisible) {
            dialogVisible.value = false
        }
}

@Composable fun DialogMessage(msg: State<String>, visible: State<Boolean>, action: () -> Unit) {
    if(visible.value) {
         Dialog(
            onCloseRequest = action,
            title = "Message",
            state = DialogState( height = Dp.Unspecified, width = 350.dp)
        ) {
            Text(msg.value)
        }
    }
}

@Composable fun BoardView(board: State<Board>, action: (Position) -> Unit) {
    Column {
        repeat(BOARD_SIZE) { lin ->
            Row {
                repeat(BOARD_SIZE) { col ->
                    val mov = board.value.moves.find { it.pos == Position(lin, col) }
                    Cell(lin, col, mov?.player, action)
                }
            }
        }
    }
}
@Composable fun Cell(
    lin: Int,
    col: Int,
    player: Player?,
    action: (Position) -> Unit )
{
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
