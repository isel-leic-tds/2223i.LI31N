package pt.isel.ui

import pt.isel.Storage
import pt.isel.ttt.*

data class Game(
    val name: String,
    val storage: Storage<String, Game>,
    val board: Board = BoardRun(),
    val player: Player = Player.CROSS
)

class cmdGamePlay : CommandOop<Game> {
    override fun action(game: Game?, args: List<String>): Game? {
        require(game != null) {"You should start a game to initialize a Board before start playing"}
        require(args.size == 3) {"Missing arguments! Required player, line and column."}
        val player = args[0].toPlayer() // May throw Error for invalid symbol diff from 0 or X
        val line = args[1].toIntOrNull() ?: throw IllegalArgumentException("Invalid Integer value for line!")
        val col = args[2].toIntOrNull() ?: throw IllegalArgumentException("Invalid Integer value for column!")
        val pos = Position(line, col) // May throw Error for illegal line or col
        val board = game.board.play(pos, player) // May throw Exception if is not your turn
        return game.copy(board = board)
    }

    override fun show(board: Game) {
        TODO("Not yet implemented")
    }

    override val syntax: String
        get() = TODO("Not yet implemented")
}
