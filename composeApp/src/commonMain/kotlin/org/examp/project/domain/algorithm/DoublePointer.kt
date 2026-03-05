package org.examp.project.domain.algorithm

class DoublePointer {

    /**
     * 合并两个有序数组
     */
    fun merge(A: IntArray, m: Int, B: IntArray, n: Int): IntArray {
        var a = m - 1
        var b = n - 1
        var index = m + n - 1
        while (a >= 0 || b >= 0) {

            A[index] = if (a >= 0 && b >= 0) {
                if (A[a] > B[b]) {
                    A[a--]
                } else {
                    B[b--]
                }
            } else if (a >= 0) {
                A[a--]
            } else {
                B[b--]
            }
            index--
        }
        return A
    }

    /**
     *  判断是否为回文字符串
     */
    fun judge(str: String): Boolean {
        var len = str.length
        if (len <= 0)
            return false
        var left = 0
        var right = len - 1
        while (left < right) {
            if (str[left] != str[right])
                return false
            left++
            right--
        }
        return true
    }

}