package org.examp.project.domain.core

import java.util.LinkedList

actual class PriorityQueueCompat<T> actual constructor(
    private val comparator: Comparator<T>
) {
    private val delegate = java.util.PriorityQueue(comparator)
    actual fun add(element: T) {
        delegate.add(element)
    }

    actual fun poll() = delegate.poll()
    actual fun peek() = delegate.peek()
    actual fun isEmpty() = delegate.isEmpty()
    actual fun size() = delegate.size
}

actual class LinkedListCompat<T> {
    private val delegate = LinkedList<T>()
    actual fun offer(element: T) {
        delegate.offer(element)
    }

    actual fun poll() = delegate.poll()
    actual fun peek() = delegate.peek()
    actual fun isEmpty() = delegate.isEmpty()
}