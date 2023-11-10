package com.example.quizapp.feature.domain.use_case.menu

import com.example.quizapp.feature.domain.use_case.ValidationResult
import com.example.quizapp.feature.presentation.menu_graph.create_question.CreateQuestionItem
import javax.inject.Inject

class AddQuestionValidation @Inject constructor() {

    operator fun invoke(createQuestionItem: CreateQuestionItem): ValidationResult {

        if (createQuestionItem.correctAnswerIndex < 0)
            return ValidationResult.Error(NOT_SELECTED_CORRECT_QUESTION)

        if (isTooShort(createQuestionItem.questionDescription, 6))
            return ValidationResult.Error(TOO_SHORT_DESCRIPTION)

        if (createQuestionItem.answers.any { it.isBlank() })
            return ValidationResult.Error(BLANK_ANSWER)

        return ValidationResult.Success

    }

    companion object {
        const val NOT_SELECTED_CORRECT_QUESTION = "You have to select correct question"
        const val TOO_SHORT_DESCRIPTION = "DESCRIPTION MUST HAVE AT LEAST 6 CHARACTERS"
        const val BLANK_ANSWER = "Answer is empty"
    }
}

