package pt.isel.ttt

const val BOARD_SIZE = 3
const val MAX_MOVES = BOARD_SIZE * BOARD_SIZE

sealed class Board {
    abstract fun play(pos: Position, p: Player) : Board
}

class BoardDraw : Board() {
    override fun play(pos: Position, p: Player): Board {
        throw IllegalStateException("This game has already finished!")
    }
}

class BoardWin(val winner: Player) : Board() {
    override fun play(pos: Position, p: Player): Board {
        throw IllegalStateException("Player $winner has won the game!")
    }
}

class BoardRun(
    val moves: List<Move> = listOf(),
    val player: Player = Player.CIRCLE
) : Board() {
    /**
     * If it is a valid move then it creates a new
     * Board with all previous moves and the new one.
     */
    override fun play(pos: Position, p: Player): Board {
        require(p == player.turn()) { "Wrong player. Should play the ${player.turn()}" }
        require(moves.any { it.pos == pos }.not()) { "Position already occupied!" }
        val m = Move(pos, p)
        return when {
            checkWinner(m) -> BoardWin(p)
            moves.size == (MAX_MOVES - 1) -> BoardDraw()
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
