package pt.isel.ui

fun main() {
    readCommandsOop(mapOf<String, CommandOop>(
        "QUIT" to CmdQuitOop,
        "PLAY" to CmdPlayOop,
        "START" to CmdStartOop
    ))
}
