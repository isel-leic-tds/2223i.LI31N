package pt.isel.ui

import pt.isel.Storage
import pt.isel.ttt.*

object CmdQuitOop : CommandOop<Game> {
    override fun action(subject: Game?, args: List<String>) = null
    override fun show(subject: Game) {}
    override val syntax: String get() = "quit"
}

class CmdStartOop(private val storage: Storage<String, Board>) : CommandOop<Game> {
    override fun show(subject: Game) = printBoard(subject.board)
    override val syntax: String get() = "start <name>"
    override fun action(subject: Game?, args: List<String>) = startGame(storage, args)
}

class CmdRefreshOop(private val storage: Storage<String, Board>) : CommandOop<Game> {
    override fun show(subject: Game) = printBoard(subject.board)
    override val syntax: String get() = "refresh"
    override fun action(subject: Game?, args: List<String>): Game? {
        require(subject != null)
        val b = storage.load(subject.name)
        require(b != null)
        return subject.copy(board = b)
    }
}

/**
 * Represents a command with syntax e.g. play 2 1
 */
class CmdPlayOop(private val storage: Storage<String, Board>) : CommandOop<Game> {
    override fun show(subject: Game) = printBoard(subject.board)
    override val syntax: String get() = "play <line> <col>"
    override fun action(subject: Game?, args: List<String>): Game? {
        require(subject != null) {"You should start a game to initialize a Board before start playing"}
        return subject.play(storage, args)
    }
}

val sepLine = "\n"+"---+".repeat(BOARD_SIZE-1)+"---"

fun printBoard(board: Board) {
    Position.values.forEach { pos ->
        print(" ${board.get(pos)?.player?.symbol ?: " "} ")
        if (pos.col == BOARD_SIZE-1)
            if (pos.lin < BOARD_SIZE-1) println(sepLine)
            else println()
        else
            print("|")
    }
    if(board is BoardWin)
        board.winner.apply{ println("Player ${board.winner} wins.") }
}
/*
 O |   |
---+---+---
 X | O |
---+---+---
 O |   | X
 */
