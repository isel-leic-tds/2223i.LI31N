package pt.isel

data class NaifDate(
    private val day: Int,
    private val month: Int,
    private val year: Int)
{
    // FIELD: private final int daysInAMonth;
    //    private val daysInAMonth = when (month) {
    // FUNCTION: private final int getDaysInAMonth();
    private val daysInAMonth
        get() = when (month) {
            1,3,5,7,8,10,12 -> 31
            2 -> febLastDay(year)
            else -> 30
        }

    val nextMonth = month % 12 + 1

    fun addDays(inc: Int) : NaifDate {
        val diff = inc - (daysInAMonth - day + 1)
        return when {
            day + inc <= daysInAMonth -> NaifDate(day + inc, month, year)
            month == 12 -> NaifDate(1, 1, year + 1).addDays(diff)
            else -> NaifDate(1, nextMonth, year).addDays(diff)
        }
    }

    private fun febLastDay(year: Int): Int {
        return if(isLeapYear(year)) 29
            else 28
    }
    private fun isLeapYear(year: Int): Boolean {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0
    }
/*
    override fun equals(other: Any?): Boolean {
        if(other !is NaifDate) return false
        return this.day == other.day
                && this.month == other.month
                && this.year == other.year
    }
*/
    override fun toString(): String = "$day-$month-$year"

    /**
     * Equivalent to inherited toString() of Any (i.e. Object in Java)
     */
    // override fun toString(): String = "${this::class}@${this.hashCode()}"
}
