package com.maum.note.core.common.util.url

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UrlManagerTest {
    @Test
    fun `url이_안전하게_인코딩된다`() {
        val raw = "https://www.google.com/search?q=코드"
        val encoded = raw.urlEncode()
        val expected = "https%3A%2F%2Fwww.google.com%2Fsearch%3Fq%3D%EC%BD%94%EB%93%9C"

        assertEquals(expected, encoded)
    }

    @Test
    fun `url이_안전하게_디코딩된다`() {
        val original = "https://www.google.com/search?q=코드"
        val encoded = original.urlEncode()
        val decoded = encoded.urlDecode()

        assertEquals(original, decoded)
    }

    @Test
    fun `따옴표가_안전하게_인코딩된다`() {
        // Given
        val raw = """수빈이 "밥" 먹음"""

        // When
        val encoded = raw.urlEncode()
        val expected = "%EC%88%98%EB%B9%88%EC%9D%B4%20%22%EB%B0%A5%22%20%EB%A8%B9%EC%9D%8C"

        // Then
        assertEquals(expected, encoded)
    }

    @Test
    fun `문자열_디코딩_원래_문자열로_복구된다`() {
        // Given
        val original = """수빈이 "밥" 먹음"""

        // When
        val encoded = original.urlEncode()
        val decoded = encoded.urlDecode()

        // Then
        assertEquals(original, decoded)
    }
}