package org.examp.project.test1

import io.github.aakira.napier.Napier
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.createCoroutine
import kotlin.coroutines.resume

class CoroutineTest {
    private val continuation = suspend {
        println("fesfesfe")
        Napier.e(tag = "pupu", message = "coroutine test log")
        5
    }.createCoroutine(object : Continuation<Int>{

        override val context: CoroutineContext
            get() = EmptyCoroutineContext

        override fun resumeWith(result: Result<Int>) {
            println("Coroutine end :${result}")
        }
    })

    fun shart(){
        continuation.resume(Unit)
    }
}