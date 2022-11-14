package pt.isel

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogState
import pt.isel.ttt.*
import java.net.URL
import kotlin.concurrent.thread

fun requestChuckNorris(onComplete: (String) -> Unit) = thread {
    URL("https://app.requestly.io/delay/1000/https://api.chucknorris.io/jokes/random")
        .readText()
        .let {
            """(?<=\"value\":\")[^\"]*""".toRegex().find(it)?.value
        }
        .also {
            /*
             * !!!!!! UI update out of the UI Thread
             */
            onComplete(it ?: "")
        }
}

@Composable
fun GameView(game: GameState) {
    val chuckNorris = remember { mutableStateOf("") }
    Column {
        BoardView(game.board) {
            game.play(it)
            requestChuckNorris { chuckNorris.value = it }
        }
        Text(chuckNorris.value)
    }
    DialogMessage(game.message, game::dismissMessage)
}

fun message(board: Board) = when(board) {
    is BoardDraw -> "Game finished with a draw!"
    is BoardWin -> "Player ${board.winner} won the game!"
    is BoardRun -> null
}

@Composable
fun DialogMessage(msg: String?, action: () -> Unit) {
    if(msg != null) {
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
