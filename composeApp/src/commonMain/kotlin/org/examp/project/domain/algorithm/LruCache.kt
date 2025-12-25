package org.examp.project.domain.algorithm

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock


class LruCache<K, V>(private val capacity: Int = 10) {
    private class Node<K, V>(
        var value: V?,
        var prev: Node<K, V>? = null,
        var next: Node<K, V>? = null
    )

    private val cache = hashMapOf<K, Node<K, V>>()
    private val head = Node<K, V>(null, null)
    private val tail = Node<K, V>(null, null)
    private val lock = Mutex()


    init {
        head.next = tail
        tail.prev = head
    }

    suspend fun size(): Int = lock.withLock { cache.size }

    suspend fun containsKey(key: K): Boolean = lock.withLock { cache.containsKey(key) }


    suspend fun put(key: K, value: V) = lock.withLock {
        var node = cache[key]
        if (node == null) {
            node = Node(value)
            cache[key] = node
            addToHead(node)
        } else {
            node.value = value
            remove(node)
            addToHead(node)
        }
        if (cache.size > capacity) {
            val tailNode = tail.prev!!
            remove(tailNode)
        }
    }

    suspend fun get(key: K): Int = lock.withLock {
        val node = cache[key] ?: return -1
        remove(node)
        addToHead(node)
        return -1
    }

    private fun remove(node: Node<K, V>) {
        node.prev?.next = node.next
        node.next?.prev = node.prev
    }

    private fun addToHead(node: Node<K, V>) {
        // 这个是重点
        node.next =head.next
        head.next?.prev=node
        head.next=node
        node.prev=head
    }
}