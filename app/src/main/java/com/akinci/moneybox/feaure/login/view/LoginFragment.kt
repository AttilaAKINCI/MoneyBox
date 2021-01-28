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
import androidx.navigation.NavOptions
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment
import androidx.transition.Fade
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionSet
import com.akinci.moneybox.R
import com.akinci.moneybox.common.extension.isValid
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
        binding.lifecycleOwner = viewLifecycleOwner
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

        val exitFade = Fade()
        exitFade.startDelay = 0
        exitFade.duration = 300
        exitTransition = exitFade
        /** **/

        binding.btnSignIn.setOnClickListener {
            loginViewModel.activateValidation()
            // validate ui components
            // isValid checks editTexts are filled and error free
            if(binding.etEmail.isValid() && binding.etPassword.isValid()){
                //components are valid...
                //play own animation
                binding.animation.playAnimation()

                //call login coroutine service.
                loginViewModel.login()
            }else{
                Snackbar.make(binding.root,  "Please fill required fields.", Snackbar.LENGTH_LONG).show()
            }
        }

        Timber.d("LoginFragment created..")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel.loginEventHandler.observe(viewLifecycleOwner, {
            when(it.status){
                InformerStatus.SUCCESS -> {
                    Timber.d("Login successful..")
                    Snackbar.make(binding.root,  "login attempt is successful, navigating to dashboard(products)", Snackbar.LENGTH_LONG).show()

                    // login attempt is successful, navigating to dashboard(products)

                    val extras = FragmentNavigatorExtras(
                        binding.animation to resources.getString(R.string.image_transition)
                    )

                    val direction =  LoginFragmentDirections.actionLoginFragmentToProductListFragment(
                        resources.getString(R.string.product_title))

                    NavHostFragment.findNavController(this).navigate(
                        direction.actionId,
                        direction.arguments,
                        NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build(),
                        extras
                    )
                }
                InformerStatus.ERROR -> {
                    Timber.d("Login failed.. ${it.message ?: "Empty Error"}")
                    Snackbar.make(binding.root, it.message ?: "Empty Error", Snackbar.LENGTH_LONG).show()
                }
            }
        })

    }

}