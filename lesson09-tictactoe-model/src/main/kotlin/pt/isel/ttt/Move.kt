package pt.isel.ttt

data class Move(val pos: Position, val player: Player) {
    fun serialize() = "${pos.lin};${pos.col};${player.symbol}"
}
fun String.deserializeToMove() = split(";").let { Move(
    Position(it[0].toInt(), it[1].toInt()),
    it[2].toPlayer()
)}
