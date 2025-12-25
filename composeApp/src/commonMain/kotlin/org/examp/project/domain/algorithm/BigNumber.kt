package org.examp.project.domain.algorithm

/**
 * 大数相加
 */
class BigNumber(private val value: String) {
    init {
        // todo:有效数字检查
        require(value.matches(Regex("^-?\\d+$"))) { "无效的数字格式" }
    }

    // 判断是否为负数
    private val isNegative: Boolean
        get() = value.startsWith("-")

    // 获取绝对值
    private val absoluteValue: String
        get() = if (isNegative) value.substring(1) else value

    //加法
    operator fun plus(other: BigNumber): BigNumber {
        return when {
            // 两个不为负数
            !this.isNegative && !other.isNegative -> {
                BigNumber(addPositive(this.absoluteValue, other.absoluteValue))
            }
            // 两负数
            this.isNegative && other.isNegative -> {
                BigNumber("-" + addPositive(this.absoluteValue, other.absoluteValue))
            }
            // 先正后负
            //先负后正
            else -> {
                BigNumber("")
            }
        }
    }
    private fun addPositive(num1: String, num2: String): String {
        // 解法1:
        val result = StringBuilder()
        var len1 = num1.length - 1
        var len2 = num2.length - 1
        var car = 0;
        while (len2 >= 0 || len1 >= 0 || car > 0) {
            val sum = if (len2 > 0) {
                num2[len2].code
            } else {
                0
            } + if (len1 > 0) {
                num1[len1].code
            } else {
                0
            } + car
            result.append(sum % 10)
            car = sum / 10
            len1--
            len2--
        }
        return result.reverse().toString()
    }

    // 优化后的写法
    private fun preHandleString(num1: String, num2: String): String {
        // 5位一份65535
        val result = StringBuilder()
        val s1 = num1.chunked(5)
        val s2 = num2.chunked(5)
        var len1 = s1.size - 1
        var len2 = s2.size - 1
        var car = 0
        while (len1 >= 0 || len2 >= 0 || car > 0) {
            val sum = if (len1 >= 0) {
                s1[len1].toInt()
            } else {
                0
            } + if (len2 >= 0) {
                s2[len2].toInt()
            } else {
                0
            } + car
            result.append(sum % 100000)
            car = sum / 100000
            len1--
            len2--
        }
        return result.reverse().toString()
    }

    //减法
    operator fun minus(other: BigNumber): BigNumber {
        return BigNumber("")
    }
}

class BigFloat(private val value: String){
    private val integerPart: String
    private val decimalPart: String
    private val isNegative: Boolean

    init {
        val trimmed = value.trim()
        isNegative = trimmed.startsWith("-")
        val absValue = if (isNegative) trimmed.substring(1) else trimmed

        val parts = absValue.split(".")
        integerPart = parts[0].ifEmpty { "0" }
        decimalPart = if (parts.size > 1) parts[1] else "0"
    }

    //加法
    fun addAbsolute(num1: BigFloat, num2: BigFloat, negative: Boolean): BigFloat {

        // 对齐小数点
        val maxDecimalLength = maxOf(num1.decimalPart.length, num2.decimalPart.length)
        val decimal1 = num1.decimalPart.padEnd(maxDecimalLength, '0')
        val decimal2 = num2.decimalPart.padEnd(maxDecimalLength, '0')

        // 2. 相加小数部分
        val (decimalResult, decimalCarry) = addStrings(decimal1, decimal2)

        // 3. 相加整数部分
        val (integerResult, _) = addStrings(num1.integerPart, num2.integerPart, decimalCarry)

        // 4. 构建结果
        val result = if (negative) "-" else ""
        return BigFloat("$result$integerResult.$decimalResult")
    }

    private fun addStrings(str1: String, str2: String, initialCarry: Int = 0): Pair<String, Int> {
        val result = StringBuilder()
        var carry = initialCarry
        var i = str1.length - 1
        var j = str2.length - 1

        while (i >= 0 || j >= 0 || carry > 0) {
            val digit1 = if (i >= 0) str1[i].digitToInt() else 0
            val digit2 = if (j >= 0) str2[j].digitToInt() else 0

            val sum = digit1 + digit2 + carry
            result.append(sum % 10)
            carry = sum / 10

            i--
            j--
        }

        return result.reverse().toString().trimStart('0').ifEmpty { "0" } to carry
    }
}