package pt.isel.ui

import pt.isel.ttt.*

val cmdQuit = CommandFp<Board>(
    action = { _, _ -> null },
    show = { },
    syntax = "quit"
)


val cmdStart = CommandFp<Board>(
    action = { _, _ -> BoardRun() },
    show = { b -> printBoard(b) },
    syntax = "start"
)

val cmdPlay = CommandFp<Board>(
    action = { board, args ->
        require(board != null) {"You should start a game to initialize a Board before start playing"}
        require(args.size == 3) {"Missing arguments! Required player, line and column."}
        val player = args[0][0].toPlayer() // May throw Error for invalid symbol diff from 0 or X
        val line = args[1].toIntOrNull() ?: throw IllegalArgumentException("Invalid Integer value for line!")
        val col = args[2].toIntOrNull() ?: throw IllegalArgumentException("Invalid Integer value for column!")
        val pos = Position(line, col) // May throw Error for illegal line or col
        board.play(pos, player)
    },
    show = { b -> b?.let(::printBoard) },
    syntax = "play <X|O> <line> <col>"
)
