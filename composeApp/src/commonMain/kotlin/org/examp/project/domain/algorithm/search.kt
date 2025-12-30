package org.examp.project.domain.algorithm


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