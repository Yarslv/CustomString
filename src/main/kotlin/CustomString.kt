import java.lang.IndexOutOfBoundsException

class CustomString : CharSequence {

    override val length: Int
    private var charSequence: CharArray

    constructor() {
        length = 0
        charSequence = CharArray(length)
    }

    constructor(charSequence: CharSequence) {
        length = charSequence.length
        this.charSequence = CharArray(charSequence.length)
        for (i in 0 until length) {
            this.charSequence[i] = charSequence[i]
        }
    }

    constructor(initialArray: CharArray) {
        length = initialArray.size
        charSequence = initialArray
    }

    operator fun plus(other: CharSequence): CustomString {
        return concat(other)
    }

    fun concat(other: CharSequence): CustomString {
        with(CharArray(length + other.length)) {
            for (i in this.indices) {
                if (i < charSequence.size) {
                    this[i] = charSequence[i]
                } else {
                    this[i] = other[i - length]
                }
            }
            return CustomString(this)
        }
    }

    fun find(other: CharSequence): Boolean {
        for (i in charSequence.indices) {
            if (charSequence[i] == other[0]) {
                for (j in other.indices) {
                    if (charSequence[i + j] == other[j]) {
                        continue
                    } else {
                        return false
                    }
                }
                return true
            } else {
                continue
            }
        }
        return false
    }

    fun trim(): CustomString {
        var start = 0
        var end = length - 1
        while (charSequence[start] == ' ')
            start++
        while (charSequence[end] == ' ')
            end--
        return subSequence(start, ++end)
    }

    fun toFloat(): Float {
        if (length == 0) {
            throw java.lang.NumberFormatException("empty String")
        }
        checkDotsAndMinus()
        val dotIndex = indexOf('.', 0)
        val containsMinus = indexOf('-', 0) != -1
        when (dotIndex) {
            -1 -> {
                return calculate(0F, length - 1,  0F)
            }
            else -> {
                val wholePart = subSequence(if(containsMinus) 1 else 0, dotIndex).toFloat()
                with(CustomString(subSequence(dotIndex + 1, length))) {
                    var fractionalPart = this.toFloat()
                    for (i in this.indices) {
                        fractionalPart /= 10
                    }
                    return if (containsMinus) -(wholePart + fractionalPart) else wholePart + fractionalPart
                }

            }
        }
    }

    fun parseIntToString(int: Int):CustomString{
        var result = CustomString()
        var temp = int
        do {
            result += when(temp%10){
                1, -1 -> "1"
                2, -2 -> "2"
                3, -3 -> "3"
                4, -4 -> "4"
                5, -5 -> "5"
                6, -6 -> "6"
                7, -7 -> "7"
                8, -8 -> "8"
                9, -9 -> "9"
                else -> "0"
            }
            temp /= 10
        }while (temp > 0 || (temp<0 && int < 0))

        if(int<0){
            result += "-"
        }
        return result.reverse()
    }

    private fun calculate(startTen: Float, currentIndex: Int, calculatedFloat: Float):Float {
        val currentTen =
            when (startTen) {
                0f -> 1f
                1000000000000000000000000000000F -> throw NumberFormatException()
                else -> startTen * 10f
            }

        val number = when (charSequence[currentIndex]) {
            '1' -> 1F
            '2' -> 2F
            '3' -> 3F
            '4' -> 4F
            '5' -> 5F
            '6' -> 6F
            '7' -> 7F
            '8' -> 8F
            '9' -> 9F
            '0' -> 0F
            else -> throw NumberFormatException( "wrong symbol: " + charSequence[currentIndex])
        }

        if(currentIndex == 0){
            return currentTen * number + calculatedFloat
        }
        return calculate(currentTen,
            currentIndex - 1,
             currentTen * number + calculatedFloat)
    }

    private fun checkDotsAndMinus(){
        with(trim()) {
            if (indexOf('.', indexOf('.', 0)+1) != -1) throw NumberFormatException("multiply dots")
            if (indexOf('-', 0) > 0
                || indexOf('-', indexOf('-', 0)+1) != -1
            ) throw NumberFormatException("minus problem")
        }
    }

    fun indexOf(char: Char, startIndex: Int): Int{
        if(startIndex < 0 || startIndex >= length){
            throw IndexOutOfBoundsException("wrong index")
        }
            for (i in startIndex until length){
                if(charSequence[i] == char){
                    return i
                }
            }
        return -1
    }

    fun reverse(): CustomString{
        with(CharArray(length)) {
            for (i in 0 until length) {
                this[i] = charSequence[length - 1 - i]
            }
            return CustomString(this)
        }
    }

    override fun get(index: Int): Char = charSequence[index]

    fun chars(): CharArray = charSequence

    override fun subSequence(startIndex: Int, endIndex: Int): CustomString {
        with(CharArray((endIndex) - startIndex)) {
            for (i in startIndex until endIndex) {
                this[i - startIndex] = charSequence[i]
            }
            return CustomString(this)
        }
    }

    override fun toString(): String {
        return String(charSequence)
    }

    override fun equals(other: Any?): Boolean {
        if(other !is CharSequence){
            return false
        }
        else{
            if (length != other.length) return false

            for(i in charSequence.indices){
                if (charSequence[i] != other[i]) return false
            }
        }
        return true
    }

    override fun hashCode(): Int {
        var result = length
        result = 31 * result + charSequence.contentHashCode()
        return result
    }
}