package com.akinci.moneybox.feature.login.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment
import androidx.transition.Fade
import androidx.transition.TransitionInflater
import androidx.transition.TransitionSet
import com.akinci.moneybox.AppRootActivity
import com.akinci.moneybox.R
import com.akinci.moneybox.common.component.SnackBar
import com.akinci.moneybox.common.extension.validateNotEmpty
import com.akinci.moneybox.common.helper.Resource
import com.akinci.moneybox.databinding.FragmentLoginBinding
import com.akinci.moneybox.feature.login.viewmodel.LoginViewModel
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
            // validate ui components
            // isValid checks editTexts are filled and error free
            if(binding.etEmail.validateNotEmpty() && binding.etPassword.validateNotEmpty()){
                //components are valid...
                //play own animation
                binding.animation.playAnimation()

                //call login coroutine service.ty
                loginViewModel.login()
            }else{
                SnackBar.make(binding.root,  "Please fill required fields.", SnackBar.LENGTH_LONG).show()
            }
        }

        Timber.d("LoginFragment created..")
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        if(activity is AppRootActivity){
            if((activity as AppRootActivity).isDirectLogin){
                SnackBar.make(binding.root, "Your login session is expired. Please login again.", SnackBar.LENGTH_LONG).show()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel.loginEventHandler.observe(viewLifecycleOwner, {
            when(it){
                is Resource.Success -> {
                    Timber.d("Login successful..")
                    SnackBar.make(binding.root,  "login attempt is successful, navigating to dashboard(products)", SnackBar.LENGTH_LONG).show()

                    // login attempt is successful, navigating to dashboard(products)
                    val extras = FragmentNavigatorExtras(
                        binding.animation to resources.getString(R.string.image_transition)
                    )

                    val direction = LoginFragmentDirections.actionLoginFragmentToProductListFragment(
                        resources.getString(R.string.product_title))

                    NavHostFragment.findNavController(this).navigate(
                        direction.actionId,
                        direction.arguments,
                        NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build(),
                        extras
                    )
                }
                is Resource.Error -> {
                    Timber.d("Login failed.. ${it.message}")
                    SnackBar.makeLarge(binding.root, it.message, SnackBar.LENGTH_LONG).show()
                }
            }
        })

    }

}