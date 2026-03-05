package org.examp.project.domain.algorithm

class Strings {
    /**
     *字符串变形
     */
    fun trans(s: String, n: Int): String {
        if (n == 0) {
            return s
        }
        val swapped = s.map {
            if (it.isUpperCase()) it.lowercase() else it.uppercase()
        }.joinToString("")

        val stack = ArrayDeque<String>()
        val strs = swapped.split(" ")
        strs.forEach {
            stack.addFirst(it)
        }
        var sb = StringBuilder()
        while (stack.isNotEmpty()) {
            sb.append(stack.removeFirst())
            sb.append(" ")
        }
        return sb.toString().trim()
    }

    private fun reverseStr(s: String): String {
        var chars = s.toCharArray()
        var left = 0
        var right = s.length - 1
        while (left < right) {
            var temp = chars[left]
            chars[left] = chars[right]
            chars[right] = temp
            left++
            right--
        }
        return chars.concatToString()
    }

    /**
     * 最长公共前缀
     */
    fun longestCommonPrefix(strs: Array<String>): String {
        if (strs.isEmpty()) {
            return ""
        }
        strs.sort()
        val first = strs[0]
        val last = strs[strs.size - 1]
        val len = minOf(last.length, first.length)
        for (i in 0 until len) {
            if (first[i] != last[i]) {
                return first.substring(0, i)
            }
        }
        return first.substring(0, len)
    }

}