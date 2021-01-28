package com.akinci.moneybox.feaure.product.list.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.transition.Fade
import androidx.transition.TransitionInflater
import androidx.transition.TransitionSet
import com.akinci.moneybox.R
import com.akinci.moneybox.databinding.FragmentLoginBinding
import com.akinci.moneybox.databinding.FragmentProductListBinding
import com.akinci.moneybox.feaure.login.viewmodel.LoginViewModel
import com.akinci.moneybox.feaure.product.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : Fragment() {

    lateinit var binding: FragmentProductListBinding
    private val productViewModel : ProductViewModel by activityViewModels() // activity scoped shared viewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // show appbar on splash screen
        (activity as AppCompatActivity).supportActionBar?.show()

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_list, container, false)
        binding.vm = productViewModel

        /** view transition configuration **/
//        val enterTransitionSet = TransitionSet()
//        enterTransitionSet.addTransition(TransitionInflater.from(context).inflateTransition(android.R.transition.move))
//        enterTransitionSet.duration = 1000
//        sharedElementEnterTransition = enterTransitionSet
//
//        val enterFade = Fade()
//        enterFade.startDelay = 1000
//        enterFade.duration = 300
//        enterTransition = enterFade
        /** **/


        // Inflate the layout for this fragment
        return binding.root
    }

}