package org.examp.project.domain.core

import java.util.PriorityQueue

actual class PriorityQueueCompat<T> actual constructor(
    private val comparator: Comparator<T>
) {
    private val delegate = PriorityQueue(comparator)
    actual fun add(element: T){
        delegate.add(element)
    }
    actual fun poll() = delegate.poll()
    actual fun peek() = delegate.peek()
    actual fun isEmpty() = delegate.isEmpty()
}