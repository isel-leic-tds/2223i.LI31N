package pt.isel.ui

fun main() {
/*
    readCommandsOop(mapOf<String, CommandOop>(
        "QUIT" to CmdQuitOop,
        "PLAY" to CmdPlayOop,
        "START" to CmdStartOop
    ))
 */
    readCommandsFp(mapOf<String, CommandFp>(
        "QUIT" to cmdQuit,
        "PLAY" to cmdPlay,
        "START" to cmdStart
    ))

}
