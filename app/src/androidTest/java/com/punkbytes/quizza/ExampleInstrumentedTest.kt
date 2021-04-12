package com.punkbytes.quizza

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.punkbytes.quizza", appContext.packageName)
    }

    @Test
    fun exampleMockingRepository() {
        // given
        val service = mockk<ExampleRepository>(relaxed = true)
        every { service.getData("Expected Param") } returns "Expected Output"

        // when
        val result = service.getData("Expected Param")
        val result2 = service.doSomethingElse("Unexpected Param")

        // then
        verify { service.getData("Expected Param") }
        assertEquals("Expected Output", result)
        assertEquals("", result2)
    }

    @Test
    fun exampleSpyingRepository() {
        // given
        val service = spyk<ExampleRepository>()
        every { service.getData("Expected Param") } returns "Expected Output"

        // when
        val result = service.getData("Expected Param")
        val result2 = service.doSomethingElse("Unexpected Param")

        // then
        verify { service.getData("Expected Param") }
        assertEquals("Expected Output", result)
        assertEquals("Something Else", result2)
    }
}

// class needs to be open for MockK to be able to use inline mocks from instrumentation tests < Android P
// : https://github.com/mockk/mockk/issues/182
open class ExampleRepository {
    open fun getData(testParameter: String): String {
        return "Data"
    }

    open fun doSomethingElse(testParameter: String): String {
        return "Something Else"
    }
}
