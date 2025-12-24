package org.examp.project.domain.algorithm

import org.examp.project.domain.core.ListNode

fun initListNode(): ListNode {
    val values = listOf(1, 2)
    val dummyHead = ListNode(0)
    var current = dummyHead
    for (value in values) {
        current.next = ListNode(value)
        current = current.next!!
    }
    return dummyHead.next!!
}

fun initListNode2(): Pair<ListNode?, ListNode?> {
    val values1 = listOf(1, 3, 5, 7)
    val values2 = listOf(2, 4, 6, 8)

    fun createList(values: List<Int>): ListNode? {
        val dummyHead = ListNode(0)
        var current = dummyHead
        for (value in values) {
            current.next = ListNode(value)
            current = current.next!!
        }
        return dummyHead.next
    }

    return Pair(createList(values1), createList(values2))
}

fun initListNode3(): Array<ListNode?> {
    val list1 = listOf(1, 4, 5)
    val list2 = listOf(1, 3, 4)
    val list3 = listOf(2, 6)

    fun createList(values: List<Int>): ListNode? {
        val dummyHead = ListNode(0)
        var current = dummyHead
        for (value in values) {
            current.next = ListNode(value)
            current = current.next!!
        }
        return dummyHead.next
    }

    return arrayOf(createList(list1), createList(list2), createList(list3))
}


fun main() {
    println("\n------------------main-----------------start!!")
//    mergeKLists(initListNode3())?.let {
//        var current: ListNode? = it
//        if (current == null) {
//            println("ListNode is null")
//            return
//        }
//        while (current != null) {
//            print("${current.value} -> ")
//            current = current.next
//        }
//    }
    println(hasCycle(initListNode()))
    println("\n------------------main-----------------end!!")
}


fun hasCycle(head: ListNode?): Boolean {
    if (head == null) return false
    var slow = head
    var fast = head
    while (fast != null && fast.next != null) {
        fast = fast.next?.next
        slow = slow?.next
        if (slow == fast) {
            // 注意，这一步应该在上面处理的后面
            return true
        }
    }
    return false
}

fun mergeKLists(lists: Array<ListNode?>): ListNode? {
    if (lists.isEmpty()) return null
    val dummyNode = ListNode(-1)
    var current: ListNode? = dummyNode
//    while (true) {
//        var index = 0 // 出错
//        var minP: ListNode? = null
//        newList.forEachIndexed { i, node ->
//            if (node != null && (minP == null || node.value < minP.value)) {
//                minP = node
//                index = i
//            }
//        }
//
//        if (minP == null) {
//            break
//        }
//        current?.next = minP
//        current = current?.next
//        newList[index] = minP.next
//    }

    while (true) {
        // 使用kotlin内置函数更方便
        val entry = lists
            .withIndex()
            .filter { it.value != null }
            .minByOrNull { it.value!!.value }
        if (entry == null) {
            break
        }
        val minNode = entry.value!!
        current?.next = minNode
        current = current?.next
        lists[entry.index] = minNode.next

    }
    return dummyNode.next
}


// 合并两个有序链表
fun mergeListNode(pHead1: ListNode?, pHead2: ListNode?): ListNode? {
    val dummyNode = ListNode(-1)
    var p1 = pHead1
    var p2 = pHead2
    var current: ListNode? = dummyNode
    while (p1 != null && p2 != null) {
        if (p1.value < p2.value) {
            current?.next = p1
            p1 = p1.next
        } else {
            current?.next = p2
            p2 = p2.next
        }
        current = current?.next
    }

    if (p1 != null) {
        current?.next = p1
    }
    if (p2 != null) {
        current?.next = p2
    }
    return dummyNode.next
}

// K 个一组翻转链表
fun reverseKGroup(head: ListNode? = null, k: Int): ListNode? {
    if (head == null || k <= 1) {
        return head
    }
    val dummyNode: ListNode? = ListNode(-1).apply {
        next = head
    }
    var left = 0
    var right = 0

    var flagNode = head
    // 分组
    while (flagNode != null) {
        // 这个需要写在reverseBetween之前，这样可以保证left和right是正确的
        right++
        flagNode = flagNode.next
        if (right - left == k) {
            dummyNode?.next = reverseBetween(dummyNode.next, left + 1, right)
            left = right
        }
    }
    return dummyNode?.next
}

/**
 * 反转链表 [m, n] 区间
 */
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