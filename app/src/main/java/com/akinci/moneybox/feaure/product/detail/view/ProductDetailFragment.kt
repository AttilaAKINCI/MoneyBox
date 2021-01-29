package com.akinci.moneybox.feaure.product.detail.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.akinci.moneybox.R
import com.akinci.moneybox.common.component.GlideApp
import com.akinci.moneybox.common.component.SnackBar
import com.akinci.moneybox.common.helper.InformerStatus
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
        binding.lifecycleOwner = viewLifecycleOwner

        // fetch selected product id
        val productDetailArgs = ProductDetailFragmentArgs.fromBundle(requireArguments())
        productViewModel.selectProduct(productDetailArgs.productId)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productViewModel.paymentEventHandler.observe(viewLifecycleOwner, { event ->
            // only one time consume this event
            val content = event.getContentIfNotHandled()
            content?.let {
                when(it.status){
                    InformerStatus.SUCCESS -> {
                        // show error message on snackBar
                        SnackBar.make(
                                binding.root,
                                resources.getString(R.string.add_money_info, String.format("%d", it.data)),
                                SnackBar.LENGTH_LONG
                        ).show()
                    }

                    InformerStatus.ERROR -> {
                        // show error message on snackBar
                        SnackBar.makeLarge(binding.root, it.message!!, SnackBar.LENGTH_LONG).show()
                    }
                    else -> { /** NO ACTION **/ }
                }
            }
        })

        // load image for detail fragment
        productViewModel.selectedProduct.observe(viewLifecycleOwner, {
            it.Product.LogoUrl?.let { imageUrl ->
                binding.productImage.visibility = View.VISIBLE
                GlideApp.with(binding.productImage.context)
                        .load(imageUrl)
                        .fitCenter()
                        .into(binding.productImage)
            }
        })
    }

    override fun onDetach() {
        super.onDetach()
        productViewModel.clearSelectedProduct()
    }

}