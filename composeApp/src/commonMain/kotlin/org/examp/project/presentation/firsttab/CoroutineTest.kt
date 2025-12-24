package org.examp.project.presentation.firsttab

import io.github.aakira.napier.Napier
import kotlinx.coroutines.delay
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
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

class MyCoroutineName(val name: String) : AbstractCoroutineContextElement(key = Key) {
    companion object Key : CoroutineContext.Key<MyCoroutineName>
}

class MyCoroutineExceptionHandler(val onErrorAction: (Throwable) -> Unit) :
    AbstractCoroutineContextElement(key = Key) {
    companion object Key : CoroutineContext.Key<MyCoroutineExceptionHandler>

    fun onError(error: Throwable) {
        error.printStackTrace()
        onErrorAction(error)
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

    // 协程作用域
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

    // 协程上下文
    fun coroutineContextTest() {
        val continuation = suspend {
            5
        }.createCoroutine(object : Continuation<Int> {

            override val context: CoroutineContext
                get() = EmptyCoroutineContext + MyCoroutineName("pupu") + MyCoroutineExceptionHandler { e ->
                    Napier.e { e.printStackTrace().toString() }
                }


            override fun resumeWith(result: Result<Int>) {
                result.onFailure {
                    context[MyCoroutineExceptionHandler]?.onError(it)
                }
            }
        })

    }

    // 协程拦截器
    fun coroutineIntercept() {
        val continuation = suspend {

            4
        }.startCoroutine(object : Continuation<Int> {
            override val context: CoroutineContext
                get() = LogInterceptor()

            override fun resumeWith(result: Result<Int>) {
                ////
            }
        })
    }
}


class LogInterceptor : ContinuationInterceptor {
    override val key: CoroutineContext.Key<*>
        get() = ContinuationInterceptor // 拦截器的key是一个固定实现

    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
        return LogContinuation(continuation)
    }

    class LogContinuation<T>(private val continuation: Continuation<T>) :
        Continuation<T> by continuation {
        override fun resumeWith(result: Result<T>) {
            Napier.e(message = "执行log")
            continuation.resumeWith(result)
        }
    }
}
