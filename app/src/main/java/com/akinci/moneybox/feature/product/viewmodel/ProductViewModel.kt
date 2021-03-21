package com.akinci.moneybox.feature.product.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akinci.moneybox.common.component.FileDownloader
import com.akinci.moneybox.common.coroutine.CoroutineContextProvider
import com.akinci.moneybox.common.helper.Event
import com.akinci.moneybox.common.helper.Resource
import com.akinci.moneybox.common.storage.LocalPreferenceConfig
import com.akinci.moneybox.common.storage.Preferences
import com.akinci.moneybox.feature.product.detail.data.input.PaymentServiceRequest
import com.akinci.moneybox.feature.product.detail.repository.PaymentRepository
import com.akinci.moneybox.feature.product.list.data.output.ProductListServiceResponse
import com.akinci.moneybox.feature.product.list.data.output.ProductResponse
import com.akinci.moneybox.feature.product.list.repository.ProductListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val coroutineContext : CoroutineContextProvider,
    private val productListRepository: ProductListRepository,
    private val paymentRepository: PaymentRepository,
    sharedPreferences: Preferences,
    private val fileDownloader: FileDownloader
) : ViewModel() {

    // product list service response
    private var _productServiceResponse = MutableLiveData<ProductListServiceResponse>()
    val productServiceResponse : LiveData<ProductListServiceResponse> = _productServiceResponse

    // product list data is observed in product list fragment in order to feed list
    private var _productList = MutableLiveData<Resource<List<ProductResponse>>>()
    val productList : LiveData<Resource<List<ProductResponse>>> = _productList

    // Username information is bind to product list fragment xml, data is provided optionally from login page
    private var _userName = MutableLiveData<String>()
    val userName : LiveData<String> = _userName

    /** Payment related
     *  Start
     * **/
    // paymentEventHandler sends event feedback to UI layer(Fragment)
    private val _paymentEventHandler = MutableLiveData<Event<Resource<Int>>>()
    val paymentEventHandler : LiveData<Event<Resource<Int>>> = _paymentEventHandler

    // selectedProduct is bind to product detail fragment xml
    private var _selectedProduct = MutableLiveData<ProductResponse>()
    val selectedProduct : LiveData<ProductResponse> = _selectedProduct
    /** Payment related
     * End
     * **/

    init {
        Timber.d("ProductViewModel created..")

        // fetch userName from shared preferences.
        _userName.value = sharedPreferences.getStoredTag(LocalPreferenceConfig.USERNAME)
    }

    fun clearSelectedProduct(){ _selectedProduct.value = null }
    fun selectProduct(selectedProductId : Int){
        _selectedProduct.value = _productServiceResponse.value?.ProductResponses?.find { it.Id == selectedProductId }
    }

    fun fetchProductList() = viewModelScope.launch(coroutineContext.IO) {
        /** if I fetch service response before, don't call again **/
        if(_productServiceResponse.value == null){

            // send loading action for initial load
            _productList.postValue(Resource.Loading())

            delay(1000)

            when(val response = productListRepository.getProductList()) {
                is Resource.Success -> {
                    response.data?.let {
                        if(it.ProductResponses.isEmpty()){
                            _productList.postValue(Resource.NoData()) // nothing to show
                        }else{
                            _productServiceResponse.postValue(it)       // save network response
                            _productList.postValue(Resource.Success(it.ProductResponses)) // post list array
                        }
                    }
                }
                is Resource.Error -> {
                    // pass error message to fragment
                    _productList.postValue(Resource.Error(response.message))
                }
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

            viewModelScope.launch(coroutineContext.IO){
                when(val response = paymentRepository.makePayment(paymentServiceRequest)) {
                    is Resource.Success -> {
                        response.data?.Moneybox?.let { newMoneyBoxValue ->
                            it.Moneybox = newMoneyBoxValue
                            _selectedProduct.postValue(it)
                            _paymentEventHandler.postValue(Event(Resource.Info(
                                "Requested amount(£$quickAddDepositValue) has been added to your MoneyBox!!"
                            )))
                        }
                    }
                    is Resource.Error -> {
                        _paymentEventHandler.postValue(Event(Resource.Error(response.message)))
                    }
                }
            }
        }
    }

    fun downloadDocument(){
            _selectedProduct.value?.Product?.Documents?.KeyFeaturesUrl?.let { documentDownloadStringUrl ->
            // start download processes
            viewModelScope.launch(Dispatchers.IO) {
                fileDownloader.downloadDocument(
                    documentDownloadStringUrl,
                    onComplete = { _paymentEventHandler.postValue(Event(Resource.Info("Document is enqueued..."))) },
                    onError = { err ->
                        _paymentEventHandler.postValue(Event(Resource.Error("Error occurred during Document download : $err")))
                    }
                )
            }
        }

    }

}