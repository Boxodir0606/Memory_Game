package com.example.memorygame.ui.viewmodel.imp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.memorygame.data.CardData
import com.example.memorygame.data.LeveleEnum
import com.example.memorygame.repositoriya.AppRepositoriya
import com.example.memorygame.ui.viewmodel.GameViewModel

class GameViewModelIMP constructor(private val app:AppRepositoriya):ViewModel(),GameViewModel{
    override val cardLiveData = MutableLiveData<List<CardData>>()


    override fun loadCardByLevel(leveleEnum: LeveleEnum) {
        cardLiveData.value = app.getByCardLevele(leveleEnum)
    }

}