package org.examp.project.domain.algorithm


// 二分查找-I
fun search(nums: IntArray, target: Int): Int {
    var left = 0
    var right = nums.size
    // 二分查找的重点是，左闭右开
    while (left < right) {
        var mid = (left + (right - left)/2)
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