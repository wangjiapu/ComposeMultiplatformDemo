package org.examp.project.domain.algorithm

import composemultiplatformdemo.composeapp.generated.resources.Res

class Hash {
    /**
     * 两数之和
     */
    fun twoSum(numbers: IntArray, target: Int): IntArray {
        val hashMap = HashMap<Int, Int>(numbers.size)
        numbers.forEachIndexed { index, value ->
            if (hashMap.containsKey(target - numbers[index])) {
                return intArrayOf(hashMap[target - numbers[index]]?.plus(1) ?: 0, index + 1)
            } else {
                hashMap[numbers[index]] = index
            }
        }
        return intArrayOf()
    }

    /**
     * 数组中出现次数超过一半的数字
     */
    fun moreThanHalfNum(numbers: IntArray): Int {
        val hashMap = HashMap<Int, Int>()
        numbers.forEach {
            if (hashMap.containsKey(it)) {
                hashMap[it] = hashMap[it]?.plus(1) ?: 0
            } else {
                hashMap[it] = 1
            }
            hashMap[it]?.let { it1 ->
                if (it1 > numbers.size / 2) {
                    return it
                }
            }
        }
        return 0
    }

    /**
     * 数组中只出现一次的两个数字
     */
    fun findNumsAppearOnce(nums: IntArray): IntArray {
        val hashMap = HashMap<Int, Int>()
        nums.forEachIndexed { index, value ->
            if (hashMap.containsKey(value)) {
                hashMap.remove(value)
            } else {
                hashMap[value] = index
            }
        }
        val res = hashMap.values.toIntArray()
        return if (nums[res[0]] > nums[res[1]]) {
            intArrayOf(nums[res[1]], nums[res[0]])
        } else {
            intArrayOf(nums[res[0]], nums[res[1]])
        }
    }


    /**
     * 缺失的第一个正整数
     */
    fun minNumberDisappeared(nums: IntArray): Int {
        val n = nums.size
        if (n == 0) {
            return 1
        }
        for (i in 0 until n) {
            // 使用while保证新换过来的数，也要放到正确的位置上
            while (nums[i] in 1..n && nums[nums[i] - 1] != nums[i]) {
                val index = nums[i] - 1
                val temp = nums[i]
                nums[i] = nums[index]
                nums[index] = temp
            }
        }
        for (i in 0 until n) {
            if (nums[i] != i + 1) {
                return i + 1
            }
        }
        return n + 1
    }

    /**
     * 三数之和
     */
    fun threeSum(num: IntArray): Array<IntArray> {
        val result = mutableListOf<IntArray>()
        val n = num.size
        if (n < 3) {
            return result.toTypedArray()
        }
        num.sort()
        for (i in 0 until n - 2) {
            if (num[i] > 0) {
                break
            }
            if (i > 0 && num[i] == num[i - 1]) {
                continue
            }
            val target = -num[i]
            var left = i + 1
            var right = n - 1
            while (left < right) {
                val sum = num[left] + num[right]
                when {
                    sum == target -> {
                        result.add(intArrayOf(num[i], num[left], num[right]))
                        while (left < right && num[left] == num[left + 1]) {
                            left++
                        }
                        while (left < right && num[right] == num[right - 1]) {
                            right--
                        }
                        left++
                        right--
                    }

                    sum < target -> {
                        left++
                    }

                    else -> {
                        right--
                    }
                }
            }
        }
        return result.toTypedArray()
    }
}