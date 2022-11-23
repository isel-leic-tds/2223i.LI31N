package pt.isel

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogState
import pt.isel.ttt.*

@Composable
fun GameView(game: GameState) {
    Column {
        BoardView(game.board, game::play)
        Text(game.chuckNorris)
    }
    DialogMessage(game.message, game::dismissMessage)
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
fun BoardView(board: Board?, action: (Position) -> Unit) {
    Column {
        repeat(BOARD_SIZE) { lin ->
            Row {
                repeat(BOARD_SIZE) { col ->
                    val mov = board?.moves?.find { it.pos == Position(lin, col) }
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
