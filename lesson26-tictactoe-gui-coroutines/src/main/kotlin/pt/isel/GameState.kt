package pt.isel

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import pt.isel.ttt.Board
import pt.isel.ttt.BoardRun
import pt.isel.ttt.Position
import java.net.URL
import kotlin.concurrent.thread

class GameState(scope: CoroutineScope) {
    private val boardState = mutableStateOf<Board>(BoardRun())
    private val messageState = mutableStateOf<String?>(null)
    private val chuckNorrisState = mutableStateOf("")
    private val jsonValueProperty = """(?<=\"value\":\")[^\"]*""".toRegex()

    val board get() = boardState.value
    val message get() = messageState.value
    val chuckNorris get() = chuckNorrisState.value
    val watch = StopWatch(scope).also { it.start() }

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

    fun requestChuckNorris() = thread {
        URL("https://app.requestly.io/delay/1000/https://api.chucknorris.io/jokes/random")
            .readText()
            .let { jsonValueProperty.find(it)?.value  }
            .also { chuckNorrisState.value = it ?: "" }
    }
}

