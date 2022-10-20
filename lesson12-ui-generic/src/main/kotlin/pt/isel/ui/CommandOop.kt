package pt.isel.ui

interface CommandOop<T> {
    fun action(subject: T?, args: List<String>) : T?
    fun show(subject: T)
    val syntax : String
}
