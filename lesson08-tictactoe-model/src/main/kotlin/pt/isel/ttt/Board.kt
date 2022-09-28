package pt.isel.ttt

const val BOARD_SIZE = 3

class Board(
    val moves: List<Move> = listOf(),
    val player: Player = Player.CIRCLE,
    val winner: Player? = null
) {
    /**
     * If it is a valid move then it creates a new
     * Board with all previous moves and the new one.
     */
    fun play(pos: Position, p: Player) : Board {
        // <=> if(p == player) throw IllegalArgumentException("Wrong player. Should play the ${player.turn()}")
        require(p == player.turn()) { "Wrong player. Should play the ${player.turn()}" }
        require(moves.any { it.pos == pos }.not()) { "Position already occupied!" }
        check(moves.size < (BOARD_SIZE * BOARD_SIZE)) { "This game has already finished!" }
        check(winner == null) {"Player $winner has won the game!"}
        val m = Move(pos, p)
        val w = if(checkWinner(m)) p else null
        return Board(moves + m, p, w)
    }

    private fun checkWinner(m: Move): Boolean {
        val ourMoves = moves.filter { it.player == m.player } + m
        return ourMoves.count { it.pos.lin == m.pos.lin } == BOARD_SIZE
                || ourMoves.count { it.pos.col == m.pos.col } == BOARD_SIZE
    }
}
