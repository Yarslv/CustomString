import java.lang.IndexOutOfBoundsException

class CustomString : CharSequence{

    override val length: Int
    private var charSequence: CharArray

    constructor(){
        length = 0
        charSequence = CharArray(length)
    }

    constructor(charSequence: CharSequence){
        length = charSequence.length
        this.charSequence = CharArray(charSequence.length)
        for(i in 0 until length){
            this.charSequence[i] = charSequence[i]
        }
    }

    constructor(initialArray: CharArray){
        length = initialArray.size
        charSequence = initialArray
    }

    operator fun plus(other: CharSequence): CustomString{
        return concat(other)
    }

    fun concat(other: CharSequence): CustomString{
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

    fun find(other: CharSequence): Boolean{
        for(i in charSequence.indices){
            if (charSequence[i] == other[0]){
                for(j in other.indices){
                    if(charSequence[i+j] == other[j]) {
                        continue
                    }else{
                        return false
                    }
                }
                return true
            }
            else{
                continue
            }
        }
        return false
    }

    fun trim(): CustomString{
        var start = 0
        var end = length - 1
        while (charSequence[start] == ' ')
            start++
        while (charSequence[end] == ' ')
            end--
        return subSequence(start, ++end)
    }

    fun toInt():Int{
        if(length == 0){
            throw java.lang.NumberFormatException("empty String")
        }
        return calculate(0, length-1, 0)
    }

    fun toFloat():Float{
        if(length == 0){
            throw java.lang.NumberFormatException("empty String")
        }
        return when(val dotIndex = indexOf('.', 0)){
            -1 -> calculate(0F, length - 1, 0F)
            else -> {
                if (indexOf('.', dotIndex + 1) != -1) throw NumberFormatException("multiply dots")
                val rigtC = CustomString(subSequence(dotIndex+1, length))
                var rigt = CustomString(subSequence(dotIndex+1, length)).toFloat()
                for(i in rigtC.indices){
                    rigt /= 10
                }
                CustomString(subSequence(0, dotIndex)).toFloat() + rigt
            }
        }
    }

    fun parseIntToString(int: Int):CustomString{
        var result = CustomString()
        var temp = int
        do {
            println(temp%10)
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
            result = result.concat("-")
        }
        return result.reverse()
    }

    @Suppress("CAST_NEVER_SUCCEEDS")
    private fun<T: Number> calculate(startTen: T, currentIndex: Int, calculatedFloat: T):T {
        val currentTen =
            when (startTen) {
                0 -> 1
                0f -> 1f
                1000000000 -> throw NumberFormatException()
                1000000000000000000000000000000F -> throw NumberFormatException()
                else -> when (startTen){ is Int -> {startTen * 10} is Float -> {startTen * 10f} else -> throw java.lang.NumberFormatException()}
            }

        val number = when (charSequence[currentIndex]) {
            '1' -> if(startTen is Int) 1 else 1F
            '2' -> if(startTen is Int) 2 else 2F
            '3' -> if(startTen is Int) 3 else 3F
            '4' -> if(startTen is Int) 4 else 4F
            '5' -> if(startTen is Int) 5 else 5F
            '6' -> if(startTen is Int) 6 else 6F
            '7' -> if(startTen is Int) 7 else 7F
            '8' -> if(startTen is Int) 8 else 8F
            '9' -> if(startTen is Int) 9 else 9F
            '0' -> if(startTen is Int) 0 else 0F
            else -> throw NumberFormatException()
        }

        if(currentIndex == 0){
            if(startTen is Int) {
                return ((currentTen as Int) * (number as Int) + calculatedFloat as Int) as T
            }
            return ((currentTen as Float) * (number.toFloat()) + calculatedFloat as Float) as T
        }

        if(startTen is Int) {
            return calculate(currentTen, currentIndex - 1, ((currentTen as Int) * number as Int) + calculatedFloat as Int) as T
        }
        return calculate(currentTen, currentIndex - 1, (currentTen as Float * number.toFloat()) + calculatedFloat as Float) as T
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

    fun isEmpty(): Boolean = length == 0

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
}