package com.nursultan.composition.domain.entity

data class GameSettings (
    val maxSumValue: Int,
    val minCountRightAnswers: Int,
    val minPercentOfRightAnswers: Int,
    val gameRimeInSeconds: Int
)