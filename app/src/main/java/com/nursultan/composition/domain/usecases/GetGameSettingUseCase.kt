package com.nursultan.composition.domain.usecases

import com.nursultan.composition.domain.entity.GameSettings
import com.nursultan.composition.domain.entity.Level
import com.nursultan.composition.domain.repository.GameRepository

class GetGameSettingUseCase(private val repository: GameRepository) {
    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }
}