package com.punkbytes.quizza

import com.punkbytes.quizza.data.remote.ApiTriviaData

object TestData {
    val triviaDbData = ApiTriviaData(
        category = "Test Data",
        type = "General Knowledge",
        difficulty = "Easy",
        "What is your favourite colour?",
        "Green",
        listOf("Pink", "Red", "Black")
    )
}
