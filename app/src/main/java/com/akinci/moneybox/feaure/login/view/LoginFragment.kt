package com.akinci.moneybox.feaure.login.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.transition.Fade
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionSet
import com.akinci.moneybox.R
import com.akinci.moneybox.databinding.FragmentLoginBinding
import timber.log.Timber


class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        /** view transition configuration **/
        val enterTransitionSet = TransitionSet()
        enterTransitionSet.addTransition(TransitionInflater.from(context).inflateTransition(android.R.transition.move))
        enterTransitionSet.duration = 1000
        sharedElementEnterTransition = enterTransitionSet

        val enterFade = Fade()
        enterFade.startDelay = 1000
        enterFade.duration = 300
        enterTransition = enterFade
        /** **/

        binding.btnSignIn.setOnClickListener {
            binding.animation.playAnimation()
        }

        binding.lifecycleOwner = viewLifecycleOwner
        Timber.d("LoginFragment created..")
        return binding.root
    }

}