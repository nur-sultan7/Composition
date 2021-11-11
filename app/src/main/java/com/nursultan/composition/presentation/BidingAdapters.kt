package com.nursultan.composition.presentation

import android.widget.ImageView
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