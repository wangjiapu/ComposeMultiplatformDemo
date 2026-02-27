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

    /**
     * 包含min函数的栈
     * push时：若新元素≤minStack栈顶（或minStack为空），则将新元素压入minStack；否则不压入（复用当前最小值）；
     * pop时：若弹出的元素等于minStack栈顶（说明当前最小值被弹出），则同步弹出minStack栈顶；
     * min时：直接返回minStack栈顶（当前栈的最小值）。
     */
    inner class MinStack{
        // 主栈：存储所有元素
        private val dataStack = ArrayDeque<Int>()
        // 最小栈：存储每个状态的最小值（仅压入更小/相等的元素）
        private val minStack = ArrayDeque<Int>()

        /**
         * 入栈操作
         * 时间复杂度：O(1)
         */
        fun push(value: Int) {
            // 1. 压入主栈
            dataStack.addLast(value)
            // 2. 压入最小栈：若最小栈为空 或 新元素≤当前最小值，同步压入
            if (minStack.isEmpty() || value <= minStack.first()) {
                minStack.addLast(value)
            }
        }

        /**
         * 出栈操作（弹出栈顶元素）
         * 时间复杂度：O(1)
         * @return 弹出的元素；若栈为空，返回null（也可抛异常）
         */
        fun pop(): Int? {
            // 空栈处理
            if (dataStack.isEmpty()) {
                return null
            }
            // 1. 弹出主栈顶元素
            val popped = dataStack.removeFirst()
            // 2. 若弹出的是当前最小值，同步弹出最小栈顶
            if (popped == minStack.first()) {
                minStack.removeFirst()
            }
            return popped
        }

        /**
         * 获取栈顶元素（不弹出）
         * 时间复杂度：O(1)
         * @return 栈顶元素；若栈为空，返回null
         */
        fun top(): Int? {
            return if (dataStack.isEmpty()) null else dataStack.first()
        }

        /**
         * 获取栈中最小元素
         * 时间复杂度：O(1)
         * @return 最小元素；若栈为空，返回null
         */
        fun min(): Int? {
            return if (minStack.isEmpty()) null else minStack.first()
        }

        /**
         * 辅助方法：判断栈是否为空（提升鲁棒性）
         */
        fun isEmpty(): Boolean {
            return dataStack.isEmpty()
        }

        /**
         * 辅助方法：获取栈大小
         */
        fun size(): Int {
            return dataStack.size
        }
    }
}