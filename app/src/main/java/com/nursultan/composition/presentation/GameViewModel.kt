package com.nursultan.composition.presentation

import android.os.CountDownTimer
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
import java.lang.RuntimeException

class GameViewModel : ViewModel() {
    private val gameRepositoryImp = GameRepositoryImpl

    private lateinit var gameSettings: GameSettings
    private var timer: CountDownTimer? = null

    fun getMinCountRightAnswers(): Int {
        return gameSettings.minCountRightAnswers
    }

    private var _gameQuestion: MutableLiveData<Question> = MutableLiveData()
    val gameQuestion: LiveData<Question>
        get() = _gameQuestion


    private var countOfQuestion = 0

    private var countOfRightAnswers=0


    private var _percentageOfRightAnswers: MutableLiveData<Int> = MutableLiveData()
    val percentageOfRightAnswers: LiveData<Int>
        get() = _percentageOfRightAnswers


    private var _timerTime: MutableLiveData<String> = MutableLiveData()
    val timerTime: LiveData<String>
        get() = _timerTime

    private var _gameIsFinished: MutableLiveData<Boolean> = MutableLiveData()
    val gameIsFinished: LiveData<Boolean>
        get() = _gameIsFinished

    private val generateQuestionUseCase = GenerateQuestionUseCase(gameRepositoryImp)
    private val getGameSettingUseCase = GetGameSettingUseCase(gameRepositoryImp)


    fun checkIsRightAnswer(answer: Int) {
        val rightAnswer = _gameQuestion.value?.rightAnswer
        if (answer==rightAnswer)
        {
            countOfRightAnswers++
        }
            calculatePercentageOfRightAnswers()
            generateGameQuestion(gameSettings.maxSumValue)

    }


    private fun calculatePercentageOfRightAnswers() {
        _percentageOfRightAnswers.value = countOfRightAnswers*100 / countOfQuestion
    }

    fun getGameResult(): GameResult {
        return GameResult(isWinner(), countOfRightAnswersValue, countOfQuestion, gameSettings)
    }

    private fun isWinner(): Boolean {
        return ((countOfRightAnswersValue >= gameSettings.minCountRightAnswers)
                and (percentageOfRightAnswersValue >= gameSettings.minPercentOfRightAnswers))
    }

    fun generateGameQuestion(maxSum: Int) {
        _gameQuestion.value = generateQuestionUseCase(maxSum)
        countOfQuestion++
    }

    fun startGame(level: Level) {
        gameSettings = getGameSettingUseCase(level)
        _gameIsFinished.value = false
        _percentageOfRightAnswers.value = 0
    }

    fun startGame(timerTime: Long) {
        timer =object: CountDownTimer(timerTime* MILLIS_IN_SECOND, MILLIS_IN_SECOND) {
            override fun onTick(p0: Long) {
                _timerTime.value = formattedTime(p0)
            }

            override fun onFinish() {
                finishGame()
            }
        }.start()
    }
    fun formattedTime(timeInMilLis: Long):String
    {
        val seconds = timeInMilLis/ MILLIS_IN_SECOND
        val minutes = seconds/ SECONDS_IN_MINUTE
        val leftSeconds = seconds - minutes* SECONDS_IN_MINUTE
        return String.format("%02d:%02d",minutes, leftSeconds)
    }

    fun finishGame() {
        _gameIsFinished.value = true
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    companion object {
        private const val MILLIS_IN_SECOND = 1000L
        private const val SECONDS_IN_MINUTE= 60
    }
}