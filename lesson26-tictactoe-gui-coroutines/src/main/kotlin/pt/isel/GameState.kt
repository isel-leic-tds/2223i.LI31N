package pt.isel

import androidx.compose.runtime.mutableStateOf

import pt.isel.ttt.Board
import pt.isel.ttt.BoardRun
import pt.isel.ttt.Position

class GameState(val watch: StopWatch) {
    val board get() = boardState.value
    val message get() = messageState.value

    private val boardState = mutableStateOf<Board>(BoardRun())
    private val messageState = mutableStateOf<String?>(null)

    fun newGame() {
        boardState.value = BoardRun()
        watch.reset()
        watch.start()
    }

    fun play(pos: Position) {
        val (board, setBoard) = boardState
        val newBoard = if(board is BoardRun) {
            board.play(pos, board.player.turn()).also(setBoard)
        } else { board }
        messageState.value = message(newBoard)
        if(message != null) watch.pause()
    }

    fun dismissMessage() {
        messageState.value = null
    }
}
