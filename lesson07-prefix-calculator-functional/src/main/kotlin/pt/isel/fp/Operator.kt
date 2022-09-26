package pt.isel.fp

import java.lang.IllegalArgumentException

enum class Operator(private val opr: Char) {
    SUM('+') ,
    SUB('-'),
    MUL('*'),
    DIV('/');

    companion object {
        fun parse(opr: Char) : Operator {
            return values().find { it.opr == opr } ?: throw IllegalArgumentException("Operator $opr is not valid!")
        }
    }
}

fun Operator.eval(a: Int, b: Int) : Int {
    return when(this) {
        Operator.SUM -> a + b
        Operator.SUB -> a - b
        Operator.MUL -> a * b
        Operator.DIV -> a / b
    }
}

