package pt.isel.ui

import pt.isel.ttt.Board

class CommandFp (
    val action: (Board?, List<String>) -> Board?,
    val show: (Board?) -> Unit,
    val syntax : String
)
