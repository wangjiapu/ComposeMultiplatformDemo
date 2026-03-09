package org.examp.project.domain.algorithm

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
}