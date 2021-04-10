package com.punkbytes.quizza

import com.punkbytes.quizza.data.TriviaData

object TestData {
    val triviaDbData = TriviaData(
        category = "Test Data",
        type = "General Knowledge",
        difficulty = "Easy",
        "What is your favourite colour?",
        "Green",
        listOf("Pink", "Red", "Black")
    )
}
