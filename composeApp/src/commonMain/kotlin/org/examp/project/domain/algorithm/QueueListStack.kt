package org.examp.project.domain.algorithm

import org.examp.project.domain.core.PriorityQueueCompat

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
    inner class MinStack {
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

    /**
     * 有效括号
     */
    fun isValid(s: String): Boolean {
        val stack = ArrayDeque<Char>()
        val chs = s.toCharArray()
        val bracketMap = mapOf(
            ')' to '(',
            ']' to '[',
            '}' to '{'
        )

        fun removeCh(ch: Char): Boolean {
            if (stack.isEmpty() || stack.last() != bracketMap[ch]) {
                return false
            } else {
                stack.removeLast()
            }
            return true
        }
        chs.forEach {
            when (it) {
                '(', '[', '{' -> stack.addLast(it)
                else -> {
                    if (!removeCh(it)) {
                        return false
                    }
                }
            }

        }
        return stack.isEmpty()
    }

    /**
     * 滑动窗口的最大值
     */
    fun maxInWindows(nums: IntArray, k: Int): IntArray {
        if (nums.isEmpty() || k <= 0 || k > nums.size) return intArrayOf()
        if (k == 1) return nums // 窗口大小为1，最大值就是元素本身

        // 结果数组：长度 = nums.size - k + 1
        val result = IntArray(nums.size - k + 1)
        // 双端队列：存储nums索引，保证对应值单调递减
        val deque = ArrayDeque<Int>()

        for (i in nums.indices) {
            // 步骤1：移除超出窗口的队头元素（不在[i-k+1, i]范围内）
            while (deque.isNotEmpty() && deque.first() <= i - k) {
                deque.removeFirst()
            }

            // 步骤2：维护队列单调性：移除队尾≤当前值的索引（这些值不可能是后续窗口的最大值）
            while (deque.isNotEmpty() && nums[deque.last()] <= nums[i]) {
                deque.removeLast()
            }

            // 步骤3：加入当前索引到队尾
            deque.addLast(i)

            // 步骤4：窗口已形成（i ≥ k-1），记录最大值（队头对应的值）
            if (i >= k - 1) {
                result[i - k + 1] = nums[deque.first()]
            }
        }

        return result
    }


    /**
     * 最小的K个数
     */
    fun getLeastNumbersSolution(input: IntArray, k: Int): IntArray {
        if (input.isEmpty() || k <= 0) {
            return intArrayOf()
        }
        if (k >= input.size) {
            return input
        }
        val result = IntArray(k)
        val queue = PriorityQueueCompat<Int>(comparator = Comparator { a, b -> a - b })
        // val maxHeap=priorityQueue<Int>(k,compareByDescending { it })

        input.forEach {
            queue.add(it)
        }
        repeat(k) {
            result[it] = queue.poll() ?: 0
        }
        return result
    }

    fun findKth(a: IntArray, n: Int, K: Int): Int {
        /**
         * 思路一：排序后取值
         * 思路二：使用小根堆
         * 思路三：使用快速排序的思想
         */
        val targetIndex = n - K
        return quickSort(a, 0, n - 1, targetIndex)
    }

    fun mySort(nums: IntArray) {
        quickSort(nums, 0, nums.size - 1, 1)
    }

    fun quickSort(nums: IntArray, left: Int, right: Int, targetIndex: Int): Int {
        val pivot = partition(nums, left, right)
        return when {
            targetIndex == pivot -> nums[targetIndex]
            targetIndex < pivot -> quickSort(nums, left, pivot - 1, targetIndex)
            else -> quickSort(nums, pivot + 1, right, targetIndex)
        }
    }

    fun partition(nums: IntArray, left: Int, right: Int): Int {
        val mid = midTree(nums, left, right)
        swap(nums, left, mid)
        var i = left
        var j = right
        while (i < j) {
            while (i < j && nums[j] >= nums[left]) {
                j--
            }
            while (i < j && nums[i] <= nums[left]) {
                i++
            }
            swap(nums, i, j)
        }
        swap(nums, i, left)
        return i
    }

    fun midTree(nums: IntArray, left: Int, right: Int): Int {
        return left
    }

    fun swap(nums: IntArray, i: Int, j: Int) {
        val temp = nums[i]
        nums[i] = nums[j]
        nums[j] = temp
    }

    inner class Median() {
        private val minHeap = PriorityQueueCompat<Int>(comparator = Comparator { a, b -> a - b })
        private val maxHeap = PriorityQueueCompat<Int>(comparator = Comparator { a, b -> b - a })

        fun insert(num: Int) {
            minHeap.add(num)
            val m = minHeap.poll()
            m?.let {
                maxHeap.add(it)
            }
            if (maxHeap.size() > minHeap.size()) {
                val n = maxHeap.poll()
                n?.let {
                    minHeap.add(it)
                }
            }
        }

        fun getMedian(): Double? {
            return if (minHeap.size() > maxHeap.size()) {
                minHeap.peek()?.toDouble() ?: 0.0
            } else {
                val maxNum = maxHeap.peek()
                if (maxNum == null) {
                    return 0.0
                }
                (minHeap.peek()?.plus(maxNum))?.div(2.0)
            }
        }
    }
}