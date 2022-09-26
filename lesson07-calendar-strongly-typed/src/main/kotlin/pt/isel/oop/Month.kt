package pt.isel.oop

enum class Month(private val lastDay: Int) {
    JAN(31),
    FEB(28),
    MAR(31),
    APR(30),
    MAY(31),
    JUN(30),
    JUL(31),
    AUG(31),
    SEP(30),
    OCT(31),
    NOV(30),
    DEC(31);

    fun next() : Month {
        val months: Array<Month> = values()
        return months[(ordinal + 1) % months.size]
    }

    fun nrOfDays(year: Int) : Int {
        return if(this == FEB && isLeapYear(year)) 29
        else this.lastDay
    }

    private fun isLeapYear(year: Int): Boolean {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0
    }
}
