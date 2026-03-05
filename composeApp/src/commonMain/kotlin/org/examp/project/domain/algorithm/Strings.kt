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

    /**
     * 验证IP地址
     */
    fun solve(IP: String): String {
        if (IP.isEmpty()) {
            return "Neither"
        }
        if (IP.contains(":")) {
            if (!isValidIPv6(IP)) {
                return "Neither"
            }
            return "IPv6"
        }
        if (IP.contains(".")) {
            if (!isValidIPv4(IP)) {
                return "Neither"
            }
            return "IPv4"
        }

        return "Neither"
    }

    private fun isValidIPv4(ip: String): Boolean {
        val segments = ip.split(".")
        if (segments.size != 4) {
            return false
        }
        for (seg in segments) {
            if (seg.isEmpty() || seg.length > 3) return false
            // 规则3：不能有前导零（长度>1且以0开头）
            if (seg.length > 1 && seg[0] == '0') return false
            // 规则4：必须全为数字
            for (c in seg) {
                if (!c.isDigit()) return false
            }
            // 规则5：数值必须≤255
            val num = seg.toInt()
            if (num < 0 || num > 255) return false
        }
        return true
    }


    private fun isValidIPv6(ip: String): Boolean {
        // 分割分段（split(":")会分割出空段，如"2001:0db8::8a2e:0370:7334" → ["2001","0db8","","8a2e","0370","7334"]）
        val segments = ip.split(':')
        // 规则1：必须恰好8段
        if (segments.size != 8) return false

        // 合法的十六进制字符集合（大小写）
        val hexChars = setOf(
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f',
            'A', 'B', 'C', 'D', 'E', 'F'
        )

        for (seg in segments) {
            // 规则2：分段不能为空，且长度≤4
            if (seg.isEmpty() || seg.length > 4) return false
            // 规则3：必须全为合法十六进制字符
            for (c in seg) {
                if (c !in hexChars) return false
            }
        }
        return true
    }

    /**
     * 大数加法
     */
    fun solve(s: String, t: String): String {
        var result = StringBuilder()
        var sLen = s.length - 1
        var tLen = t.length - 1
        var car = 0
        while (sLen >= 0 || tLen >= 0 || car > 0){
            val sum = if (sLen >= 0) {
                s[sLen] - '0'
            } else {
                0
            } + if (tLen >= 0) {
                t[tLen]-'0'
            } else {
                0
            } + car
            result.append(sum % 10)
            car = sum / 10
            sLen--
            tLen--
        }

        return result.reverse().toString()
    }
}