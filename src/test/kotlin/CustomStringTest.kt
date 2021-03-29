import org.junit.jupiter.api.Assertions.*

internal class CustomStringTest {

    private val originalString = "  qwertyu12345 "
    private val customString = CustomString(originalString)

    @org.junit.jupiter.api.Test
    fun constructors() {
        val int = 1000
        val charSequence = charArrayOf('a', 'b', 'c')
        assertTrue(CustomString(int).equals(int.toString()))
        assertTrue(CustomString(charSequence).equals(String(charSequence)))
        assertTrue(CustomString(charArrayOf()).equals(String(charArrayOf())))
    }

    @org.junit.jupiter.api.Test
    fun getLength() {
        val anotherString = "qwerty"
        assertEquals(originalString.length, customString.length)
        assertEquals((originalString + anotherString).length, customString.concat(anotherString).length)
    }

    @org.junit.jupiter.api.Test
    fun concat() {
        val anotherString = "qwerty"
        assertEquals( customString + anotherString, originalString + anotherString)
    }

    @org.junit.jupiter.api.Test
    fun find() {
        assertFalse(customString.find("a"))
        assertTrue(customString.find(customString.subSequence(0,5)))
    }

    @org.junit.jupiter.api.Test
    fun trim() {
        assertEquals(originalString.trim(), customString.trim().toString())
    }

    @org.junit.jupiter.api.Test
    fun get() {
        for (i in originalString.indices){
            assertEquals(originalString.get(i), customString.get(i))
        }
    }

    @org.junit.jupiter.api.Test
    fun chars() {
        val charsOriginalString = originalString.toCharArray()
        val charsCustomString = customString.chars()

        for(i in charsOriginalString.indices){
            assertEquals(charsOriginalString[i], charsCustomString[i])
        }
    }

    @org.junit.jupiter.api.Test
    fun subSequence() {
        for (i in originalString.indices){
            assertEquals(originalString.subSequence(0, i), customString.subSequence(0,i).toString())
        }
    }

    @org.junit.jupiter.api.Test
    fun testToString() {
        assertEquals(originalString, customString.toString())
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
        assertEquals(originalString.indexOf('5'), customString.indexOf('5', 0))
        assertEquals(originalString.indexOf('z'), customString.indexOf('z', 0))
    }

    @org.junit.jupiter.api.Test
    fun equals(){
        assertTrue(CustomString().equals(String()))
        assertTrue(customString.equals((originalString)))
    }

    @org.junit.jupiter.api.Test
    fun isEmpty(){
        assertEquals(originalString.isEmpty(), customString.isEmpty())
        assertFalse(String().isEmpty() == customString.isEmpty())
        assertTrue(String().isEmpty() == CustomString().isEmpty())
    }

    @org.junit.jupiter.api.Test
    fun reverse(){
        assertEquals(originalString.reversed(), customString.reversed().toString())
        assertTrue(customString.reversed().equals(originalString.reversed()))
    }

    @org.junit.jupiter.api.Test
    fun parseIntToString(){
        assertEquals("123", customString.parseIntToString(123).toString())
        assertEquals("0", customString.parseIntToString(0).toString())
        assertEquals("-1", customString.parseIntToString(-1).toString())
    }
}