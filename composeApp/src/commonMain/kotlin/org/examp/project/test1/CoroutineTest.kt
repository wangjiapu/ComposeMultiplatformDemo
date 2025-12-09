package org.examp.project.test1

import io.github.aakira.napier.Napier
import kotlinx.coroutines.delay
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.RestrictsSuspension
import kotlin.coroutines.createCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.startCoroutine
import kotlin.coroutines.suspendCoroutine

/**
 * 协程的基础设施
 */

//作为协程的一个作用域
class ProducerScope<T> {
    suspend fun produce(value: T) {
    }
}

@RestrictsSuspension
class ProducerScope2<T> {
    suspend fun produce(value: T) {
    }
}


class CoroutineTest {
    private val continuation = suspend {
        println("fesfesfe")
        Napier.e(tag = "pupu", message = "coroutine test log")
        5
    }.createCoroutine(object : Continuation<Int> {

        override val context: CoroutineContext
            get() = EmptyCoroutineContext

        override fun resumeWith(result: Result<Int>) {
            println("Coroutine end :${result}")
        }
    })

    fun shart() {
        continuation.resume(Unit)
    }

    // receiver
    fun <R, T> launchCoroutine(receiver: R, block: suspend R.() -> T) {
        block.startCoroutine(receiver, object : Continuation<T> {
            override val context: CoroutineContext
                get() = EmptyCoroutineContext

            override fun resumeWith(result: Result<T>) {
                Napier.e(tag = "pupu", message = "end:${result}")
            }
        })
    }

    fun callLaunchCoroutine() {
        launchCoroutine(ProducerScope<Int>()) {
            Napier.e(message = "start")
            // 此处可以直接调用producerScope中的函数
            produce(111)
            delay(100)
            produce(222)
        }
    }

    fun callLaunchCoroutine2() {
        launchCoroutine(ProducerScope2<Int>()) {
            Napier.e(message = "start")
            // 此处可以直接调用producerScope中的函数
            produce(111)
            //delay(100) // 有RestrictsSuspension注解就无法调用外部的函数
            produce(222)
        }
    }

    // 这是一个不会挂起的挂起函数
    suspend fun notSuspend() = suspendCoroutine<Int> { continuation ->
        continuation.resume(100)
    }

}

class CoroutineName(val name: String) : AbstractCoroutineContextElement(key = Key) {
    companion object Key : CoroutineContext.Key<CoroutineName>
}

class CoroutineExceptionHandler(val onErrorAction: (Throwable) -> Unit) :
    AbstractCoroutineContextElement(key = Key) {
    companion object Key : CoroutineContext.Key<CoroutineExceptionHandler>

    fun onError(error: Throwable) {
        error.printStackTrace()
        onErrorAction(error)
    }
}