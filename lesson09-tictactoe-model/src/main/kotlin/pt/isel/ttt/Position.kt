package pt.isel.ttt

const val LAST_COORD = BOARD_SIZE - 1

class Position private constructor(val lin: Int, val col: Int) {

    val backslash get() = lin == col
    val slash get() = lin == BOARD_SIZE - col - 1

    companion object {
        val values = (0 until MAX_MOVES).map { Position(it / BOARD_SIZE, it % BOARD_SIZE) }

        operator fun invoke(l: Int, c: Int): Position {
            require(l in 0..LAST_COORD) { "Invalid line value of $l must be between 0 and $LAST_COORD" }
            require(c in 0..LAST_COORD) { "Invalid column value of $c must be between 0 and $LAST_COORD" }
            val index = l * BOARD_SIZE + c
            return values[index]
        }
    }
}
