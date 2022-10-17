package pt.isel.ui

fun main() {

    readCommandsOop(mapOf(
        "QUIT" to CmdQuitOop,
        "PLAY" to CmdPlayOop,
        "START" to CmdStartOop
    ))
/*
    readCommandsFp(mapOf(
        "QUIT" to cmdQuit,
        "PLAY" to cmdPlay,
        "START" to cmdStart
    ))
*/
}
