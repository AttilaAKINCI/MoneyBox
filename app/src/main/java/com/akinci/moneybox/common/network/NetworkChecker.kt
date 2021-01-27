package com.akinci.moneybox.common.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkChecker @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun isNetworkConnected(): Boolean {
        val connectivityManager = context.getSystemService(
                Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

            return when {
                activeNetwork.hasTransport(
                        NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(
                        NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(
                        NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }else{
            val networkInfo = connectivityManager.activeNetworkInfo

            return when (networkInfo?.type) {
                ConnectivityManager.TYPE_WIFI -> true
                ConnectivityManager.TYPE_MOBILE -> true
                ConnectivityManager.TYPE_ETHERNET -> true
                else -> false
            }
        }
    }
}