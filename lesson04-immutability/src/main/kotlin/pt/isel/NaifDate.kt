package pt.isel


class NaifDate(
    private val day: Int,
    private val month: Int,
    private val year: Int)
{
    fun nextMonth() = month % 12 + 1

    fun addDays(days: Int): NaifDate {
        var d = day
        var m = month
        var y = year
        var inc = days
        while(d + inc > daysInAMonth(m, y)) {
            inc -= (daysInAMonth(m, y) - d)
            d = 1
            m = m %12 + 1
            if(m == 1) y++
        }
        return NaifDate(d, m, y)
    }

    private fun febLastDay(year: Int): Int {
        return if(isLeapYear(year)) 29
            else 28
    }
    private fun isLeapYear(year: Int): Boolean {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0
    }

    private fun daysInAMonth(month: Int, year: Int): Int{
        return when (month) {
            1,3,5,7,8,10,12 -> 31
            2 -> febLastDay(year)
            else -> 30
        }
    }

    override fun toString(): String = "$day-$month-$year"
}
