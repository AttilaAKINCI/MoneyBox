package com.akinci.moneybox.feature.product.viewmodel

import com.akinci.moneybox.ahelpers.InstantExecutorExtension
import com.akinci.moneybox.ahelpers.TestContextProvider
import com.akinci.moneybox.common.component.FileDownloader
import com.akinci.moneybox.common.helper.Resource
import com.akinci.moneybox.common.storage.Preferences
import com.akinci.moneybox.feature.product.detail.data.output.PaymentServiceResponse
import com.akinci.moneybox.feature.product.detail.repository.PaymentRepository
import com.akinci.moneybox.feature.product.list.data.output.ProductListServiceResponse
import com.akinci.moneybox.feature.product.list.repository.ProductListRepository
import com.akinci.moneybox.jsonresponses.AddMoneyResponse
import com.akinci.moneybox.jsonresponses.GetEmptyProductsResponse
import com.akinci.moneybox.jsonresponses.GetProductsResponse
import com.google.common.truth.Truth.assertThat
import com.squareup.moshi.Moshi
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class ProductViewModelTest {

    @MockK
    private lateinit var productListRepository: ProductListRepository

    @MockK
    private lateinit var paymentRepository: PaymentRepository

    @MockK
    private lateinit var sharedPreferences: Preferences

    @MockK
    private lateinit var fileDownloader: FileDownloader

    private lateinit var productViewModel: ProductViewModel

    private val coroutineContext = TestContextProvider()
    private val moshi = Moshi.Builder().build()

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        every { sharedPreferences.getStoredTag(any()) } returns "Dummy"
        productViewModel = ProductViewModel(coroutineContext, productListRepository, paymentRepository, sharedPreferences, fileDownloader)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `fetch product list with products successfully`() {
        coEvery { productListRepository.getProductList() } returns Resource.Success(
            moshi.adapter(ProductListServiceResponse::class.java).fromJson(GetProductsResponse.productsResponse)
        )

        productViewModel.productList.observeForever{
            when(it){
                is Resource.Loading -> { assertThat(true) }
                is Resource.Success -> {
                    assertThat(it.data?.get(0)?.Id).isEqualTo(6137)
                    assertThat(it.data?.get(0)?.CollectionDayMessage).isEqualTo("3 days until collection")
                }
            }
        }

        productViewModel.fetchProductList()

        coroutineContext.testCoroutineDispatcher.advanceUntilIdle()
    }

    @Test
    fun `fetch product list with empty product list returns nodata`() {
        coEvery { productListRepository.getProductList() } returns Resource.Success(
            moshi.adapter(ProductListServiceResponse::class.java).fromJson(GetEmptyProductsResponse.productsResponse)
        )

        productViewModel.productList.observeForever{
            when(it){
                is Resource.Loading -> { assertThat(true).isTrue() }
                is Resource.NoData -> { assertThat(true).isTrue() }
            }
        }

        productViewModel.fetchProductList()

        coroutineContext.testCoroutineDispatcher.advanceUntilIdle()
    }

    @Test
    fun `fetch product list encounter an error`() {
        coEvery { productListRepository.getProductList() } returns Resource.Error("fetch product list encounter an error")

        productViewModel.productList.observeForever{
            when(it){
                is Resource.Loading -> { assertThat(true).isTrue() }
                is Resource.Error -> {
                    assertThat(it.message).isEqualTo("fetch product list encounter an error")
                }
            }
        }

        productViewModel.fetchProductList()

        coroutineContext.testCoroutineDispatcher.advanceUntilIdle()
    }


    @Test
    fun `make payment successfully`() {
        coEvery { productListRepository.getProductList() } returns Resource.Success(
                moshi.adapter(ProductListServiceResponse::class.java).fromJson(GetProductsResponse.productsResponse)
        )
        coEvery { paymentRepository.makePayment(any()) } returns Resource.Success(
            moshi.adapter(PaymentServiceResponse::class.java).fromJson(AddMoneyResponse.addMoneyResponse)
        )

        productViewModel.productServiceResponse.observeForever{
            productViewModel.selectProduct(6137)

            assertThat(productViewModel.selectedProduct.value).isNotNull()
            assertThat(productViewModel.selectedProduct.value?.Personalisation?.QuickAddDeposit?.Amount).isEqualTo(10.00)

            productViewModel.paymentEventHandler.observeForever{
                assertThat(it).isNotNull()
                when(val value = it.getContentIfNotHandled()){
                    is Resource.Info -> {
                        assertThat(value.message).contains("has been added to your MoneyBox!!")
                    }
                }
            }

            productViewModel.makePayment()
        }

        productViewModel.fetchProductList()

    }

    @Test
    fun `make payment encounter an error`() {
        coEvery { productListRepository.getProductList() } returns Resource.Success(
                moshi.adapter(ProductListServiceResponse::class.java).fromJson(GetProductsResponse.productsResponse)
        )
        coEvery { paymentRepository.makePayment(any()) } returns Resource.Error("Make Payment service encountered an error")

        productViewModel.productServiceResponse.observeForever{
            productViewModel.selectProduct(6137)

            assertThat(productViewModel.selectedProduct.value).isNotNull()
            assertThat(productViewModel.selectedProduct.value?.Personalisation?.QuickAddDeposit?.Amount).isEqualTo(10.00)

            productViewModel.paymentEventHandler.observeForever{
                assertThat(it).isNotNull()
                when(val value = it.getContentIfNotHandled()){
                    is Resource.Error -> {
                        assertThat(value.message).isEqualTo("Make Payment service encountered an error")
                    }
                }
            }

            productViewModel.makePayment()

        }

        productViewModel.fetchProductList()

    }

    @Test
    fun `download document end successfully`(){
        coEvery { productListRepository.getProductList() } returns Resource.Success(
                moshi.adapter(ProductListServiceResponse::class.java).fromJson(GetProductsResponse.productsResponse)
        )

        productViewModel.productServiceResponse.observeForever {
            productViewModel.selectProduct(6137)

            assertThat(productViewModel.selectedProduct.value).isNotNull()

            productViewModel.paymentEventHandler.observeForever{
                assertThat(it).isNotNull()
                when(val value = it.getContentIfNotHandled()){
                    is Resource.Info -> {
                        assertThat(value.message).isEqualTo("Document is enqueued...")
                    }
                }
            }

            productViewModel.downloadDocument()
        }

        productViewModel.fetchProductList()
    }

}