package com.example.memorygame.utils

import android.view.View
import android.widget.ImageView
import com.example.memorygame.R
import com.example.memorygame.data.CardData


fun ImageView.openCard(finishAnim:() ->Unit ={}){
    this.animate()
        .setDuration(200)
        .rotationY(89f)
        .withEndAction {
            this.setImageResource((tag as CardData).ImageRes)
            this.rotationY = -91f
            this.animate()
                .setDuration(200)
                .rotationY(0f)
                .withEndAction {
                    finishAnim.invoke()
                }.start()
        }
        .start()
}

fun ImageView.closeCard(finishAnim : () -> Unit = {}) {
    this.animate()
        .setDuration(200)
        .rotationY(-91f)
        .withEndAction {
            this.setImageResource(R.drawable.backgroung)
            this.rotationY = 89f
            this.animate()
                .setDuration(200)
                .rotationY(0f)
                .withEndAction {
                    finishAnim.invoke()
                }.start()
        }
        .start()
}


fun ImageView.hideCard(finishAnim : () -> Unit = {}) {
    this.animate()
        .setDuration(500)
        .scaleX(0f)
        .scaleY(0f)
        .withEndAction {
            this.visibility = View.GONE
            finishAnim.invoke()
        }
        .start()
}