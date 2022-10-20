package pt.isel.ui

import pt.isel.Storage
import pt.isel.ttt.*
import pt.isel.ttt.Player.CROSS

data class Game(val name: String, val board: Board, val player: Player = CROSS)

/**
 * If given arguments comply to <line> <col> then it will:
 * 1. create a new Board object through play() operation.
 * 2. save the board on storage, and
 * 3. return the new Game object with that board.
 */
fun Game.play(storage: Storage<String, Board>, args: List<String>): Game {
    /*
     * Validate args.
     */
    require(args.size == 2) { "Missing arguments! Required line and column." }
    val line = args[0].toIntOrNull() ?: throw IllegalArgumentException("Invalid Integer value for line!")
    val col = args[1].toIntOrNull() ?: throw IllegalArgumentException("Invalid Integer value for column!")
    val pos = Position(line, col) // May throw Error for illegal line or col
    /*
     * Creates a new Board, saves it on storage, and returns a new Game.
     */
    return board
        .play(pos, player)                // 1. create a new Board object through play() operation.
        .also { storage.save(name, it) }  // 2. save the board on storage
        .let { copy(board = it) }         // 3. return the new Game object with that board.
}

fun startGame(storage: Storage<String, Board>, args: List<String>): Game {
    require(args.size == 1) { "You should provide a single argument with a unique name!" }
    val name = args[0]
    /**
     * If there is no Board then inserts a new empty Board on storage and
     * returns a new Game for that Board.
     */
    val board = storage.load(name) ?: return Game(name, storage.new(name), CROSS)
    /**
     * If there is already a Board on storage with moves then it deletes the
     * existing Board from storage and creates a Game with a new empty Board.
     * Otherwise, it returns a Game with the existing Board for player CIRCLE.
     */
    return if(board.moves.isNotEmpty()) {
        storage.delete(name)
        Game(name, storage.new(name), CROSS)
    }
    else Game(name, board, Player.CIRCLE)
}
