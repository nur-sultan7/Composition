package com.nursultan.composition.domain.usecases

import com.nursultan.composition.domain.entity.Question
import com.nursultan.composition.domain.repository.GameRepository

class GenerateQuestionUseCase(private val repository: GameRepository) {
    operator fun invoke(maxSumValue: Int): Question {
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

    companion object {
        private const val COUNT_OF_OPTIONS = 6
    }
}