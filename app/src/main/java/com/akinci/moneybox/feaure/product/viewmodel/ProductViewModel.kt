package com.akinci.moneybox.feaure.product.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akinci.moneybox.common.component.filedownloader.FileDownloadEventListener
import com.akinci.moneybox.common.component.filedownloader.FileDownloader
import com.akinci.moneybox.common.helper.Event
import com.akinci.moneybox.common.helper.Informer
import com.akinci.moneybox.common.helper.ResourceStatus
import com.akinci.moneybox.common.storage.PrefConfig
import com.akinci.moneybox.common.storage.Preferences
import com.akinci.moneybox.feaure.product.detail.data.input.PaymentServiceRequest
import com.akinci.moneybox.feaure.product.detail.repository.PaymentRepository
import com.akinci.moneybox.feaure.product.list.data.output.ProductListServiceResponse
import com.akinci.moneybox.feaure.product.list.data.output.ProductResponse
import com.akinci.moneybox.feaure.product.list.repository.ProductListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class ProductViewModel @ViewModelInject constructor(
        private val productListRepository: ProductListRepository,
        private val paymentRepository: PaymentRepository,
        private val sharedPreferences: Preferences,
        private val fileDownloader: FileDownloader
) : ViewModel() {

    // product list service response
    private var _productServiceResponse = MutableLiveData<ProductListServiceResponse>()
    val productServiceResponse : LiveData<ProductListServiceResponse> = _productServiceResponse

    // product list data is observed in product list fragment in order to feed list
    private var _productList = MutableLiveData<Informer<List<ProductResponse>>>()
    val productList : LiveData<Informer<List<ProductResponse>>> = _productList

    // Username information is bind to product list fragment xml, data is provided optionally from login page
    private var _userName = MutableLiveData<String>()
    val userName : LiveData<String> = _userName

    /** Payment related
     *  Start
     * **/
    // paymentEventHandler sends event feedback to UI layer(Fragment)
    private val _paymentEventHandler = MutableLiveData<Event<Informer<Int>>>()
    val paymentEventHandler : LiveData<Event<Informer<Int>>> = _paymentEventHandler

    private val _documentDownloadEventHandler = MutableLiveData<Event<Informer<Any>>>()
    val documentDownloadEventHandler : LiveData<Event<Informer<Any>>> = _documentDownloadEventHandler

    // selectedProduct is bind to product detail fragment xml
    private var _selectedProduct = MutableLiveData<ProductResponse>()
    val selectedProduct : LiveData<ProductResponse> = _selectedProduct
    /** Payment related
     * End
     * **/

    init {
        Timber.d("ProductViewModel created..")

        // fetch userName from shared preferences.
        _userName.value = sharedPreferences.getStoredTag(PrefConfig.USERNAME)

        // fetch products
        getProductList()
    }

    fun clearSelectedProduct(){ _selectedProduct.value = null }
    fun selectProduct(selectedProductId : Int){
        _selectedProduct.value = _productServiceResponse.value?.ProductResponses?.find { it.Id == selectedProductId }
    }

    private fun getProductList() = viewModelScope.launch {
        // send loading action for initial load
        _productList.postValue(Informer.loading())

        delay(1000) // simulate network delay.

        val response = productListRepository.getProductList()
        when(response.resourceStatus){
            ResourceStatus.SUCCESS -> {
                response.data?.let {
                    if(it.ProductResponses.isEmpty()){
                        _productList.postValue(Informer.noData()) // nothing to show
                    }else{
                        _productServiceResponse.value = it       // save network response
                        _productList.postValue(Informer.success(it.ProductResponses)) // post list array
                    }
                } ?: _productList.postValue(Informer.noData()) // response body is null
            }
            ResourceStatus.ERROR ->{
                // pass error message to fragment
                _productList.postValue(Informer.error(response.message ?: "", null))
            }
        }
    }

    fun makePayment() {
        _selectedProduct.value?.let {
            // selected product is founded

            //prepare make payment service input
            val quickAddDepositValue = it.Personalisation.QuickAddDeposit.Amount.toInt()
            val paymentServiceRequest = PaymentServiceRequest(
                quickAddDepositValue,
                it.Id
            )

            viewModelScope.launch {
                val response = paymentRepository.makePayment(paymentServiceRequest)
                when(response.resourceStatus){
                    ResourceStatus.SUCCESS ->{
                        response.data?.Moneybox?.let { newMoneyBoxValue ->
                            it.Moneybox = newMoneyBoxValue
                            _selectedProduct.postValue(it)
                            _paymentEventHandler.postValue(Event(Informer.success(quickAddDepositValue)))
                        }
                    }
                    ResourceStatus.ERROR->{
                        _paymentEventHandler.postValue(Event(Informer.error(response.message ?: "", null)))
                    }
                }
            }
        }
    }

    fun downloadDocument(){
            _selectedProduct.value?.Product?.Documents?.KeyFeaturesUrl?.let {
            // start download processes
            viewModelScope.launch(Dispatchers.IO) {

                fileDownloader.download(it, object : FileDownloadEventListener {
                    override fun onEnqueued() {
                        _documentDownloadEventHandler.postValue(Event(Informer.info("Document is enqueued...")))
                    }
                })
            }
        }

    }

}