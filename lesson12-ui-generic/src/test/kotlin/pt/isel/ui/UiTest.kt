package pt.isel.ui

import java.io.BufferedInputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.Test
import kotlin.test.assertEquals

object CmdFinish : CommandOop<Int> {
    override fun action(subject: Int?, args: List<String>) = null
    override fun show(board: Int) { }
    override val syntax: String get() = "FINISH"
}
class UiTest {
    @Test fun `Cmd with action returning null finishes the readCommands loop`() {
        val input = "finish".toByteArray()
        System.setIn(ByteArrayInputStream(input))
        readCommandsOop(mapOf("FINISH" to CmdFinish))
    }

    @Test fun `Input of unknown command gives message invalid command`() {
        /*
        val input = "kjahfkja\nfinish".toByteArray()
        System.setIn(ByteArrayInputStream(input))
        val mem = ByteArrayOutputStream()
        System.setOut(PrintStream(mem))
        readCommandsOop(mapOf("FINISH" to CmdFinish))
        val output = mem
            .toString()
            .split(System.lineSeparator())
            .map {
                if(it.startsWith("> ")) it.drop(2)
                else it
            }
        assertEquals("Invalid command", output[0])
         */
        val output = redirectInOut(listOf("ljkdhfjkh", "finish")) {
            readCommandsOop(mapOf("FINISH" to CmdFinish))
        }
        assertEquals("Invalid command", output[0])
    }

    fun redirectInOut(stmts: List<String>, block: () -> Unit) : List<String> {
        /**
         * First store the standard input and output and
         * redirect to memory.
         */
        val oldOut = System.out
        val oldIn = System.`in`
        val mem = ByteArrayOutputStream()
        System.setOut(PrintStream(mem))
        System.setIn(ByteArrayInputStream(stmts.joinToString("\n").toByteArray()))
        /**
         * Execute given block.
         */
        block()
        /**
         * Restore standard input and output and return the resulting output lines.
         */
        System.setOut(oldOut)
        System.setIn(oldIn)
        return mem
            .toString()
            .split(System.lineSeparator())
            .map { if(it.startsWith("> ")) it.drop(2) else it }
    }
}
