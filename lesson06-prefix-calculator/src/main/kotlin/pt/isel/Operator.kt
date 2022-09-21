package pt.isel

import java.lang.IllegalArgumentException

enum class Operator(private val opr: Char) {
    Sum('+') {
        override fun eval(a: Int, b: Int) = a + b
    },
    Sub('-') {
        override fun eval(a: Int, b: Int) = a - b
    },
    Mul('*') {
        override fun eval(a: Int, b: Int) = a * b
    },
    Div('/') {
        override fun eval(a: Int, b: Int) = a/ b
    };

    companion object {
        fun parse(opr: Char) : Operator {
            return values().find { it.opr == opr } ?: throw IllegalArgumentException("Operator $opr is not valid!")
        }
    }

    abstract fun eval(a: Int, b: Int) : Int
}

