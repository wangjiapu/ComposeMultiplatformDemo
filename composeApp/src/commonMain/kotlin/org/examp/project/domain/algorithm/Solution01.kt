package org.examp.project.domain.algorithm

class Solution01 {
    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     * 旋转数组
     * @param n int整型 数组长度
     * @param m int整型 右移距离
     * @param a int整型一维数组 给定数组
     * @return int整型一维数组
     */
    fun solve(n: Int, m: Int, a: IntArray): IntArray {
        var count = m % n
        while (count > 0) {
            val temp = a[n - 1]
            for (i in n - 1 downTo 1) {
                a[i] = a[i - 1]
            }
            a[0] = temp
            count--
        }
        return a
    }

    fun slove2(n: Int, m: Int, a: IntArray): IntArray {
        var count = m % n
        reverse(a, 0, n - 1)
        reverse(a, 0, count - 1)
        reverse(a, count, n - 1)
        return a
    }

    private fun reverse(a: IntArray, start: Int, end: Int) {
        var s = start
        var e = end
        while (s < e) {
            var temp = a[s]
            a[s] = a[e]
            a[e] = temp
            s++
            e--
        }
    }

}
/**
 * 问：三次反转法这种思路，还能应用到哪些数组 / 字符串的面试题中？
 * 答：这是数组 / 字符串操作的通用模板，常见应用场景：
 * 字符串的循环移位（如 “abcdef” 右移 2 位→“efabcd”）；
 * 反转字符串中的单词（如 “hello world”→“world hello”，先反转整体，再反转每个单词）；
 * 数组的循环左移 / 右移（本题的变形）；
 * 核心都是将复杂的整体移位，拆解为简单的局部反转。
 */