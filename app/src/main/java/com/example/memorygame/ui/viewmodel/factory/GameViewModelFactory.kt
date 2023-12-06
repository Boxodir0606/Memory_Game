package com.example.memorygame.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.memorygame.repositoriya.AppRepositoriya
import com.example.memorygame.ui.viewmodel.imp.GameViewModelIMP

class GameViewModelFactory:ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModelIMP::class.java))
            return GameViewModelIMP(AppRepositoriya.getInstens()) as T
        throw IllegalArgumentException()
    }

}