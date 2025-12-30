package org.examp.project.domain.algorithm

import kotlin.ranges.until

//旋转数组的最小数字
fun minNumberInRotateArray(nums: IntArray): Int {
    var l = 0
    var r = nums.size - 1
    while (l < r) {
        val mid = (r - l) / 2 + l
        if (nums[mid] > nums[r]) {
            l = mid + 1
        } else if (nums[mid] < nums[r]) {
            r = mid
        } else {
            r--
        }
    }
    return nums[l]
}

//数组中的逆序对
fun inversePairs(nums: IntArray): Int {
    fun merge(nums: IntArray, left: Int, mid: Int, right: Int, temp: IntArray): Long {
        var i = left
        var j = mid + 1
        var index = 0
        var result = 0L
        while (i <= mid && j <= right) {
            if (nums[i] <= nums[j]) {
                temp[index++] = nums[i++]
            } else {
                result += (mid + 1 - i)
                temp[index++] = nums[j++]
            }
        }
        while (i <= mid) {
            temp[index++] = nums[i++]
        }
        while (j <= right) {
            temp[index++] = nums[j++]
        }
        for (k in 0 until index) {
            nums[left + k] = temp[k]
        }
        return result
    }

    fun sort(nums: IntArray, left: Int, right: Int, temp: IntArray): Long {
        if (left >= right) {
            return 0
        }
        val mid = (right - left) / 2 + left
        var sum = sort(nums, left, mid, temp) + sort(nums, mid + 1, right, temp)
        return sum + merge(nums, left, mid, right, temp)
    }

    val left = 0
    val right = nums.size - 1
    return (sort(nums, left, right, IntArray(nums.size)) % 1000000007).toInt()
}

// 寻找峰值
fun findPeekElement(nums: IntArray): Int {
    var left = 0
    var right = nums.size - 1
    while (left < right) {
        val mid = (right - left) / 2 + left
        if (nums[mid] < nums[mid + 1]) {
            left = mid + 1
        } else {
            right = mid
        }
    }
    return left
}

//二维数组中的查找
fun find(target: Int, array: Array<IntArray>): Boolean {
    var b = array.size - 1
    if (b < 0) {
        return false
    }
    var l = 0
    var r = array[0].size
    if (r <= 0) {
        return false
    }
    // 主要思路主要是把握住从左下角开始寻找
    while (b >= 0 && l < r) {
        if (array[b][l] < target) {
            l++
        } else if (array[b][l] > target) {
            b--
        } else {
            return true
        }
    }
    return false
}


// 二分查找-I
fun search(nums: IntArray, target: Int): Int {
    var left = 0
    var right = nums.size
    // 二分查找的重点是，左闭右开，循环不加=，初始right为size
    while (left < right) {
        var mid = (left + (right - left) / 2)
        if (nums[mid] < target) {
            left = mid + 1
        } else if (nums[mid] > target) {
            right = mid
        } else {
            return mid
        }
    }
    return -1
}