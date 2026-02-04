package org.examp.project.domain.core

actual class PriorityQueueCompat<T> actual constructor(
    private val comparator: Comparator<T>
) {
    private val list = mutableListOf<T>()

    actual fun add(element: T) {
        list.add(element)
        list.sortWith(comparator)
    }

    actual fun poll(): T? = if (list.isNotEmpty()) list.removeFirst() else null


    actual fun peek(): T? = list.firstOrNull()

    actual fun isEmpty(): Boolean = list.isEmpty()
}

actual class LinkedListCompat<T> {
    actual constructor()
    private val delegate = mutableListOf<T>()

    actual fun offer(element: T) {
        delegate.add(element)
    }

    actual fun poll(): T? = if (delegate.isNotEmpty()) {
        delegate.removeFirst()
    } else {
        null
    }

    actual fun peek(): T? = delegate.firstOrNull()

    actual fun isEmpty(): Boolean = delegate.isEmpty()

    actual fun size(): Int = delegate.size
}