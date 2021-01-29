package com.akinci.moneybox.feaure.splash

import android.animation.Animator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavOptions
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment
import androidx.transition.Fade
import com.airbnb.lottie.LottieAnimationView
import com.akinci.moneybox.R
import com.akinci.moneybox.databinding.FragmentSplashBinding
import timber.log.Timber

class SplashFragment : Fragment() {

    lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.animation.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) { navigateToLogin() }
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
        })

        Timber.d("SplashFragment created..")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //hide appbar on splash screen
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onStart() {
        super.onStart()
        binding.animation.playAnimation()
    }

    private fun navigateToLogin(){
        Handler(Looper.getMainLooper()).postDelayed({

            val imageTransition = resources.getString(R.string.image_transition)
            val logoTransition = resources.getString(R.string.logo_transition)

            val extras = FragmentNavigatorExtras(
                binding.animation to imageTransition,
                binding.imgLogo to logoTransition
            )

            NavHostFragment.findNavController(this).navigate(
                R.id.action_splashFragment_to_loginFragment,
                null,
                NavOptions.Builder().setPopUpTo(R.id.splashFragment, true).build(),
                extras
            )
        }, 1000)
    }
}