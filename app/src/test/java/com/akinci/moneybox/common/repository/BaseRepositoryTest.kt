package com.akinci.moneybox.common.repository

import com.akinci.moneybox.common.helper.Resource
import com.akinci.moneybox.common.network.NetworkChecker
import com.akinci.moneybox.common.network.errorhandler.ApiErrorResponse
import com.akinci.moneybox.common.network.errorhandler.ErrorHandler
import com.akinci.moneybox.jsonresponses.GetApiErrorResponse
import com.google.common.truth.Truth.assertThat
import com.squareup.moshi.Moshi
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class BaseRepositoryTest {

    @MockK
    lateinit var networkChecker: NetworkChecker

    @MockK
    lateinit var errorHandler: ErrorHandler

    @MockK
    lateinit var callBackObj : SuspendingCallBack

    private lateinit var repository : BaseRepositoryImpl
    private val moshi = Moshi.Builder().build()

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        repository = BaseRepositoryImpl(networkChecker, errorHandler)
    }

    @AfterEach
    fun tearDown() { unmockkAll() }

    class SuspendingCallBack {
        fun serviceActionCallBack() : Response<String> { return Response.success("Rest Call Succeeded") }
    }

    /**
     *  BaseRepositoryImpl class holds base logic of repository layer.
     *
     *  callService with only retrofitServiceAction directly sends retrofit service response object
     *
     *  Contents of callService functions is the same apart from success case.
     * **/

    @Test
    fun `Network is ok, callService function is called, returns Resource-Success for success`() = runBlockingTest {
        every { networkChecker.isNetworkConnected() } returns true
        coEvery { callBackObj.serviceActionCallBack() } returns Response.success("Rest Call Succeeded")

        val callServiceResponseObjStr = repository.callService { callBackObj.serviceActionCallBack() }

        /** repository function response type should be Resource.Success **/
        assertThat(callServiceResponseObjStr).isInstanceOf(Resource.Success::class.java)
        /** returned error should be network error message **/
        assertThat((callServiceResponseObjStr as Resource.Success).data).isEqualTo("Rest Call Succeeded")

        /** call back should be fired. **/
        coVerify (exactly = 1) { callBackObj.serviceActionCallBack() }
        confirmVerified(callBackObj)
    }

    @Test
    fun `Network is ok, callService function is called, returns Resource-Error on null response`() = runBlockingTest {
        every { networkChecker.isNetworkConnected() } returns true
        coEvery { callBackObj.serviceActionCallBack() } returns Response.success(null)

        val callServiceResponseObj = repository.callService { callBackObj.serviceActionCallBack() }

        /** repository function response type should be Resource.Error **/
        assertThat(callServiceResponseObj).isInstanceOf(Resource.Error::class.java)
        /** returned error should be response body is null **/
        assertThat((callServiceResponseObj as Resource.Error).message).isEqualTo("Service response body is null")

        /** call back should be fired. **/
        coVerify (exactly = 1) { callBackObj.serviceActionCallBack() }
        confirmVerified(callBackObj)
    }

    @Test
    fun `Network is ok, callService function is called, returns Resource-Error on unsuccessful response code`() = runBlockingTest {
        val error : Response<String> = Response.error(404, "{\"error\":[\"404 Not Found\"]}".toResponseBody("application/json".toMediaTypeOrNull()))

        every { networkChecker.isNetworkConnected() } returns true
        coEvery { errorHandler.parseError(error) } returns moshi.adapter(ApiErrorResponse::class.java).fromJson(GetApiErrorResponse.apiErrorResponse)
        coEvery { callBackObj.serviceActionCallBack() } returns error

        val callServiceResponseObj = repository.callService { callBackObj.serviceActionCallBack() }

        /** repository function response type should be Resource.Error **/
        assertThat(callServiceResponseObj).isInstanceOf(Resource.Error::class.java)
        /** returned error should be contains response code of error **/
        assertThat((callServiceResponseObj as Resource.Error).message).contains(": 404")
        assertThat(callServiceResponseObj.message).contains("Parse Error")
        assertThat(callServiceResponseObj.message).contains("Dummy Name")

        /** call back should be fired. **/
        coVerify (exactly = 1) { callBackObj.serviceActionCallBack() }
        confirmVerified(callBackObj)
    }

    @Test
    fun `Network is ok, callService function is called, returns Resource-Error for network error`() = runBlockingTest {
        every { networkChecker.isNetworkConnected() } returns false
        coEvery { callBackObj.serviceActionCallBack() } returns Response.success("Rest Call Succeeded")

        val callServiceResponseObj = repository.callService { callBackObj.serviceActionCallBack() }

        /** Network is not connected. **/

        /** repository function response type should be Resource.Error **/
        assertThat(callServiceResponseObj).isInstanceOf(Resource.Error::class.java)
        /** returned error should be network error message **/
        assertThat((callServiceResponseObj as Resource.Error).message).isEqualTo("Couldn't reached to server. Please check your internet connection")

        /** call back shouldn't be fired. **/
        coVerify (exactly = 0) { callBackObj.serviceActionCallBack() }
        confirmVerified(callBackObj)
    }

    @Test
    fun `An exception is occurred during call Service`() = runBlockingTest {
        every { networkChecker.isNetworkConnected() } returns true
        coEvery { callBackObj.serviceActionCallBack() } throws Exception()

        val callServiceResponseObj = repository.callService { callBackObj.serviceActionCallBack() }

        /** repository function response type should be Resource.Error **/
        assertThat(callServiceResponseObj).isInstanceOf(Resource.Error::class.java)
        /** returned error message should be unexpected error **/
        assertThat((callServiceResponseObj as Resource.Error).message).isEqualTo("UnExpected Service Exception.")

        /** call back should be fired. **/
        coVerify (exactly = 1) { callBackObj.serviceActionCallBack() }
        confirmVerified(callBackObj)
    }
}