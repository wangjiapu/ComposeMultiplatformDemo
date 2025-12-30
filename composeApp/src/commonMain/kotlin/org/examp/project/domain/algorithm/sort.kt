package org.examp.project.domain.algorithm

// 归并排序
fun mergeSort(nums: IntArray): IntArray {
    fun merge(nums: IntArray, left: Int, mid: Int, right: Int, temp: IntArray) {
        var i = left
        var j = mid
        var index = 0

        while (i < mid && j < right) {
            temp[index++] = if (nums[i] > nums[j]) nums[j++] else nums[i++]
        }
        while (i < mid) {
            temp[index++] = nums[i++]
        }
        while (j < right) {
            temp[index++] = nums[j++]
        }
        for (k in 0 until index) {
            nums[left + k] = temp[k]
        }
    }

    fun sort(nums: IntArray, left: Int, right: Int, temp: IntArray) {
        if (left > right) {
            return
        }
        val mid = (right - left) / 2 + left
        sort(nums, left, mid, temp)
        sort(nums, mid, right, temp)
        merge(nums, left, mid, right, temp)
    }

    var left = 0
    var right = nums.size
    sort(nums, left, right, IntArray(nums.size))
    return nums
}