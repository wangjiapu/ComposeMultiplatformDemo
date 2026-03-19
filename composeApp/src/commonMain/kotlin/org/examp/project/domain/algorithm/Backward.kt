package org.examp.project.domain.algorithm

/**
 * 递归/回溯
 */
class Backward {
    /**
     * 没有重复项数字的全排列
     */
    fun permute(num: IntArray): Array<IntArray> {
        val result = mutableListOf<IntArray>()
        fun swap(i: Int, j: Int) {
            val temp = num[i]
            num[i] = num[j]
            num[j] = temp
        }

        fun backTrack(start: Int) {
            if (start == num.size) {
                result.add(num.clone())
                return
            }
            for (i in start until num.size) {
                swap(start, i)
                backTrack(start + 1)
                swap(start, i)
            }
        }
        backTrack(0)
        return result.toTypedArray()
    }
}