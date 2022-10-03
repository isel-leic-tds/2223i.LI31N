package pt.isel.ui

import pt.isel.ttt.Board

val cmdQuit = CommandFp(
    action = { _, _ -> null },
    show = { },
    syntax = "quit"
)
