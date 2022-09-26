package pt.isel.oop

import pt.isel.oop.Month.DEC
import pt.isel.oop.Month.JAN

data class NaifDate(
    private val day: Int,
    private val month: Month,
    private val year: Int)
{

    val nextMonth = month.next()

    fun addDays(inc: Int) : NaifDate {
        val diff = inc - (month.nrOfDays(year) - day + 1)
        return when {
            day + inc <= month.nrOfDays(year) -> NaifDate(day + inc, month, year)
            month == DEC -> NaifDate(1, JAN, year + 1).addDays(diff)
            else -> NaifDate(1, nextMonth, year).addDays(diff)
        }
    }

    override fun toString(): String = "$day-${month.ordinal + 1}-$year"

    operator fun plus(days: Int): NaifDate {
        return this.addDays(days)
    }
}
