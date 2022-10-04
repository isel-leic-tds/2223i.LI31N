package pt.isel.ttt

const val BOARD_SIZE = 3
const val MAX_MOVES = BOARD_SIZE * BOARD_SIZE

sealed class Board(val moves: List<Move>) {
    abstract fun play(pos: Position, p: Player) : Board
    fun get(pos: Position): Move? {
        return moves.find { it.pos == pos }
    }
}

class BoardDraw(moves: List<Move>) : Board(moves) {
    override fun play(pos: Position, p: Player) = throw IllegalStateException("This game has already finished!")
}

class BoardWin(moves: List<Move>, val winner: Player) : Board(moves) {
    override fun play(pos: Position, p: Player) = throw IllegalStateException("Player $winner has won the game!")
}

class BoardRun(
    moves: List<Move> = emptyList(),
    val player: Player = Player.CIRCLE
) : Board(moves) {
    /**
     * If it is a valid move then it creates a new
     * Board with all previous moves and the new one.
     */
    override fun play(pos: Position, p: Player): Board {
        require(p == player.turn()) { "Wrong player. Should play the ${player.turn()}" }
        require(moves.any { it.pos == pos }.not()) { "Position already occupied!" }
        val m = Move(pos, p)
        return when {
            checkWinner(m) -> BoardWin(moves + m, p)
            moves.size == (MAX_MOVES - 1) -> BoardDraw(moves + m)
            else -> BoardRun(moves + m, p)
        }
    }

    private fun checkWinner(m: Move): Boolean {
        val ourMoves = moves.filter { it.player == m.player } + m
        return ourMoves.count { it.pos.lin == m.pos.lin } == BOARD_SIZE
                || ourMoves.count { it.pos.col == m.pos.col } == BOARD_SIZE
                || ourMoves.count { it.pos.backslash } == BOARD_SIZE
                || ourMoves.count { it.pos.slash } == BOARD_SIZE
    }
}
