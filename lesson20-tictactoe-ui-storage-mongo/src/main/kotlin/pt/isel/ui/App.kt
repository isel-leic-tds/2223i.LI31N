package pt.isel.ui

import pt.isel.FileStorage
import pt.isel.StringSerializer
import pt.isel.ttt.Board
import pt.isel.ttt.BoardRun
import pt.isel.ttt.deserializeToBoard

fun main() {

    val serializer = object : StringSerializer<Board> {
        override fun write(obj: Board) = obj.serialize()
        override fun parse(input: String) = input.deserializeToBoard()
    }
    val fs = FileStorage<String, Board>("out", serializer) { BoardRun() }

    readCommandsOop(mapOf(
        "QUIT" to CmdQuitOop,
        "PLAY" to CmdPlayOop(fs),
        "START" to CmdStartOop(fs),
        "REFRESH" to CmdRefreshOop(fs)
    ))
/*
    readCommandsFp(mapOf(
        "QUIT" to cmdQuit,
        "PLAY" to cmdPlay,
        "START" to cmdStart
    ))
*/
}
