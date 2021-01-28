package com.akinci.moneybox.feaure.product.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akinci.moneybox.common.helper.Informer
import com.akinci.moneybox.common.helper.ResourceStatus
import com.akinci.moneybox.common.storage.LocalPreferences
import com.akinci.moneybox.common.storage.PrefConfig
import com.akinci.moneybox.feaure.product.list.repository.ProductListRepository
import com.akinci.moneybox.feaure.product.detail.repository.PaymentRepository
import com.akinci.moneybox.feaure.product.list.data.output.ProductListServiceResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class ProductViewModel @ViewModelInject constructor(
        private val productListRepository: ProductListRepository,
        private val paymentRepository: PaymentRepository,
        private val sharedPreferences: LocalPreferences
) : ViewModel() {

    private var _productList = MutableLiveData<Informer<ProductListServiceResponse>>()
    val productList : LiveData<Informer<ProductListServiceResponse>> = _productList

    private var _userName = MutableLiveData<String>()
    val userName : LiveData<String> = _userName

    init {
        Timber.d("ProductViewModel created..")

        // fetch userName from shared preferences.
        _userName.value = sharedPreferences.getStoredTag(PrefConfig.USERNAME)

        // fetch products
        getProductList()
    }

    private fun getProductList() = viewModelScope.launch {
        // send loading action for initial load
        _productList.postValue(Informer.loading())

        val response = productListRepository.getProductList()
        when(response.resourceStatus){
            ResourceStatus.SUCCESS -> {
                response.data?.let {



                } ?: _productList.postValue(Informer.noData()) // response body is null
            }
            ResourceStatus.ERROR ->{
                // pass error message to fragment
                _productList.postValue(Informer.error(response.message ?: "", null))
            }
        }
    }

}