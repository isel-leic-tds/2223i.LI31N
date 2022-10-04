package pt.isel.ui

import pt.isel.ttt.Board

interface CommandOop {
    fun action(board: Board?, args: List<String>) : Board?
    fun show(board: Board)
    val syntax : String
}
