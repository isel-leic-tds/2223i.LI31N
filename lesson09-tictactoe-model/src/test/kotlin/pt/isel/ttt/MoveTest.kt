package pt.isel.ttt

import org.junit.Test
import kotlin.test.assertEquals

class MoveTest {
    @Test fun `Serialize and deserialize returns equivalent Move object`() {
        val m = Move(Position(1, 2), Player.CROSS)
        val newMove = m.serialize().deserializeToMove()
        assertEquals(m, newMove)
    }
}
