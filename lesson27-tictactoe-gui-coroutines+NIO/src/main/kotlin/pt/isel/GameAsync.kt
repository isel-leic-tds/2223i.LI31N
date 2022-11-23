package pt.isel

import pt.isel.ttt.Board
import pt.isel.ttt.BoardRun
import pt.isel.ttt.Player
import pt.isel.ttt.Position

data class GameAsync(
    val name: String,
    val board: Board = BoardRun(),
    val player: Player = Player.CROSS
)

suspend fun startGame(storage: StorageAsync<String, Board>, name: String) : GameAsync {
    val board = storage.load(name)
    if(board != null && board.moves.size <= 1) return GameAsync(name, board, Player.CIRCLE)
    if(board == null) return GameAsync(name, storage.new(name))
    // storage.delete(name)
    return GameAsync(name, storage.new(name))
}

suspend fun GameAsync.play(storage: StorageAsync<String, Board>, pos: Position): GameAsync {
    val newBoard = this.board.play(pos, player) // May throw Exception if is not your turn
    storage.save(name, newBoard)
    // return Game(name, newBoard, player)
    return this.copy(board = newBoard)
}
