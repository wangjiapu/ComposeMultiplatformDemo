package org.examp.project.domain.core

expect class PriorityQueueCompat<T> {
    constructor(comparator: Comparator<T>)

    fun add(element: T)
    fun poll(): T?
    fun peek(): T?
    fun isEmpty(): Boolean
}


expect class LinkedListCompat<T> constructor() {
    fun offer(element: T)
    fun poll(): T?
    fun peek(): T?
    fun isEmpty(): Boolean
    fun size():Int
}