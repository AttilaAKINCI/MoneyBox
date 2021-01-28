package com.akinci.moneybox.feaure.product.detail.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.akinci.moneybox.R
import com.akinci.moneybox.databinding.FragmentProductDetailBinding
import com.akinci.moneybox.feaure.product.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    lateinit var binding: FragmentProductDetailBinding
    private val productViewModel : ProductViewModel by activityViewModels() // activity scoped shared viewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_detail, container, false)
        binding.vm = productViewModel

        return binding.root
    }


}