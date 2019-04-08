package cossul.anderson.marvelcharacters.utils

import org.junit.Test

import org.junit.Assert.*

class EncryptionTest {

    @Test
    fun md5() {
        val input = "andersoncossul"
        val output = Encryption.md5(input)
        val expected = "715ea73fe884a0c0d14944d6d6c446d1"
        assertEquals(output, expected)
    }
}