package pt.isel

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import pt.isel.ttt.*
import java.net.URI
import java.net.URL
import java.net.http.HttpClient
import java.net.http.HttpClient.Redirect
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.Paths
import java.time.Duration
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
        requestChuckNorrisNio()
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

    /**
     * DO NOT use multi-thread approach for IO!!!!
     */
    fun requestChuckNorrisParallel() = thread {
        URL("https://app.requestly.io/delay/1000/https://api.chucknorris.io/jokes/random")
            .readText()
            .let { jsonValueProperty.find(it)?.value  }
            .also { chuckNorrisState.value = it ?: "" }
    }

    /**
     * Using Non-blocking IO with native JDK HttpClient API.
     */
    fun requestChuckNorrisNio() {
        val request: HttpRequest = HttpRequest.newBuilder()
            .uri(URI.create("https://app.requestly.io/delay/1000/https://api.chucknorris.io/jokes/random"))
            .GET()
            .build()
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(HttpResponse<String>::body)
            .thenApply { jsonValueProperty.find(it)?.value }
            .thenAccept { chuckNorrisState.value = it ?: "" }
    }
    val client = HttpClient.newBuilder()
        .followRedirects(Redirect.ALWAYS)
        .build()

    fun message(board: Board) = when(board) {
        is BoardDraw -> "Game finished with a draw!"
        is BoardWin -> "Player ${board.winner} won the game!"
        is BoardRun -> null
    }
}

