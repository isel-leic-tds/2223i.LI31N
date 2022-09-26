package pt.isel.oop

import org.junit.Test
import kotlin.test.assertEquals

class NaifDateTest {
    @Test fun `28-10-2022 next month should be 11`() {
        val date = NaifDate(28,Month.OCT,2020)
        assertEquals(Month.NOV, date.nextMonth)
    }
    @Test fun `28-11-2022 next month should be 12`() {
        val date = NaifDate(28,Month.NOV,2020)
        assertEquals(Month.DEC, date.nextMonth)
    }
    @Test fun `28-12-2022 next month should be 1`() {
        val date = NaifDate(28,Month.DEC,2020)
        assertEquals(Month.JAN, date.nextMonth)
    }
    @Test fun `28-9-2022 add 73 days should return 10-12-2022`() {
        val date = NaifDate(28,Month.SEP,2022)
        assertEquals(NaifDate(10,Month.DEC,2022), date.addDays(73))
    }
    @Test fun `28-9-2022 add 117 days should return 23-1-2023`() {
        val date = NaifDate(28,Month.SEP,2022)
        assertEquals(NaifDate(23,Month.JAN,2023), date.addDays(117))
    }
    @Test fun `28-12-2022 add 43 days should return 9-2-2023`() {
        val date = NaifDate(28,Month.DEC,2022)
        assertEquals(NaifDate(9,Month.FEB,2023), date.addDays(43))
    }
    @Test fun `28-12-2022 add 43 days with plus operatotor should return 9-2-2023`() {
        val date = NaifDate(28,Month.DEC,2022)
        assertEquals(NaifDate(9,Month.FEB,2023), date + 43)
    }

    @Test fun `toString of NaifDate should return in format dd-mm-yyyy`() {
        assertEquals("9-2-2023", NaifDate(9,Month.FEB,2023).toString())
    }
}
