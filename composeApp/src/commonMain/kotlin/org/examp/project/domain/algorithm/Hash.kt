package org.examp.project.domain.algorithm

class Hash {
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
}