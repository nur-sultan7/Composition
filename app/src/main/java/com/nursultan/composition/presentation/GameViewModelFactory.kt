package com.nursultan.composition.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nursultan.composition.domain.entity.Level
import java.lang.RuntimeException

class GameViewModelFactory(private val application: Application, private val level: Level) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(GameViewModel::class.java))
            @Suppress("UNCHECKED_CAST")
            GameViewModel(application, level) as T
        else
            throw RuntimeException("Unknown view model class: ${modelClass.simpleName}")
    }
}