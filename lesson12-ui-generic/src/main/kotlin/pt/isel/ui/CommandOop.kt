package pt.isel.ui

interface CommandOop<T> {
    fun action(board: T?, args: List<String>) : T?
    fun show(board: T)
    val syntax : String
}
