import java.lang.IndexOutOfBoundsException

/**
 * Class representing String class
 */
class CustomString : CharSequence {

    companion object{
        const val MAX_NUMB_OF_TENS_IN_FLOAT = 1000000000000000000000000000000F
        const val INITIAL_SIZE = 0
    }

    override val length: Int

    private var charSequence: CharArray

    constructor() {
        length = INITIAL_SIZE
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

    /**
     * Adds another sequence to the internal sequence of characters at the end.
     * @return a new CustomString consisting of these lines.
     */
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

    /**
     * Searches for a given sequence of characters.
     * @return true - if found, false - no
     */
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

    /**
     * Return new CustomString without spaces at the ends if they were there.
     */
    fun trim(): CustomString {
        var start = 0
        var end = length - 1
        while (charSequence[start] == ' ')
            start++
        while (charSequence[end] == ' ')
            end--
        return subSequence(start, ++end)
    }

    /**
     * Converts a CustomString to a Float number
     * @return float number
     */
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

    /**
     * Converts a number of type Int to new CustomString
     * @return CustomString representing this number
     */
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
        return result.reversed()
    }

    /**
     * Calculates a Float number from an internal sequence of characters
     * @return float number
     */
    private fun calculate(startTen: Float, currentIndex: Int, calculatedFloat: Float):Float {
        val currentNumberOfTens =
            when (startTen) {
                0f -> 1f
                MAX_NUMB_OF_TENS_IN_FLOAT -> throw NumberFormatException()
                else -> startTen * 10f
            }

        val digit = when (charSequence[currentIndex]) {
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
            return currentNumberOfTens * digit + calculatedFloat
        }
        return calculate(currentNumberOfTens,
            currentIndex - 1,
             currentNumberOfTens * digit + calculatedFloat)
    }

    /**
     * Checks the rule: there should be one minus at the beginning and  only one dot.
     */
    private fun checkDotsAndMinus(){
        with(trim()) {
            if (indexOf('.', indexOf('.', 0)+1) != -1) throw NumberFormatException("multiply dots")
            if (indexOf('-', 0) > 0
                || indexOf('-', indexOf('-', 0)+1) != -1
            ) throw NumberFormatException("problem with minus")
        }
    }


    /**
     * Searches for the first occurrence of the desired character
     * @return index of the searched char if it is found, or -1 - if not
     */
    fun indexOf(char: Char, startIndex: Int): Int{
        if(startIndex < 0 || startIndex >= length){
            throw IndexOutOfBoundsException("wrong index($startIndex) in indexOf()")
        }
            for (i in startIndex until length){
                if(charSequence[i] == char){
                    return i
                }
            }
        return -1
    }

    /**
     * Returns a new CustomString whose internal character sequence will be mirrored.
     */
    fun reversed(): CustomString{
        with(CharArray(length)) {
            for (i in 0 until length) {
                this[i] = charSequence[length - 1 - i]
            }
            return CustomString(this)
        }
    }

    /**
     * Returns the character at the specified index
     */
    override fun get(index: Int): Char{
        return if(index < 0 || index >= length)
            throw IndexOutOfBoundsException("wrong index($index) in get()")
            else charSequence[index]
    }

    /**
     * Returns the internal sequence of chars
     */
    fun chars(): CharArray = charSequence

    /**
     * Creates a substring from the start index to the end one (not including it)
     */
    override fun subSequence(startIndex: Int, endIndex: Int): CustomString {
        if(startIndex > endIndex){
            throw IndexOutOfBoundsException("start index($startIndex) " +
                    "is greater than end index($endIndex) in subSequence()")
        }else if(startIndex < 0 || startIndex > length){
            throw IndexOutOfBoundsException("wrong start index($startIndex) in subSequence()")
        }else if(endIndex < 0 || endIndex > length){
            throw IndexOutOfBoundsException("wrong end index($endIndex) in subSequence()")
        }

        with(CharArray((endIndex) - startIndex)) {
            for (i in startIndex until endIndex) {
                this[i - startIndex] = charSequence[i]
            }
            return CustomString(this)
        }
    }

    /**
     * Creates a String class
     */
    override fun toString(): String {
        return String(charSequence)
    }

    /**
     * Allows to compare CustomString with other classes that implement the CharSequence interface
     * @return true - if their chars match, false - if not
     */
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

    /**
     * Automatically generated hash function
     */
    override fun hashCode(): Int {
        var result = length
        result = 31 * result + charSequence.contentHashCode()
        return result
    }
}