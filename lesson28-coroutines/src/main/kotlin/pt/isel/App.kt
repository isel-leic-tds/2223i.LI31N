package pt.isel

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Thread.sleep
import kotlin.concurrent.thread

fun main() = runBlocking {
    // coroutines()
    // threads()
    // manyCoroutines(100_000)
    manyThreads(100_000)
}

fun coroutines() = runBlocking { // this: CoroutineScope
    launch { // launch a new coroutine and continue
        printWorld()
    }
    print("${Thread.currentThread().name} ")
    println("Hello") // main coroutine continues while a previous one is delayed
}

suspend fun printWorld() {
    delay(1000L) // non-blocking delay for 1 second (default time unit is ms)
    print("${Thread.currentThread().name} ")
    println("World!") // print after delay
}

fun threads()  {
    thread { // launch a new Thread
        sleep(1000L) // blocking delay for 1 second (default time unit is ms)
        print("${Thread.currentThread().name} ")
        println("World!") // print after delay
    }
    print("${Thread.currentThread().name} ")
    println("Hello") // main Thread continues while a previous one is sleeping
}

suspend fun doWorld() = coroutineScope { // this: CoroutineScope
    val job1 = launch {
        delay(2000L)
        println("World 2")
    }
    val job2 = launch {
        delay(1000L)
        println("World 1")
    }
    job1.join()
    println("Hello")
}

suspend fun manyCoroutines(size: Int) = coroutineScope {
    repeat(size) { // launch a lot of coroutines
        launch {
            delay(5000L)
            print(".")
        }
    }
}
fun manyThreads(size: Int) {
    repeat(size) { // launch a lot of coroutines
        thread {
            sleep(5000L)
            print(".")
        }
    }
}
