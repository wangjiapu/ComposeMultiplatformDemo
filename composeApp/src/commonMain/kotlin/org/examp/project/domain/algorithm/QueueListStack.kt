package org.examp.project.domain.algorithm

import cafe.adriel.voyager.core.stack.Stack

/**
 * 堆、栈、队列
 */
class QueueListStack {
    /**
     * 用两个栈实现队列
     */
    inner class StackToQueue {
        //此处采用stack
        //核心思路是：使用入栈和出栈两个栈，入栈不做处理，在出栈时，判断出栈栈为空，那么将入栈栈的数据全部移到出栈栈中
        // 扩展：如果使用两个队列实现一个栈：每次入队后，将前 n-1 个元素迁移到另一个队列
        private val inStack = ArrayDeque<Int>()
        private val outStack = ArrayDeque<Int>()
        fun push(node: Int) {
            inStack.addLast(node)
        }

        fun pop(): Int {
            if (outStack.isEmpty()) {
                while (inStack.isNotEmpty()) {
                    outStack.addFirst(inStack.removeFirst())
                }
            }
            return outStack.removeFirst()
        }
    }
}