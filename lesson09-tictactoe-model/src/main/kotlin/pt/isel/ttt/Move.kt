package pt.isel.ttt

data class Move(val pos: Position, val player: Player) {
    fun serialize() = "${pos.lin}${pos.col}${player.symbol}"
}
fun String.deserializeToMove(): Move {
    require(this.length == 3) { "String must contain exactly 3 characters corresponding to <line> <column> <X|Y>" }
    return Move(
        Position(this[0].toString().toInt(), this[1].toString().toInt()),
        this[2].toPlayer()
    )
}
