package pt.isel.ui

/**
 * cmds is a Map<String, CommandOop>, e.g.:
 *   "QUIT" to CmdQuitOop,
 *   "START" to CmdStartOop,
 */
fun <T> readCommandsOop(cmds: Map<String, CommandOop<T>>) {
    var model: T? = null
    while(true) {
        print("> ")
        val input = readln()
        val words = input.trim().split(' ') // E.g. ["play", "X", "0", "1"]
        val cmd = cmds[words[0].uppercase()]
        if(cmd == null) {
            println("Invalid command")
            continue
        }
        try {
            model = cmd.action(model, words.drop(1))
            if(model == null) break
            cmd.show(model)
        } catch (e: Exception) {
            println(e.message)
            println(cmd.syntax)
        }
    }
}

fun <T> readCommandsFp(cmds: Map<String, CommandFp<T>>) {
    var state: T? = null
    while(true) {
        print("> ")
        val input = readln()
        val words = input.trim().split(' ') // E.g. ["play", "X", "0", "1"]
        val cmd = cmds[words[0].uppercase()]
        if(cmd == null) {
            println("Invalid command")
            continue
        }
        try {
            state = cmd.action(state, words.drop(1))
            if(state == null) break
            cmd.show(state)
        } catch (e: Exception) {
            println(e.message)
            println(cmd.syntax)
        }
    }
}
