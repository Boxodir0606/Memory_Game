package com.example.memorygame.ui.screen

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.media.MediaPlayer
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.Chronometer
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.memorygame.R
import com.example.memorygame.data.CardData
import com.example.memorygame.data.LeveleEnum
import com.example.memorygame.databinding.ScreenGameBinding
import com.example.memorygame.ui.viewmodel.factory.GameViewModelFactory
import com.example.memorygame.ui.viewmodel.imp.GameViewModelIMP
import com.example.memorygame.utils.closeCard
import com.example.memorygame.utils.hideCard
import com.example.memorygame.utils.openCard
import kotlinx.coroutines.delay

import kotlinx.coroutines.launch


class GameScreen : Fragment(R.layout.screen_game) {

    private val binding: ScreenGameBinding by viewBinding(ScreenGameBinding::bind)

    private val viewmodel by lazy {
        ViewModelProvider(this, GameViewModelFactory())[GameViewModelIMP::class.java]
    }

    private var h = 0
    private var w = 0
    private var counter = 0
    private var step = 0
    private var level = LeveleEnum.EASY

    private val image = ArrayList<ImageView>()
    private var firstOpen = -1
    private var SecondOpen = -1

    private var media: MediaPlayer? = null
    private var truemedi: MediaPlayer? = null
    private var winsound: MediaPlayer? = null

    private var isSound = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        media()
    }

    private var clickItem: ImageView? = null
    private var clickItemRestart: AppCompatButton? = null


    @SuppressLint("ObjectAnimatorBinding")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        level = requireArguments().getSerializable("data") as LeveleEnum

        val animation = ObjectAnimator.ofInt(level, "progress", 0, 0)
        animation.duration = 2000L
        animation.interpolator = DecelerateInterpolator()
        animation.start()

        clickItem = null
        clickItemRestart = null

        val window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.gamescreen)



        binding.container.post {
            h = binding.container.height / level.horCount
            w = binding.container.width / level.verCount

            viewmodel.loadCardByLevel(level)
        }
        viewmodel.cardLiveData.observe(viewLifecycleOwner, cardsObserver)

        isSoundIcon()
        restart()
    }

    fun media() {
        media = MediaPlayer.create(context, R.raw.close)
        truemedi = MediaPlayer.create(context, R.raw.remov)
        winsound = MediaPlayer.create(context, R.raw.win)
    }

    fun winsound() {
        if (isSound) {
            winsound?.start()
        } else {
            winsound?.pause()
        }
    }

    fun stepSound() {
        if (isSound) {
            media?.start()
        } else {
            media?.pause()
        }
    }

    fun trueanswerSound() {
        if (isSound) {
            truemedi?.start()
        } else {
            truemedi?.pause()
        }
    }


    fun isSoundIcon() {
        binding.soundIcon.setOnClickListener {
            if (clickItem == null) {
                clickItem = binding.soundIcon
                binding.soundIcon.animate()
                    .scaleX(0.7f)
                    .setDuration(200)
                    .scaleY(0.7f)
                    .withEndAction {
                        binding.soundIcon.animate()
                            .setDuration(90)
                            .scaleY(1f)
                            .scaleX(1f)
                            .withEndAction {
                                if (isSound) {
                                    binding.soundIcon.setImageResource(R.drawable.nosound)
                                    isSound = false
                                    clickItem = null
                                } else {
                                    binding.soundIcon.setImageResource(R.drawable.sound)
                                    isSound = true
                                    clickItem = null
                                }
                            }
                            .start()
                    }.start()
            }
        }
    }

    private fun restart() {


        binding.reload.setOnClickListener(View.OnClickListener { view: View? ->
            if (clickItem == null) {
                clickItem = binding.reload
                binding.reload.animate()
                    .scaleX(0.7f)
                    .setDuration(200)
                    .scaleY(0.7f)
                    .withEndAction {
                        binding.reload.animate()
                            .setDuration(90)
                            .scaleY(1f)
                            .scaleX(1f)
                            .withEndAction {
                                clickItem = null
                                showRestartGameDialog()
                            }
                            .start()
                    }
                    .start()
            }

        })

        binding.menu.setOnClickListener {
            if (clickItem == null) {
                clickItem = binding.menu
                binding.menu.animate()
                    .scaleX(0.7f)
                    .setDuration(200)
                    .scaleY(0.7f)
                    .withEndAction {
                        binding.menu.animate()
                            .setDuration(90)
                            .scaleY(1f)
                            .scaleX(1f)
                            .withEndAction {
                                clickItem = null
                                findNavController().popBackStack()
                            }
                            .start()
                    }
                    .start()
            }

        }
    }

    private val cardsObserver = Observer<List<CardData>> {
        for (i in 0 until level.verCount) {
            for (j in 0 until level.horCount) {
                val imageView = ImageView(requireContext())
                binding.container.addView(imageView)
                val list = imageView.layoutParams as ConstraintLayout.LayoutParams
                list.apply {
                    width = w
                    height = h
                }

                imageView.x = i * w * 1f
                imageView.y = j * h * 1f
                imageView.layoutParams = list
                imageView.tag = it[i * level.horCount + j]
                imageView.setImageResource(R.drawable.backgroung)


                imageView.setPadding(10, 0, 10, 0)


                image.add(imageView)
                openFirst(image)
                binding.time.start()
            }
        }
        setClickImage()
    }

    private fun setClickImage() {
        image.forEachIndexed { index, view ->
            view.setOnClickListener {
                if (firstOpen == -1 && index != firstOpen) {
                    firstOpen = index
                    view.openCard()
                } else if (SecondOpen == -1 && index != SecondOpen && index != firstOpen) {
                    SecondOpen = index
                    view.openCard {
                        step++
                        check()
                        win()
                    }
                }
            }
        }
    }

    private fun check() =
        if ((image[firstOpen].tag as CardData).id == (image[SecondOpen].tag as CardData).id) {
            image[firstOpen].hideCard()
            image[SecondOpen].hideCard()
            counter += 2
            firstOpen = -1
            SecondOpen = -1
            trueanswerSound()
            binding.attemptcount.text = step.toString()
        } else {
            image[firstOpen].closeCard()
            stepSound()
            image[SecondOpen].closeCard()
            firstOpen = -1
            SecondOpen = -1
            binding.attemptcount.text = step.toString()
        }

    private fun win() {
        if (level.horCount * level.verCount == counter) {
            binding.time.stop()
            winsound()
            showEndGameDialog()
        } else {

        }
    }

    private fun showEndGameDialog() {

        val delTime: Long = binding.time.base - SystemClock.elapsedRealtime()

        val dialog = activity?.let { Dialog(it) }

        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog?.window!!.attributes)
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.MATCH_PARENT
        dialog.window!!.attributes = lp
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent);


        dialog.setCancelable(false)
        dialog.setContentView(R.layout.item_win)
        val yesBtn = dialog.findViewById(R.id.imageView4) as ImageView
        val noBtn = dialog.findViewById(R.id.imageView3) as ImageView

        val timeresolt = dialog.findViewById(R.id.textView4) as Chronometer
        val step_dialog = dialog.findViewById(R.id.textView3) as TextView

        step_dialog.text = step.toString()
        timeresolt.base = SystemClock.elapsedRealtime() + delTime

        yesBtn.setOnClickListener {

            if (clickItem == null) {
                clickItem = yesBtn
                yesBtn.animate()
                    .scaleX(0.7f)
                    .setDuration(200)
                    .scaleY(0.7f)
                    .withEndAction {
                        yesBtn.animate()
                            .setDuration(90)
                            .scaleY(1f)
                            .scaleX(1f)

                            .withEndAction {
                                image.forEach { image ->
                                    binding.container.removeView(image)
                                }

                                viewmodel.loadCardByLevel(level)
                                viewmodel.cardLiveData.observe(viewLifecycleOwner, cardsObserver)

                                binding.time.base = SystemClock.elapsedRealtime()
                                counter = 0
                                step = 0
                                binding.attemptcount.text = step.toString()
                                clickItem = null
                                dialog.dismiss()
                            }
                            .start()
                    }
                    .start()
            }

        }
        noBtn.setOnClickListener {
            if (clickItem == null) {
                clickItem = noBtn
                noBtn.animate()
                    .scaleX(0.7f)
                    .setDuration(200)
                    .scaleY(0.7f)
                    .withEndAction {
                        noBtn.animate()
                            .setDuration(90)
                            .scaleY(1f)
                            .scaleX(1f)
                            .withEndAction {
                                findNavController().popBackStack()
                                clickItem = null
                                dialog.dismiss()
                            }.start()
                    }.start()
            }
        }
        dialog.show()
    }


    private fun openFirst(images: ArrayList<ImageView>) {
        var i = 0
        images.forEach { imageView ->
            val data = imageView.tag as CardData

            imageView.animate()
                .setDuration(600)
                .rotationY(90f)
                .setInterpolator(AccelerateInterpolator())
                .withEndAction {

                    imageView.setImageResource(data.ImageRes)
                    imageView.rotationY = -90f
                    imageView.animate()
                        .setDuration(1200)
                        .rotationY(0f)
                        .setInterpolator(DecelerateInterpolator())
                        .withEndAction {
                            lifecycleScope.launch {
                                delay(i * 5L)
                                imageView.animate()
                                    .setInterpolator(AccelerateInterpolator())
                                    .setDuration(1000)
                                    .rotationY(90f)
                                    .withEndAction {

                                        imageView.setImageResource(R.drawable.backgroung)
                                        imageView.rotationY = -89.9f
                                        imageView.animate()
                                            .setInterpolator(DecelerateInterpolator())
                                            .setDuration(800)
                                            .rotationY(0f)
                                            .withEndAction {

                                            }
                                            .start()
                                    }
                                    .start()
                            }
                                .start()
                        }
                        .start()
                }
        }
    }

    private fun showRestartGameDialog(){

        val dialog = activity?.let { Dialog(it) }

        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog?.window!!.attributes)
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.MATCH_PARENT
        dialog.window!!.attributes = lp
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent);


        dialog.setCancelable(true)
        dialog.setContentView(R.layout.item_restart)

        val yesBtn = dialog.findViewById(R.id.button) as AppCompatButton
        val noBtn = dialog.findViewById(R.id.button2) as AppCompatButton


        yesBtn.setOnClickListener {

            if (clickItemRestart == null) {
                clickItemRestart = yesBtn
                yesBtn.animate()
                    .scaleX(0.7f)
                    .setDuration(200)
                    .scaleY(0.7f)
                    .withEndAction {
                        yesBtn.animate()
                            .setDuration(90)
                            .scaleY(1f)
                            .scaleX(1f)

                            .withEndAction {
                                image.forEach { image ->
                                    binding.container.removeView(image)
                                }

                                viewmodel.loadCardByLevel(level)
                                viewmodel.cardLiveData.observe(viewLifecycleOwner, cardsObserver)

                                binding.time.base = SystemClock.elapsedRealtime()
                                counter = 0
                                step = 0
                                binding.attemptcount.text = step.toString()
                                clickItemRestart = null
                                dialog.dismiss()
                            }
                            .start()
                    }
                    .start()
            }

        }
        noBtn.setOnClickListener {
            if (clickItemRestart == null) {
                clickItemRestart = noBtn
                noBtn.animate()
                    .scaleX(0.7f)
                    .setDuration(200)
                    .scaleY(0.7f)
                    .withEndAction {
                        noBtn.animate()
                            .setDuration(90)
                            .scaleY(1f)
                            .scaleX(1f)
                            .withEndAction {
                                clickItemRestart = null
                                dialog.dismiss()
                            }.start()
                    }.start()
            }
        }
        dialog.show()
    }

}