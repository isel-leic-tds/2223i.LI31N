package pt.isel

interface StringSerializer<T> {
    fun write(obj: T): String
    fun parse(input: String): T
}
