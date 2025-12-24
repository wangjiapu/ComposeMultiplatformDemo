package org.examp.project.domain.algorithm

import org.examp.project.domain.core.ListNode

fun main() {
    println("\n------------------main-----------------start!!")
    reverseBetween(initListNode(), 2, 4)?.let {
        var current: ListNode? = it
        if (current == null) {
            println("ListNode is null")
            return
        }
        while (current != null) {
            print("${current.value} -> ")
            current = current.next
        }
    }
    println("\n------------------main-----------------end!!")
}

fun reverseBetween(head: ListNode? = null, m: Int, n: Int): ListNode? {
    if (head == null) return null
    // 创建虚拟头节点还是比较重要的
    var dummyNode: ListNode? = ListNode(-1).apply {
        next = head
    }
    var prev: ListNode? = dummyNode
    for (i in 1 until m) {
        prev = prev?.next
    }
    var current: ListNode? = prev?.next

    var nextTemp: ListNode?
    for (i in m until n) {
        nextTemp = current?.next
        current?.next = nextTemp?.next
        nextTemp?.next = prev?.next
        prev?.next = nextTemp
    }
    return dummyNode?.next
//    if (head == null || n == m) return head
//
//    // 创建虚拟头节点，简化边界处理
//    val dummy = ListNode(0).apply { next = head }
//    var pre: ListNode? = dummy
//
//    // 1. 找到 left 前一个节点
//    repeat(n - 1) {
//        pre = pre?.next
//    }
//
//    // 2. 反转 [left, right] 区间
//    var current = pre?.next
//    var next: ListNode?
//
//    repeat(n-m) {
//        next = current?.next
//        current?.next = next?.next
//        next?.next = pre?.next
//        pre?.next = next
//    }

//    return dummy.next

}

fun reverseListNode(head: ListNode? = null): ListNode? {
    if (head == null) return null
    var prev: ListNode? = null
    var current: ListNode? = head
    var nextTemp: ListNode? = null
    while (current != null) {
        nextTemp = current.next
        current.next = prev
        prev = current
        current = nextTemp
    }
    return prev
}

fun initListNode(): ListNode {
    val values = listOf(1, 2,3,4,5)
    val dummyHead = ListNode(0)
    var current = dummyHead
    for (value in values) {
        current.next = ListNode(value)
        current = current.next!!
    }
    return dummyHead.next!!
}