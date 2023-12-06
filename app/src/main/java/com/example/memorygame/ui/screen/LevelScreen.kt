package com.example.memorygame.ui.screen

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.memorygame.R
import com.example.memorygame.data.LeveleEnum
import com.example.memorygame.databinding.ScreenLevelBinding

class LevelScreen : Fragment(R.layout.screen_level) {


    private val binding: ScreenLevelBinding by viewBinding(ScreenLevelBinding::bind)



    private val open: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.button_animation
        )
    }
    private var clickItem: ImageView? = null
    private var clickItemtext: TextView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        clickItem = null
        clickItemtext = null

        binding.easy.setOnClickListener {
            if (clickItem == null) {
                clickItem = binding.easy
                binding.easy.animate()
                    .scaleX(0.7f)
                    .setDuration(200)
                    .scaleY(0.7f)
                    .withEndAction {
                        binding.easy.animate()
                            .setDuration(90)
                            .scaleY(1f)
                            .scaleX(1f)
                            .withEndAction {
                                openGameScreen(LeveleEnum.EASY)
                            }
                            .start()
                    }
                    .start()

            }

        }
        binding.medium.setOnClickListener {
            if (clickItem == null) {
                clickItem = binding.medium
                binding.medium.animate()
                    .scaleX(0.7f)
                    .setDuration(200)
                    .scaleY(0.7f)
                    .withEndAction {
                        binding.medium.animate()
                            .setDuration(90)
                            .scaleY(1f)
                            .scaleX(1f)
                            .withEndAction {
                                openGameScreen(LeveleEnum.MEDIUM)
                            }
                            .start()
                    }
                    .start()

            }
        }
        binding.hard.setOnClickListener {
            if (clickItem == null) {
                clickItem = binding.hard
                binding.hard.animate()
                    .scaleX(0.7f)
                    .setDuration(200)
                    .scaleY(0.7f)
                    .withEndAction {
                        binding.hard.animate()
                            .setDuration(90)
                            .scaleY(1f)
                            .scaleX(1f)
                            .withEndAction {
                                openGameScreen(LeveleEnum.HARD)
                            }
                            .start()
                    }
                    .start()
            }
        }

        val window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        binding.imageView.setOnClickListener {
            if (clickItem == null) {
                clickItem = binding.imageView
                binding.imageView.animate()
                    .scaleX(0.7f)
                    .setDuration(200)
                    .scaleY(0.7f)
                    .withEndAction {
                        binding.imageView.animate()
                            .setDuration(90)
                            .scaleY(1f)
                            .scaleX(1f)
                            .withEndAction {
                                findNavController().navigate(R.id.action_levelScreen_to_info_Screen)
                            }
                            .start()
                    }
                    .start()
            }
        }
    }


    fun openGameScreen(levele: LeveleEnum) {
        findNavController().navigate(
            R.id.action_levelScreen_to_gameScreen,
            bundleOf("data" to levele)
        )
    }
}