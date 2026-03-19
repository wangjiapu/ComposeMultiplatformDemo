package org.examp.project.domain.algorithm

import org.examp.project.domain.core.PriorityQueueCompat

class Greedy {

    /**
     * 分糖果问题
     */
    fun candy(arr: IntArray): Int {
        val n = arr.size
        if (n <= 1) {
            return n
        }

        val candies = IntArray(n) { 1 }
        // 从左往右
        for (i in 1 until n) {
            if (arr[i] > arr[i - 1]) {
                candies[i] = candies[i - 1] + 1
            }
        }
        // 从右往左
        for (i in n - 2 downTo 0) {
            if (arr[i] > arr[i + 1]) {
                candies[i] = maxOf(candies[i], candies[i + 1] + 1)
            }
        }

        return candies.sum()
    }

    /**
     * 主持人调度（二）
     */
    fun minmumNumberOfHost(n: Int, startEnd: Array<IntArray>): Int {
        if (n <= 1) {
            return n
        }
        // 排序
        startEnd.sortWith(compareBy({ it[0] }, { it[1] }))
        val minHeap = PriorityQueueCompat<Int>(comparator = { a, b -> a - b })
        minHeap.add(startEnd[0][1])
        for (i in 1 until startEnd.size) {
            if (minHeap.peek()!! <= startEnd[i][0]) {
                minHeap.poll()
            }
            minHeap.add(startEnd[i][1])
        }

        return minHeap.size()
    }

    /**
     * 合并区间
     */
    class Interval {
        var start: Int = 0 //起点
        var end: Int = 0 //终点
        override fun toString(): String {
            return "[${start},${end}]"
        }
    }

    fun merge(intervals: Array<Interval?>): Array<Interval?> {
        if (intervals.size <= 1) {
            return intervals
        }
        intervals.sortWith(compareBy({ it?.start }, { it?.end }))
        val result = mutableListOf<Interval>()
        intervals[0]?.let {
            result.add(it)
        }
        for (i in 1..<intervals.size) {
            intervals[i]?.let {
                val tempInterval = result[result.size - 1]
                if (tempInterval.end >= it.start) {
                    tempInterval.end = maxOf(it.end, tempInterval.end)
                } else {
                    result.add(it)
                }
            }
        }
        return result.toTypedArray()
    }


}