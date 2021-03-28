import org.junit.jupiter.api.Assertions.*

internal class CustomStringTest {

    private val string = "  qwertyu12345 "
    private val customString = CustomString(string)

    @org.junit.jupiter.api.Test
    fun getLength() {
        val anotherString = "qwerty"
        assertEquals(string.length, customString.length)
        assertEquals((string + anotherString).length, customString.concat(anotherString).length)
    }

    @org.junit.jupiter.api.Test
    fun concat() {
        val anotherString = "qwerty"
        assertEquals( customString + anotherString, string + anotherString)
    }

    @org.junit.jupiter.api.Test
    fun find() {
        assertFalse(customString.find("a"))
        assertTrue(customString.find(customString.subSequence(0,5)))
    }

    @org.junit.jupiter.api.Test
    fun trim() {
        assertEquals(string.trim(), customString.trim().toString())
    }

    @org.junit.jupiter.api.Test
    fun get() {
        for (i in string.indices){
            assertEquals(string.get(i), customString.get(i))
        }
    }

    @org.junit.jupiter.api.Test
    fun chars() {
        val charsOriginalString = string.toCharArray()
        val charsCustomString = customString.chars()

        for(i in charsOriginalString.indices){
            assertEquals(charsOriginalString[i], charsCustomString[i])
        }
    }

    @org.junit.jupiter.api.Test
    fun subSequence() {
        for (i in string.indices){
            assertEquals(string.subSequence(0, i), customString.subSequence(0,i).toString())
        }
    }

    @org.junit.jupiter.api.Test
    fun testToString() {
        assertEquals(string, customString.toString())
        assertEquals(String(), CustomString().toString())
    }

    @org.junit.jupiter.api.Test
    fun toFloat(){
        val number = "100500.100500"
        val zero = "0.000"
        assertEquals(number.toFloat(), CustomString(number).toFloat())
        assertEquals(zero.toFloat(), CustomString(zero).toFloat())
        assertEquals("-$number".toFloat(), ("-" + CustomString(number)).toFloat())
    }

    @org.junit.jupiter.api.Test
    fun indexOf(){
        assertEquals(string.indexOf('5'), customString.indexOf('5', 0))
        assertEquals(string.indexOf('z'), customString.indexOf('z', 0))
    }

    @org.junit.jupiter.api.Test
    fun equals(){
        assertTrue(CustomString().equals(String()))
        assertTrue(customString.equals((string)))
    }

    @org.junit.jupiter.api.Test
    fun isEmpty(){
        assertEquals(string.isEmpty(), customString.isEmpty())
        assertFalse(String().isEmpty() == customString.isEmpty())
        assertTrue(String().isEmpty() == CustomString().isEmpty())
    }

    @org.junit.jupiter.api.Test
    fun reverse(){
        assertEquals(string.reversed(), customString.reverse().toString())
        assertTrue(customString.reverse().equals(string.reversed()))

        println("123".hashCode())
        print(CustomString("123").hashCode())

    }

    @org.junit.jupiter.api.Test
    fun parseIntToString(){
        assertEquals("123", customString.parseIntToString(123).toString())
        assertEquals("0", customString.parseIntToString(0).toString())
        assertEquals("-1", customString.parseIntToString(-1).toString())
    }
}