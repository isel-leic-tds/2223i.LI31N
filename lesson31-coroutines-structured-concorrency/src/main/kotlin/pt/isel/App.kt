package pt.isel

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.*


val urls = listOf( "https://github.com/", "https://stackoverflow.com/", "http://dzone.com/", "https://www.infoq.com/")

fun main() = runBlocking {
    //fetchAndSumSequential(urls)
    fetchAndSumConcurrent(urls)
}

val httpClient = HttpClient()

suspend fun CoroutineScope.fetchAndSumConcurrent(urls: List<String>) {
    val sum = urls
        .map { async { fetchAndGetBodySize(it) } } // List<Deferred<Int>>
        .sumOf { it.await() }
    println("TOTAL sum = $sum")
}

suspend fun CoroutineScope.fetchAndSumSequential(urls: List<String>) {
    var sum = 0
    for (url in urls) {
        val promise: Deferred<Int> = async { fetchAndGetBodySize(url) }
        sum += promise.await()
    }
    println("TOTAL sum = $sum")
}

suspend fun fetchAndGetBodySize(url: String): Int {
    println("Requesting $url")
    val body = httpClient.request(url).bodyAsText()
    println("=========> $url body size = ${body.length}")
    return body.length
}
