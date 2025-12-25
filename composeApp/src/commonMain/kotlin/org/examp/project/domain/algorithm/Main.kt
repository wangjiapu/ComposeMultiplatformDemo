package org.examp.project.domain.algorithm

import org.examp.project.domain.core.ListNode
import org.examp.project.domain.core.createList

fun main() {
    println("\n------------------main-----------------start!!")
    addInList(createList(list = arrayOf(9, 3, 7)),createList(list = arrayOf(6, 3)))?.let {
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
    // println(findKthToTail(initListNode(), 1)?.value)
    println("\n------------------main-----------------end!!")
}