package org.examp.project.domain.core

class ListNode {
    var value: Int
    var next: ListNode?

    constructor(value: Int) {
        this.value = value
        this.next = null
    }
}

val random = kotlin.random.Random.Default

/**
 * 初始化两个有序链表
 */
fun createTwoList(): Pair<ListNode?, ListNode?> {
    return Pair(createList(5, needSort = true), createList(2, needSort = true))
}

/**
 * 初始化一个链表数组
 */
fun createArrayList(): MutableList<ListNode?> {
    return mutableListOf<ListNode?>().apply {
        repeat(5) {
            add(createList(count = random.nextInt(6), needSort = true))
        }
    }
}

/**
 * 初始化一个链表
 * @param count 链表节点个数
 * @param needSort 是否需要排序
 * @param list 指定链表节点值的数组
 */
fun createList(count: Int = 0, needSort: Boolean = false, list: Array<Int>? = null): ListNode {

    //生成一个100以内随机数的数组
    val values = mutableListOf<Int>()
    if (list.isNullOrEmpty()) {
        for (i in 0 until count) {
            values.add(random.nextInt(100))
        }
        if (needSort) {
            values.sort()
        }
    } else {
        values.addAll(list)
    }
    val dummyHead = ListNode(0)
    var current = dummyHead
    for (value in values) {
        current.next = ListNode(value)
        current = current.next!!
    }
    return dummyHead.next!!
}
