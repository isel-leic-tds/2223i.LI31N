package pt.isel.ui

import pt.isel.Storage
import pt.isel.ttt.*

object CmdQuitOop : CommandOop<Game> {
    override fun action(board: Game?, args: List<String>) = null
    override fun show(board: Game) {}
    override val syntax: String get() = "quit"
}
class CmdStartOop(val storage: Storage<String, Board>) : CommandOop<Game> {
    override fun show(game: Game) = printBoard(game.board)
    override val syntax: String get() = "start"
    override fun action(game: Game?, args: List<String>): Game {
        require(args.size == 1) { "You should provide a name for the game!"}
        return startGame(storage, args[0])
    }
}
/**
 * Represents a command with syntax e.g. play 2 1
 */
class CmdPlayOop(val storage: Storage<String, Board>) : CommandOop<Game> {
    override fun show(game: Game) = printBoard(game.board)
    override val syntax: String get() = "play <line> <col>"
    override fun action(game: Game?, args: List<String>): Game? {
        require(game != null) {"You should start a game to initialize a Board before start playing"}
        require(args.size == 2) {"Missing arguments! Required line and column."}
        val line = args[0].toIntOrNull() ?: throw IllegalArgumentException("Invalid Integer value for line!")
        val col = args[1].toIntOrNull() ?: throw IllegalArgumentException("Invalid Integer value for column!")
        val pos = Position(line, col) // May throw Error for illegal line or col
        return game.play(storage, line, col)
    }
}

class CmdRefreshOop(val storage: Storage<String, Board>) : CommandOop<Game> {
    override fun show(game: Game) = printBoard(game.board)
    override val syntax: String get() = "refresh"
    override fun action(game: Game?, args: List<String>): Game? {
        require(game != null) {"You should star a new game before refreshing!"}
        val board = storage.load(game.name)
        require(board != null)
        return game.copy(board = board)
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
        board.winner?.apply{ println("Player ${board.winner} wins.") }
}
/*
 O |   |
---+---+---
 X | O |
---+---+---
 O |   | X
 */
