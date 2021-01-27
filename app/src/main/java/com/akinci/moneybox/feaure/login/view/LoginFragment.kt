package com.akinci.moneybox.feaure.login.view

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.transition.Fade
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionSet
import com.akinci.moneybox.R
import com.akinci.moneybox.common.helper.InformerStatus
import com.akinci.moneybox.databinding.FragmentLoginBinding
import com.akinci.moneybox.feaure.login.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    private val loginViewModel : LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.vm = loginViewModel

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
            //play own animation
            binding.animation.playAnimation()

            //call login coroutine service.
            loginViewModel.login()
        }

        loginViewModel.loginEventHandler.observe(viewLifecycleOwner, {
          when(it.status){
              InformerStatus.SUCCESS -> {
                  Timber.d("Login successful..")
                    // login attempt is successful, navigating to dashboard(products)

                  Snackbar.make(binding.root,  "login attempt is successful, navigating to dashboard(products)", Snackbar.LENGTH_LONG).show()
//                    NavHostFragment.findNavController(this).navigate(
//
//                    )
              }
              InformerStatus.ERROR -> {
                  Timber.d("Login failed.. ${it.message ?: "Empty Error"}")
                  Snackbar.make(binding.root, it.message ?: "Empty Error", Snackbar.LENGTH_LONG).show()
              }
          }
        })

        binding.lifecycleOwner = viewLifecycleOwner
        Timber.d("LoginFragment created..")
        return binding.root
    }

}