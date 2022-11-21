package pt.isel

import org.junit.Test

class AsyncHttpRequestTest {

    @Test
    fun callRequestChuckNorrisNio() {
        println("Start Requesting")
        val promise = requestChuckNorrisNio()
        println("Requesting Sent")
        promise.join()
    }
}
