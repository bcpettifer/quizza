package com.punkbytes.quizza.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.punkbytes.quizza.R
import com.punkbytes.quizza.data.model.QuizQuestion
import com.punkbytes.quizza.data.model.QuizQuestionViewModel
import com.punkbytes.quizza.data.repository.QuizRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class QuizQuestionFragmentTest {

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
    fun testBasicViewModel() {
        // arrange
        val question = QuizQuestion(
            "Test Question",
            "Yes",
            listOf("No-1", "No-2", "No-3")
        )
        val repository = mockk<QuizRepository>()
        coEvery { repository.getQuestionAsync(any()) } returns question

        // act
        val model = QuizQuestionViewModel(0, repository)

        // assert
        coVerify { repository.getQuestionAsync(0) }
        Assert.assertEquals("Test Question", model.question.value?.question!!)
    }

    @Test
    @Ignore("Disabled temporarily")
    fun testQuizQuestionFragmentAsync() = runBlockingTest {
        val question = QuizQuestion(
            "Test Question",
            "Yes",
            listOf("No-1", "No-2", "No-3")
        )

        val repository = mockk<QuizRepository>()
        coEvery { repository.getQuestionAsync(any()) } returns question

        val model = QuizQuestionViewModel(0, repository)

        val scenario = launchFragmentInContainer<QuizQuestionFragment>()
        scenario.onFragment { fragment ->
            fragment.bind(model)
        }
        onView(withId(R.id.button_answer_top_start)).perform(click())
        // Assert some expected behavior
//        TODO()
    }

    @Test
    fun testQuizQuestionFragment() {
        val question = QuizQuestion(
            "Test Question",
            "Yes",
            listOf("No", "Maybe", "Perhaps")
        )

        val model = QuizQuestionViewModel(0, mockk() {
            coEvery { getQuestionAsync(any()) } returns question
        })

        val scenario = launchFragmentInContainer<QuizQuestionFragment>()
        scenario.onFragment { fragment ->
            fragment.bind(model)
        }

        onView(withId(R.id.text_question)).check(matches(withText("Test Question")))
        onView(withText("Yes")).check(matches(isDisplayed()))
        onView(withText("No")).check(matches(isDisplayed()))
        onView(withText("Maybe")).check(matches(isDisplayed()))
        onView(withText("Perhaps")).check(matches(isDisplayed()))
    }

}
