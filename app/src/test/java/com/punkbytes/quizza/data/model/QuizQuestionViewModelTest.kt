package com.punkbytes.quizza.data.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.punkbytes.quizza.data.repository.QuizRepository
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*

@ExperimentalCoroutinesApi
class QuizQuestionViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.cleanupTestCoroutines()
    }

    @Test
    fun `test basic view model`() {
        // arrange
        val question = QuizQuestion(
            "Test Question",
            "Yes",
            listOf("No-1", "No-2", "No-3")
        )
        val repository = mockk<QuizRepository>()
        every { repository.getQuestion(any()) } returns question

        // act
        val model = QuizQuestionViewModel(0, repository)

        // assert
        verify { repository.getQuestion(0) }
        Assert.assertEquals("Test Question", model.question.value?.question!!)
    }

    @Test
    @Ignore("Disabled temporarily")
    fun `test async view model`() = runBlockingTest {
        // arrange
        val question = QuizQuestion(
            "Test Question",
            "One",
            listOf("Two", "Three", "Four")
        )
        val repository = mockk<QuizRepository>()
        coEvery { repository.getQuestionAsync(any()) } returns question

        // act
        val model = QuizQuestionViewModel(0, repository)

        // assert
        coVerify { repository.getQuestionAsync(0) }
        Assert.assertEquals("Test Question", model.question.value?.question!!)
    }
}
