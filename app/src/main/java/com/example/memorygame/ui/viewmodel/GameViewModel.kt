package com.example.memorygame.ui.viewmodel

import androidx.lifecycle.LiveData
import com.example.memorygame.data.CardData
import com.example.memorygame.data.LeveleEnum

interface GameViewModel {

    val cardLiveData:LiveData<List<CardData>>

    fun loadCardByLevel(leveleEnum: LeveleEnum)
}