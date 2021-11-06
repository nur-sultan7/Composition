package com.nursultan.composition.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nursultan.composition.data.GameRepositoryImpl
import com.nursultan.composition.domain.entity.GameResult
import com.nursultan.composition.domain.entity.GameSettings
import com.nursultan.composition.domain.entity.Level
import com.nursultan.composition.domain.entity.Question
import com.nursultan.composition.domain.usecases.GenerateQuestionUseCase
import com.nursultan.composition.domain.usecases.GetGameSettingUseCase

class GameViewModel : ViewModel() {
    private val gameRepositoryImp = GameRepositoryImpl
    private var _gameSettings: MutableLiveData<GameSettings> = MutableLiveData()
    val gameSettings: LiveData<GameSettings>
        get() = _gameSettings

    private var _gameQuestion: MutableLiveData<Question> = MutableLiveData()
    val gameQuestion: LiveData<Question>
        get() = _gameQuestion

    private val generateQuestionUseCase = GenerateQuestionUseCase(gameRepositoryImp)
    private val getGameSettingUseCase = GetGameSettingUseCase(gameRepositoryImp)

    fun generateGameQuestion(maxSum:Int)
    {
        _gameQuestion.value = generateQuestionUseCase(maxSum)
    }

    fun setGameSetting(level: Level) {
        _gameSettings.value = getGameSettingUseCase(level)
    }
}