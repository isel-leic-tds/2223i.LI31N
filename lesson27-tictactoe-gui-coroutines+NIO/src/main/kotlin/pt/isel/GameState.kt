package pt.isel

import androidx.compose.runtime.mutableStateOf
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pt.isel.ttt.*
import java.net.URI
import java.net.URL
import java.net.http.HttpClient
import java.net.http.HttpClient.Redirect
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.Paths
import java.time.Duration
import java.util.concurrent.CompletableFuture
import kotlin.concurrent.thread

class GameState(val scope: CoroutineScope) {
    private val boardState = mutableStateOf<GameAsync?>(null)
    private val messageState = mutableStateOf<String?>(null)
    private val chuckNorrisState = mutableStateOf("")

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
        // requestChuckNorrisNio().thenApply { chuckNorrisState.value = it ?: "" }
        scope.launch { chuckNorrisState.value = requestChuckNorris() }
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

    fun message(board: Board) = when(board) {
        is BoardDraw -> "Game finished with a draw!"
        is BoardWin -> "Player ${board.winner} won the game!"
        is BoardRun -> null
    }
}

private val jsonValueProperty = """(?<=\"value\":\")[^\"]*""".toRegex()
val client = HttpClient.newBuilder()
    .followRedirects(Redirect.ALWAYS)
    .build()
/**
 * Using Non-blocking IO with native JDK HttpClient API.
 */
fun requestChuckNorrisNio(): CompletableFuture<String> {
    val request: HttpRequest = HttpRequest.newBuilder()
        .uri(URI.create("https://app.requestly.io/delay/1000/https://api.chucknorris.io/jokes/random"))
        .GET()
        .build()
    return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
        .thenApply { println("get Body from Response"); it.body() }
        .thenApply { println("parsing JSON and read value"); jsonValueProperty.find(it)?.value }
}

val clientKtor: io.ktor.client.HttpClient = HttpClient()
suspend fun requestChuckNorris(): String {
    val res = clientKtor.request("https://app.requestly.io/delay/1000/https://api.chucknorris.io/jokes/random")
    val body = res.bodyAsText()
    return jsonValueProperty.find(body)?.value ?: ""
}
