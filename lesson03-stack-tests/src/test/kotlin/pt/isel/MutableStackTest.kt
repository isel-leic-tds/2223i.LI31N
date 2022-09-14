package pt.isel

import kotlin.test.*

class MutableStackTest {
    @Test fun `new instance of MutableStack should be empty`() {
        val stk = MutableStack<String>()
        assertTrue(stk.isEmpty())
    }
    @Test fun `last pushed item is the peeked item`() {
        val stk = MutableStack<String>()
        stk.push("ISEL")
        assertFalse (stk.isEmpty())
        assertEquals("ISEL", stk.peek())
    }
    @Test fun `after poping MutableStack with single item stays empty`() {
        val stk = MutableStack<String>()
        stk.push("ISEL")
        stk.pop()
        assertTrue(stk.isEmpty())
    }
    @Test fun `poping empty stack throws NoSuchElementException`() {
        val stk = makeStack<String>()
        assertFailsWith<NoSuchElementException> {
            stk.pop()
        }
    }
    @Test fun `peeking empty stack throws NoSuchElementException`() {
        val stk = makeStack<String>()
        assertFailsWith<NoSuchElementException> {
            stk.peek()
        }
    }
}
