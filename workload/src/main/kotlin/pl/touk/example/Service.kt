@file:Example
package pl.touk.example

import pl.touk.ksp.example.Example
import pl.touk.ksp.slf4j.Slf4j

@Slf4j
class Service {

    fun test() {
        logger.info("test1")
    }
}

fun main() {
    val service = Service()
    service.test()
}
