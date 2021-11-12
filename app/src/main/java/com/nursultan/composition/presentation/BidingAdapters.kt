package com.nursultan.composition.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.nursultan.composition.R

@BindingAdapter("requiredAnswers")
fun bindRequiredAnswers(textView: TextView, count: Int) {
    setText(textView, R.string.required_right_answers, count)
}

@BindingAdapter("countOfAnswers")
fun bindCountOfAnswers(textView: TextView, count: Int) {
    setText(textView, R.string.score_answers, count)
}

@BindingAdapter("requiredPercentage")
fun bindRequiredPercentage(textView: TextView, percent: Int) {
    setText(textView, R.string.required_right_answers_percentage, percent)
}

@BindingAdapter("percentageOfRightAnswers")
fun bindPercentageRightAnswers(textView: TextView, percent: Int) {
    setText(textView, R.string.required_right_answers_percentage, percent)
}

fun setText(textView: TextView, res: Int, value: Int) {
    textView.text = String.format(
        textView.context.getString(res),
        value
    )
}

@BindingAdapter("resultEmoji")
fun bindIsWinner(imageView: ImageView, isWinner: Boolean) {
    imageView.setImageResource(getDrawable(isWinner))
}

private fun getDrawable(isWinner: Boolean) = if (isWinner) {
    R.drawable.happiness
} else {
    R.drawable.sad
}

@BindingAdapter("setProgress")
fun bindProgressBar(progressBar: ProgressBar, value: Int)
{
   progressBar.setProgress(value, true)
}
@BindingAdapter("isRequiredCount")
fun bindIsRequiredCount(textView: TextView, isRequired: Boolean)
{
    textView.setTextColor(getIsRightColor(textView.context,isRequired))
}
@BindingAdapter("isRequiredPercentage")
fun bindIsRequiredPercentage(progressBar: ProgressBar, isRequired: Boolean)
{
    progressBar.progressTintList = ColorStateList.valueOf(getIsRightColor(progressBar.context,isRequired))
}
private fun getIsRightColor(context: Context,isRequired: Boolean): Int {
    return if (isRequired) {
        ContextCompat.getColor(context, android.R.color.holo_green_dark)
    } else {
        ContextCompat.getColor(context, android.R.color.holo_red_light)
    }
}