package pt.isel.ui

import pt.isel.Storage
import pt.isel.ttt.*

val cmdQuit = CommandFp<Game>(
    action = { _, _ -> null },
    show = { },
    syntax = "quit"
)


fun cmdStart(storage: Storage<String, Board>) = CommandFp<Game>(
    action = { _, args -> startGame(storage, args) },
    show = { game -> printBoard(game.board) },
    syntax = "start"
)

fun cmdPlay(storage: Storage<String, Board>) = CommandFp<Game>(
    action = { game, args ->
        require(game != null) {"You should start a game to initialize a Board before start playing"}
        game.play(storage, args)
    },
    show = { game -> game?.board.let(::printBoard) },
    syntax = "play <X|O> <line> <col>"
)
