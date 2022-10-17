package pt.isel.ui

class CommandFp<T> (
    val action: (T?, List<String>) -> T?,
    val show: (T) -> Unit,
    val syntax : String
)
