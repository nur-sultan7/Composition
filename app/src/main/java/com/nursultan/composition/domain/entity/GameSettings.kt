package com.nursultan.composition.domain.entity

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class GameSettings(
    val maxSumValue: Int,
    val minCountRightAnswers: Int,
    val minPercentOfRightAnswers: Int,
    val gameTimeInSeconds: Int
) : Parcelable {
    val minCountRightAnswersString: String
        get() = minCountRightAnswers.toString()
    val minPercentOfRightAnswersString: String
        get() = minPercentOfRightAnswers.toString()

}