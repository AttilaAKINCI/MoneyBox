package com.akinci.moneybox.feature.product.list.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.transition.Fade
import androidx.transition.TransitionInflater
import androidx.transition.TransitionSet
import com.akinci.moneybox.R
import com.akinci.moneybox.common.component.SnackBar
import com.akinci.moneybox.common.component.adapter.ShimmerAdapter
import com.akinci.moneybox.common.helper.Resource
import com.akinci.moneybox.databinding.FragmentProductListBinding
import com.akinci.moneybox.feature.product.list.component.adapter.ProductListAdapter
import com.akinci.moneybox.feature.product.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ProductListFragment : Fragment() {

    lateinit var binding: FragmentProductListBinding
    private val productViewModel : ProductViewModel by activityViewModels() // activity scoped shared viewModel

    private val shimmerAdapter = ShimmerAdapter()
    private lateinit var productListAdapter : ProductListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_list, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = productViewModel

        /** view transition configuration **/
        val enterTransitionSet = TransitionSet()
        enterTransitionSet.addTransition(TransitionInflater.from(context).inflateTransition(android.R.transition.move))
        enterTransitionSet.duration = 1000
        sharedElementEnterTransition = enterTransitionSet

        val enterFade = Fade()
        enterFade.startDelay = 500
        enterFade.duration = 300
        enterTransition = enterFade
        /** **/

        // product listing adapter
        productListAdapter = ProductListAdapter(clickListener = { productId ->
            // catch product row clicks and navigate to product detail fragment
            Timber.d("Navigation to product detail fragment")

            NavHostFragment.findNavController(this).navigate(
                    ProductListFragmentDirections.actionProductListFragmentToProductDetailFragment(
                        productId,
                        resources.getString(R.string.product_detail_title)
                    ),
                    null
            )
        })

        Timber.d("ProductListFragment created..")
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        // fetch products
        productViewModel.fetchProductList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // show appbar on splash screen
        (activity as AppCompatActivity).supportActionBar?.show()

        productViewModel.productList.observe(viewLifecycleOwner, {
            when(it){
                is Resource.Loading -> {
                    // setup initial shimmer loading
                    // start with initial shimmer adapter
                    binding.informationContainer.visibility = View.GONE
                    binding.recyclerProductList.visibility = View.VISIBLE

                    binding.recyclerProductList.adapter = shimmerAdapter
                }
                is Resource.Success -> {
                    // when data fetches, recycler adapter will be updated with it
                    binding.informationContainer.visibility = View.GONE
                    binding.recyclerProductList.visibility = View.VISIBLE

                    binding.recyclerProductList.adapter = productListAdapter
                    productListAdapter.submitList(it.data)
                }
                is Resource.Error -> {
                    SnackBar.makeLarge(binding.root, it.message!!, SnackBar.LENGTH_LONG).show()

                    // on errors.
                    binding.informationContainer.visibility = View.VISIBLE
                    binding.recyclerProductList.visibility = View.GONE

                    binding.informationImage.setImageDrawable(ContextCompat.getDrawable(binding.root.context, R.drawable.ic_err))
                    binding.informationText.text = resources.getString(R.string.err)
                    binding.informationImage.contentDescription = resources.getString(R.string.accessibility_information_err)
                }
                is Resource.NoData -> {
                    binding.informationContainer.visibility = View.VISIBLE
                    binding.recyclerProductList.visibility = View.GONE

                    binding.informationImage.setImageDrawable(ContextCompat.getDrawable(binding.root.context, R.drawable.ic_no_data))
                    binding.informationText.text = resources.getString(R.string.no_data)
                    binding.informationImage.contentDescription = resources.getString(R.string.accessibility_information_nodata)
                }
            }
        })
    }

}