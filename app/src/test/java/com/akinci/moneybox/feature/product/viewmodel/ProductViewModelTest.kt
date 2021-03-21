package com.akinci.moneybox.feature.product.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.akinci.moneybox.common.storage.LocalPreferenceConfig
import com.akinci.moneybox.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProductViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var productViewModel : ProductViewModel
    private lateinit var fakeLocalPreferences: FakeLocalPreferences
    private lateinit var fakeProductListRepository: FakeProductListRepository
    private lateinit var fakePaymentRepository: FakePaymentRepository
    private lateinit var fakeFileDownloader: FakeFileDownloader

    @Before
    fun setUp() {
        fakeLocalPreferences =  FakeLocalPreferences()
        fakeProductListRepository = FakeProductListRepository()
        fakePaymentRepository = FakePaymentRepository()
        fakeFileDownloader = FakeFileDownloader()

        productViewModel = ProductViewModel(
            fakeProductListRepository,
            fakePaymentRepository,
            fakeLocalPreferences,
            fakeFileDownloader
        )
    }

    @Test
    fun `test fetch username from local preferences return error if provided`(){
        val userName = "Jaeren"

        fakeLocalPreferences.setStoredTag(LocalPreferenceConfig.USERNAME, userName)

        // userName is fetched at the initialization of product view model
        // so I need to recreate product view model here
        val newProductViewModel = ProductViewModel(
            fakeProductListRepository,
            fakePaymentRepository,
            fakeLocalPreferences,
            fakeFileDownloader
        )

        val userNameResp = newProductViewModel.userName.getOrAwaitValueTest()

        assertThat(userNameResp).isEqualTo(userName)
    }

    @After
    fun tearDown() { /** NO NEED **/ }
}