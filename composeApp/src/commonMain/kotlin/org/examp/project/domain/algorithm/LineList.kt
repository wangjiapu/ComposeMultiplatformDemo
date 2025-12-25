package org.examp.project.domain.algorithm

import org.examp.project.domain.core.ListNode

// WIP: 两个链表相加
fun addInList(head1: ListNode?, head2: ListNode?): ListNode? {
    // 解法1:反转链表
    fun baseReverseList(head1: ListNode?, head2: ListNode?): ListNode? {
        if (head1 == null) return head2
        if (head2 == null) return head1

        fun reverseListNode(head: ListNode?): ListNode? {

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

        var newHead1 = reverseListNode(head1)
        var newHead2 = reverseListNode(head2)
        val newDummyNode = ListNode(-1)
        var cap = 0

        fun headInsert(node: ListNode, head: ListNode): ListNode {
            node.next = head.next
            head.next = node
            return head
        }

        while (newHead1 != null || newHead2 != null) {
            val sum = if (newHead1?.value == null) {
                0
            } else {
                newHead1.value
            } + if (newHead2?.value == null) {
                0
            } else {
                newHead2.value
            } + cap

            val newNode = ListNode(sum % 10)
            cap = sum / 10
            headInsert(newNode, newDummyNode)
            newHead1 = newHead1?.next
            newHead2 = newHead2?.next
        }

        if (cap != 0) {
            val newNode = ListNode(cap)
            headInsert(newNode, newDummyNode)
        }
        return newDummyNode.next
    }

    // 解法2:使用栈
    fun baseStack(head1: ListNode?, head2: ListNode?): ListNode? {
        val stack1: ArrayDeque<ListNode> = ArrayDeque<ListNode>()
        val stack2 = ArrayDeque<ListNode>()
        var p1 = head1
        var p2 = head2
        while (p1 != null) {
            stack1.addLast(p1)
            p1 = p1.next
        }
        while (p2 != null) {
            stack2.addLast(p2)
            p2 = p2.next
        }
        val dummyNode = ListNode(-1)
        var cap = 0

        fun insertHead(node: ListNode, head: ListNode): ListNode {
            node.next = head.next
            head.next = node
            return head
        }

        while (stack1.isNotEmpty() || stack2.isNotEmpty()) {
            val sum = if (stack1.isEmpty()) {
                0
            } else {
                stack1.removeLast().value
            } + if (stack2.isEmpty()) {
                0
            } else {
                stack2.removeLast().value
            } + cap

            val newNode = ListNode(sum % 10)
            cap = sum / 10
            insertHead(newNode, dummyNode)
        }

        if (cap != 0) {
            insertHead(ListNode(cap), dummyNode)
        }
        return dummyNode.next
    }

    return baseStack(head1, head2)
}

// 两个链表相加
// 这种写法无法满足大数据相加的需求
fun addInListError(head1: ListNode?, head2: ListNode?): ListNode? {
    val dummyNode: ListNode = ListNode(-1)

    var p1 = head1
    var p2 = head2

    fun toNumber(p: ListNode?): Long {
        var num = 0L
        var current = p
        while (current != null) {
            num = num * 10 + current.value
            current = current.next
        }
        return num
    }

    val num1 = toNumber(p1)
    val num2 = toNumber(p2)
    var sum = num1 + num2
    while (sum != 0L) {
        val digit = (sum % 10).toInt()
        val newNode = ListNode(digit)
        if (dummyNode.next == null) {
            dummyNode.next = newNode
        } else {
            newNode.next = dummyNode.next
            dummyNode.next = newNode
        }
        sum = sum / 10
    }
    return dummyNode.next
}

// 两个链表的第一个公共节点
// 需要考虑两个链表长度不一样的情况
fun findFirstCommonNode(pHead1: ListNode?, pHead2: ListNode?): ListNode? {
    var p1 = pHead1
    var p2 = pHead2
    // 这样写肯定错误
//    while (p1 != null && p2 != null) {
//        if (p1 == p2) {
//            return p1
//        }
//        p1 = p1.next
//        p2 = p2.next
//
//    }

    // 解法1:时间复杂度 O(mn)
    while (p1 != null) {
        p2 = pHead2
        while (p2 != null) {
            if (p1 == p2) {
                return p1
            }
            p2 = p2.next
        }
        p1 = p1.next
    }
    // 解法2: 时间复杂度O(m+n)
    while (p1 != p2) {
        p1 = if (p1 == null) {
            pHead2
        } else {
            p1.next
        }
        p2 = if (p2 == null) {
            pHead1
        } else {
            p2.next
        }
    }
    return p1
}

// 删除链表的倒数第N个节点
fun removeNthFromEnd(head: ListNode?, n: Int): ListNode? {
    val dummyNode = ListNode(-1).apply {
        // 删除的时候采用虚拟头节点，避免删除头节点时的特殊处理
        next = head
    }
    //1, 2, 3, 4, 5
    var tempNode = findKthToTail(dummyNode, n + 1)
    if (tempNode == null) {
        return null
    }
    tempNode.next = tempNode.next?.next
    return dummyNode.next
}

// 链表中倒数第k个节点
fun findKthToTail(pHead: ListNode?, k: Int): ListNode? {
    if (pHead == null || k <= 0) return null
    var fast: ListNode? = pHead
    var slow: ListNode? = pHead
    repeat(k - 1) {
        fast = fast?.next
    }
    // 注意：k>链表长度
    if (fast == null) return null
    while (fast?.next != null) {
        fast = fast.next
        slow = slow?.next
    }
    return slow
}

// 环形链表 II  找到环的入口节点
fun entryNodeOrLoop(head: ListNode?): ListNode? {
    //解题1:
    val nodeSet: MutableSet<ListNode> = mutableSetOf()
    var current = head
    while (current != null) {
        if (nodeSet.contains(current)) {
            return current
        }
        nodeSet.add(current)
        current = current.next
    }

    // 解题2:
    if (head == null) return null
    var slow = head
    var fast = head
    while (fast != null && fast.next != null) {
        fast = fast.next?.next
        slow = slow?.next!!
        if (slow == fast) {
            // 找到相遇点
            var ptr1 = head
            while (ptr1 != slow) {
                ptr1 = ptr1?.next
                slow = slow?.next
            }
            return ptr1
        }
    }
    return null
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

// 反转链表
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