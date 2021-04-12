package com.punkbytes.quizza

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun `example mocking class with MockK`() {
        // given
        val service = mockk<ExampleService>()
        every { service.getModel("Expected Param") } returns "Expected Output"

        // when
        val result = service.getModel("Expected Param")

        // then
        verify { service.getModel("Expected Param") }
        assertEquals("Expected Output", result)
    }
}

class ExampleService {
    fun getModel(testParameter: String): String {
        println("getModel - Not expected to print when function is mocked. $testParameter")
        return "Model Data"
    }

    fun performAction(testParameter: String):  String {
        println("doSomethingElse - Not expected to print when function is mocked. $testParameter")
        return "Action Finished"
    }
}
