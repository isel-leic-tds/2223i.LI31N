package pt.isel

import pt.isel.fp.CalculatorFp
import pt.isel.oop.CalculatorOop
import kotlin.test.Test
import kotlin.test.assertEquals

class PrefixCalculatorTest {

    @Test fun `CalculatorOop eval complex prefix expression successfully`() {
        val res = CalculatorOop.parsePrefix("* / 1024 12 - 45 + 2 17")
        // assertEquals( 1024 / 12 * (45 - (2 + 17)), res) // 2210
        assertEquals( 2210, res) // 2218
    }
    @Test fun `CalculatorFp eval complex prefix expression successfully`() {
        val res = CalculatorFp.parsePrefix("* / 1024 12 - 45 + 2 17")
        // assertEquals( 1024 / 12 * (45 - (2 + 17)), res) // 2210
        assertEquals( 2210, res) // 2218
    }
}
