package org.examp.project.domain.algorithm

import org.examp.project.domain.core.PriorityQueueCompat
import kotlin.math.max
import kotlin.math.min

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


    /**
     * 反转字符串
     */
    fun solve(str: String): String {
        return str.reversed()
    }

    /**
     * 盛水最多的容器
     */
    fun maxArea(height: IntArray): Int {
        var maxArea = 0
        var left = 0
        var right = height.size - 1

        while (left < right) {
            val w = right - left
            val h = min(height[left], height[right])
            maxArea = max(maxArea, w * h)
            if (height[left] < height[right]) {
                left++
            } else {
                right--
            }
        }
        return maxArea
    }


    /**
     * 接雨水问题
     */
    // 暴力解法
    fun maxWater(arr: IntArray): Long {
        if (arr.size < 3) {
            return 0L
        }
        var totalWater = 0L
        for (i in 1 until arr.size - 1) {
            var leftMaxHeight = 0
            for (j in 0..i) {
                //找到左边最高
                leftMaxHeight = max(leftMaxHeight, arr[j])
            }

            var rightMaxHeight = 0
            for (j in i + 1..<arr.size) {
                // 找到右边最高
                rightMaxHeight = max(rightMaxHeight, arr[j])
            }
            // 计算当前位置可接过少水
            val waterSize = min(leftMaxHeight, rightMaxHeight) - arr[i]
            // 如果当前位置高于左右最高，那么接到的水<0 ,只存储>0的水
            if (waterSize > 0) {
                totalWater += waterSize
            }
        }
        return totalWater
    }

    // 双指针
    fun maxWaterDoublePoint(arr: IntArray): Long {
        if (arr.size < 3) {
            return 0L
        }
        var left = 0
        var right = arr.size - 1

        var leftMax = 0
        var rightMax = 0
        var totalWater = 0L
        while (left < right) {
            leftMax = max(leftMax, arr[left])
            rightMax = max(rightMax, arr[right])
            if (leftMax < rightMax) {
                totalWater += (leftMax - arr[left])
                left++
            } else {
                totalWater += (rightMax - arr[right])
                right--
            }
        }
        return totalWater
    }

    /**
     * 最长无重复子数组
     */
    // 暴力解法
    fun maxLength(arr: IntArray): Int {
        var maxLen = 0
        val setInt = HashSet<Int>()
        arr.forEach {
            if (setInt.contains(it)) {
                maxLen = max(setInt.size, maxLen)
                setInt.clear()
            }
            setInt.add(it)
        }
        return max(maxLen, setInt.size)
    }

    // 双指针+hash
    fun maxLengthDoublePoint(nums: IntArray): Int {
        if (nums.isEmpty()) return 0

        val n = nums.size
        var maxLen = 0 // 最长无重复子数组长度
        var left = 0 // 窗口左边界
        val indexMap = mutableMapOf<Int, Int>() // 元素 -> 最后出现的下标

        for (right in 0 until n) {
            val current = nums[right]
            // 若当前元素在窗口内重复，收缩左边界到重复位置+1
            if (indexMap.containsKey(current) && indexMap[current]!! >= left) {
                left = indexMap[current]!! + 1
            }
            // 更新当前元素的最新下标
            indexMap[current] = right
            // 计算当前窗口长度，更新最大值
            maxLen = maxOf(maxLen, right - left + 1)
        }

        return maxLen
    }

    // 字符串的方法
    fun lengthOfLongestSubstring(s: String): Int {
        if (s.isEmpty()) {
            return 0
        }
        val map = mutableMapOf<Char, Int>()
        var maxLen = 0
        var left = 0
        for (right in s.indices) {
            val currentCh = s[right]
            if (map.containsKey(currentCh) && map[currentCh]!! >= left) {
                left = map[currentCh]!! + 1
            }
            map[currentCh] = right
            maxLen = max(maxLen, right - left + 1)
        }
        return maxLen
    }
}