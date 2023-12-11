package com.example.quizapp.feature.domain.use_case.menu

import com.example.quizapp.feature.domain.model.CreateQuizValidationData
import com.example.quizapp.feature.domain.use_case.ValidationResult
import javax.inject.Inject

class CreateQuizValidation @Inject constructor() {

    operator fun invoke(createQuizValidationData: CreateQuizValidationData): ValidationResult {

        if (isTooShort(createQuizValidationData.title, 3))
            return ValidationResult.Error(TOO_SHORT_TITLE)

        if (isTooShort(createQuizValidationData.description, 6))
            return ValidationResult.Error(TOO_SHORT_DESCRIPTION)

        return ValidationResult.Success
    }

    companion object {
        const val TOO_SHORT_TITLE = "TITLE MUST HAVE AT LEAST 3 CHARACTERS"
        const val TOO_SHORT_DESCRIPTION = "DESCRIPTION MUST HAVE AT LEAST 6 CHARACTERS"
    }
}
 fun isTooShort(text: String, minLength: Int): Boolean {
    return text.filterNot { it.isWhitespace() }.length < minLength
}