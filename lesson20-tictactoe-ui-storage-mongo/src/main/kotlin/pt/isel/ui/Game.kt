package pt.isel.ui

import pt.isel.Storage
import pt.isel.ttt.*
import pt.isel.ttt.Player.*

data class Game(
    val name: String,
    val board: Board = BoardRun(),
    val player: Player = CROSS
)

fun startGame(storage: Storage<String, Board>, name: String) : Game {
    val board = storage.load(name)
    if(board != null && board.moves.size <= 1) return Game(name, board, CIRCLE)
    if(board == null) return Game(name, storage.new(name))
    storage.delete(name)
    return Game(name, storage.new(name))
}

fun Game.play(storage: Storage<String, Board>, lin: Int, col: Int): Game? {
    val pos = Position(lin, col) // May throw Error for illegal line or col
    val newBoard = this.board.play(pos, player) // May throw Exception if is not your turn
    storage.save(name, newBoard)
    // return Game(name, newBoard, player)
    return this.copy(board = newBoard)
}
