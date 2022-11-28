package pt.isel

import androidx.compose.runtime.mutableStateOf
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pt.isel.ttt.*
import java.lang.Exception
import java.net.URI
import java.net.URL
import java.net.http.HttpClient
import java.net.http.HttpClient.Redirect
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.concurrent.CompletableFuture
import kotlin.concurrent.thread

class GameState(val scope: CoroutineScope, val storage: StorageAsync<String, Board>) {
    private var refreshJob: Job? = null
    private val gameState = mutableStateOf<GameAsync?>(null)
    private val messageState = mutableStateOf<String?>(null)
    private val chuckNorrisState = mutableStateOf("")

    val board get() = gameState.value?.board
    val message get() = messageState.value
    val chuckNorris get() = chuckNorrisState.value
    val watch = StopWatch(scope)

    fun newGame(name: String) {
        scope.launch {
            gameState.value = startGame(storage, name)
            watch.reset()
            watch.start()
            refreshJob = scope.launch {
                val (game, setGame) = gameState
                if(game == null) return@launch
                while(true) {
                    val newBoard = storage.load(game.name)
                    if(newBoard != null && newBoard.moves != game.board.moves) {
                        setGame(game.copy(board = newBoard))
                    }
                    delay(500)
                }
            }
        }
    }

    fun play(pos: Position) {
        scope.launch { chuckNorrisState.value = requestChuckNorris() }
        val (game, setGame) = gameState
        if(game == null) {
            messageState.value = "You must start a new Game before playing!"
            return
        }
        scope.launch {
            try {
                val newGame = if(game.board is BoardRun) {
                    game.play(storage, pos).also(setGame)
                } else { game }
                messageState.value = message(newGame.board)
                if(message != null) watch.pause()
            } catch(ex: Exception) {
                messageState.value = ex.message
                val newBoard = storage.load(game.name)
                if(newBoard != null && newBoard.moves != board?.moves)
                    setGame(game.copy(board = newBoard))
            }
        }
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
        is BoardDraw -> {
            refreshJob?.cancel()
            refreshJob = null
            "Game finished with a draw!"
        }
        is BoardWin -> {
            refreshJob?.cancel()
            refreshJob = null
            "Game finished with a draw!"
        }
        is BoardRun -> {
            null
        }
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
