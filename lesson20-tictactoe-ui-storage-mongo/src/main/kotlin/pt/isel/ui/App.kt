package pt.isel.ui

import com.mongodb.ConnectionString
import com.mongodb.client.MongoDatabase
import org.litote.kmongo.KMongo
import pt.isel.FileStorage
import pt.isel.MongoStorage
import pt.isel.StringSerializer
import pt.isel.ttt.Board
import pt.isel.ttt.BoardRun
import pt.isel.ttt.deserializeToBoard

fun main() {
    val connStr = "mongodb+srv://gamboa:G34JmOYJLOkjW3Or@tictactoe.scrluo1.mongodb.net/?retryWrites=true&w=majority"
    val db: MongoDatabase = KMongo
            .createClient(ConnectionString(connStr))
            .getDatabase("ttt-console-ui")

    val serializer = object : StringSerializer<Board> {
        override fun write(obj: Board) = obj.serialize()
        override fun parse(input: String) = input.deserializeToBoard()
    }
    // val fs = FileStorage<String, Board>("out", serializer) { BoardRun() }
    val fs = MongoStorage<Board>("games", db, serializer) { BoardRun() }

    readCommandsOop(mapOf(
        "QUIT" to CmdQuitOop,
        "PLAY" to CmdPlayOop(fs),
        "START" to CmdStartOop(fs),
        "REFRESH" to CmdRefreshOop(fs)
    ))
/*
    readCommandsFp(mapOf(
        "QUIT" to cmdQuit,
        "PLAY" to cmdPlay,
        "START" to cmdStart
    ))
*/
}
