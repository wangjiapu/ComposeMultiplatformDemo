package org.examp.project.domain.core

actual class PriorityQueueCompat<T> actual constructor(
    private val comparator: Comparator<T>
) {
    private val list = mutableListOf<T>()

    actual fun add(element: T) {
        list.add(element)
        list.sortWith(comparator) // 简单排序模拟优先级队列
    }

    actual fun poll(): T? = if (list.isNotEmpty()) list.removeFirst() else null
    actual fun peek(): T? = list.firstOrNull()
    actual fun isEmpty(): Boolean = list.isEmpty()
}