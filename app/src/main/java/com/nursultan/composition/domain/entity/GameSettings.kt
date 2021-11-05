package com.nursultan.composition.domain.entity

import java.io.Serializable

data class GameSettings (
    val maxSumValue: Int,
    val minCountRightAnswers: Int,
    val minPercentOfRightAnswers: Int,
    val gameTimeInSeconds: Int
):Serializable