package pt.isel

import java.lang.IllegalArgumentException

enum class Operator(private val opr: Char) {
    SUM('+') {
        override fun eval(a: Int, b: Int) = a + b
    },
    SUB('-') {
        override fun eval(a: Int, b: Int) = a - b
    },
    MUL('*') {
        override fun eval(a: Int, b: Int) = a * b
    },
    DIV('/') {
        override fun eval(a: Int, b: Int) = a/ b
    };

    companion object {
        fun parse(opr: Char) : Operator {
            return values().find { it.opr == opr } ?: throw IllegalArgumentException("Operator $opr is not valid!")
        }
    }

    abstract fun eval(a: Int, b: Int) : Int
}

