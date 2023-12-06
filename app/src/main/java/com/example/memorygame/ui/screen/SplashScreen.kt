package com.example.memorygame.ui.screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.system.Os.bind
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.memorygame.R
import com.example.memorygame.databinding.SplashlayoutBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreen : Fragment(R.layout.splashlayout){


    private val binding by viewBinding(SplashlayoutBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.splashscreen)


        lifecycleScope.launch {
            binding.placeHolder.playAnimation()
            textAnimation()
            delay(4500)
            findNavController().navigate(R.id.action_splashScreen_to_levelScreen)
        }
    }


    private fun textAnimation(){
        val a = AnimationUtils.loadAnimation(requireContext(),R.anim.text_animation)

        binding.textView6.clearAnimation()
        binding.textView6.startAnimation(a)

    }


}