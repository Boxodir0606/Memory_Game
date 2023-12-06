package com.example.memorygame.repositoriya

import com.example.memorygame.R
import com.example.memorygame.data.CardData
import com.example.memorygame.data.LeveleEnum

class AppRepositoriya private constructor(){

    companion object{
        private lateinit var instanse:AppRepositoriya
        fun getInstens():AppRepositoriya{
            if (!(::instanse.isInitialized)){
                instanse = AppRepositoriya()
            }
            return instanse
        }
    }

    private val list = ArrayList<CardData>()

    init {
        list.add(CardData(1, R.drawable.image1))
        list.add(CardData(2, R.drawable.image2))
        list.add(CardData(3, R.drawable.image3))
        list.add(CardData(4, R.drawable.image4))
        list.add(CardData(5, R.drawable.image5))
        list.add(CardData(6, R.drawable.image6))
        list.add(CardData(7, R.drawable.image7))
        list.add(CardData(8, R.drawable.image8))
        list.add(CardData(9, R.drawable.image9))
        list.add(CardData(10, R.drawable.image10))
        list.add(CardData(11, R.drawable.image11))
        list.add(CardData(12, R.drawable.image12))
        list.add(CardData(13, R.drawable.image13))
        list.add(CardData(14, R.drawable.image14))
        list.add(CardData(15, R.drawable.image15))
        list.add(CardData(16, R.drawable.image16))
        list.add(CardData(17, R.drawable.image17))
        list.add(CardData(18, R.drawable.image18))
        list.add(CardData(19, R.drawable.image19))
        list.add(CardData(20, R.drawable.image20))
        list.add(CardData(21, R.drawable.image21))
        list.add(CardData(22, R.drawable.image22))
        list.add(CardData(23, R.drawable.image23))
        list.add(CardData(24, R.drawable.image24))
        list.add(CardData(25, R.drawable.image25))
        list.add(CardData(26, R.drawable.image26))
        list.add(CardData(27, R.drawable.image27))
        list.add(CardData(28, R.drawable.image28))
        list.add(CardData(29, R.drawable.image29))
        list.add(CardData(30, R.drawable.image30))
        list.add(CardData(31, R.drawable.image31))
        list.add(CardData(32, R.drawable.image32))
        list.add(CardData(33, R.drawable.image34))
        list.add(CardData(34, R.drawable.image35))
        list.add(CardData(35, R.drawable.image36))
        list.add(CardData(36, R.drawable.image37))
        list.add(CardData(37, R.drawable.image38))
        list.add(CardData(38, R.drawable.image39))
        list.add(CardData(49, R.drawable.image40))
        list.add(CardData(40, R.drawable.image41))
        list.add(CardData(41, R.drawable.image42))
        list.add(CardData(42, R.drawable.image43))
    }
    fun getByCardLevele(leveleEnum: LeveleEnum):List<CardData>{
        val count = leveleEnum.horCount*leveleEnum.verCount
        list.shuffle()
        val resolt = ArrayList<CardData>(count)
        val ls = list.subList(0,count/2)
        resolt.addAll(ls)
        resolt.addAll(ls)
        resolt.shuffle()
        resolt.shuffle()
        return resolt
    }
}