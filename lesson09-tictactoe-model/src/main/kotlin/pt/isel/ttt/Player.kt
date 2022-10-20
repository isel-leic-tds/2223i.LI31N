package pt.isel.ttt

import java.lang.IllegalArgumentException

enum class Player(val symbol: Char) {
    CROSS('X'), CIRCLE('O');

    fun turn() = if(this == CIRCLE) CROSS else CIRCLE
}

fun Char.toPlayer(): Player {
    return Player
        .values()
        .find { it.symbol == this }
        ?: throw IllegalArgumentException("There is no player for symbol $this")
}
