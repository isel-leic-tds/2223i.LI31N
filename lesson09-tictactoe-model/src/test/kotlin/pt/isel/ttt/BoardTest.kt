package pt.isel.ttt

import pt.isel.ttt.Player.CIRCLE
import pt.isel.ttt.Player.CROSS
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class BoardTest {

    @Test fun `The same player cannot play twice`() {
        val ex = assertFailsWith<IllegalArgumentException> {
            BoardRun(player = CIRCLE).play(Position(0,0), CIRCLE)
        }
        assertEquals("Wrong player. Should play the CROSS", ex.message)
    }
    @Test fun `Cannot play on already occupied position`() {
        val ex = assertFailsWith<IllegalArgumentException> {
            BoardRun()
                .play(Position(0,0), CROSS)
                .play(Position(0,0), CIRCLE)
        }
        assertEquals("Position already occupied!", ex.message)
    }
    @Test fun `We cannot make more plays after 9 moves`() {
        val ex = assertFailsWith<IllegalStateException> {
            BoardRun()
                .play(Position(0,0), CROSS)
                .play(Position(0,1), CIRCLE)
                .play(Position(0,2), CROSS)
                .play(Position(2,0), CIRCLE)
                .play(Position(1,0), CROSS)
                .play(Position(1,1), CIRCLE)
                .play(Position(2,2), CROSS)
                .play(Position(1,2), CIRCLE)
                .play(Position(2,1), CROSS)
                .play(Position(0,0), CIRCLE) // Has already finished

        }
        assertEquals("This game has already finished!", ex.message)
    }

    @Test fun `Check CIRCLE wins the game with a line`() {
        val board = BoardRun()
            .play(Position(1,0), CROSS)
            .play(Position(0,0), CIRCLE)
            .play(Position(1,2), CROSS)
            .play(Position(0,1), CIRCLE)
            .play(Position(2,0), CROSS)
            .play(Position(0,2), CIRCLE)
        assertEquals(CIRCLE, (board as BoardWin).winner)
        val ex = assertFailsWith<IllegalStateException> {
            board.play(Position(0,0), CIRCLE)
        }
        assertEquals("Player CIRCLE has won the game!", ex.message)
    }
    @Test fun `Check CIRCLE wins the game with a backslash`() {
        val board = BoardRun()
            .play(Position(1,0), CROSS) .play(Position(0,0), CIRCLE)
            .play(Position(1,2), CROSS) .play(Position(1,1), CIRCLE)
            .play(Position(2,0), CROSS) .play(Position(2,2), CIRCLE)
        assertEquals(CIRCLE, (board as BoardWin).winner)
        val ex = assertFailsWith<IllegalStateException> {
            board.play(Position(0,0), CIRCLE)
        }
        assertEquals("Player CIRCLE has won the game!", ex.message)
    }

}
