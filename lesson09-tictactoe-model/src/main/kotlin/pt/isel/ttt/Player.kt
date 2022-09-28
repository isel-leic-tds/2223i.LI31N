package pt.isel.ttt

enum class Player {
    CROSS, CIRCLE;

    fun turn() = if(this == CIRCLE) CROSS else CIRCLE
}
