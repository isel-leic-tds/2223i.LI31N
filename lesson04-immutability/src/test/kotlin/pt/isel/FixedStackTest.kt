package pt.isel

import kotlin.test.*

class FixedStackTest {
    @Test fun `new instance of FixedStack should be empty`() {
        val stk = FixedStack<String>()
        assertTrue(stk.isEmpty())
    }
    @Test fun `last pushed item is the peeked item`() {
        val stk = FixedStack<String>().push("ISEL")
        assertFalse (stk.isEmpty())
        assertEquals("ISEL", stk.peek())
    }
    @Test fun `after poping FixedStack with single item stays empty`() {
        val stk = FixedStack<String>()
            .push("ISEL")
            .pop()
        assertTrue(stk.isEmpty())
    }
    @Test fun `poping empty FixedStack throws NoSuchElementException`() {
        val stk = FixedStack<String>()
        assertFailsWith<NoSuchElementException> {
            stk.pop()
        }
    }
    @Test fun `peeking empty FixedStack throws NoSuchElementException`() {
        val stk = FixedStack<String>()
        assertFailsWith<NoSuchElementException> {
            stk.peek()
        }
    }
}
