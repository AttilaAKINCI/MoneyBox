package com.akinci.moneybox.feature.product.detail.view

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.akinci.moneybox.R
import com.akinci.moneybox.common.component.GlideApp
import com.akinci.moneybox.common.component.SnackBar
import com.akinci.moneybox.common.helper.Resource
import com.akinci.moneybox.databinding.FragmentProductDetailBinding
import com.akinci.moneybox.feature.product.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {
    companion object{
        const val WRITE_PERMISSION_CODE = 99
    }

    lateinit var binding: FragmentProductDetailBinding
    private val productViewModel : ProductViewModel by activityViewModels() // activity scoped shared viewModel

    private val productDetailArgs by lazy { ProductDetailFragmentArgs.fromBundle(requireArguments()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_detail, container, false)
        binding.vm = productViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // fetch selected product id
        productViewModel.selectProduct(productDetailArgs.productId)

        binding.btnDownloadDocument.setOnClickListener {
            // ask for permission
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                // only for api 29 and newer versions
                productViewModel.downloadDocument()
            }else{
                if(ActivityCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    // need permission
                    val permissions = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permissions, WRITE_PERMISSION_CODE)
                 }else{
                    productViewModel.downloadDocument()
                 }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productViewModel.paymentEventHandler.observe(viewLifecycleOwner, { event ->
            // only one time consume this event
            val content = event.getContentIfNotHandled()
            content?.let {
                when(it){
                    is Resource.Info -> {
                        SnackBar.make(binding.root, it.message, SnackBar.LENGTH_LONG).show()
                    }
                    is Resource.Error -> {
                        SnackBar.makeLarge(binding.root, it.message, SnackBar.LENGTH_LONG).show()
                    }else->{ /** NO ACTION **/ }
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == WRITE_PERMISSION_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            // permission granted so I can load document
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q) {
                productViewModel.downloadDocument()
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        productViewModel.clearSelectedProduct()
    }

}