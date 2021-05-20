package com.anurag.ituneapiapp.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.anurag.ituneapiapp.util.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(private val context: Context): Interceptor {
    private val applicationContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isNetworkAvailable(context)) {
            throw NoInternetException("you are not connected to internet")

        }
        return chain.proceed(chain.request())
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        if (context == null) return false
        val connectivityManager=
            context.getSystemService(Context.CONNECTIVITY_SERVICE)as ConnectivityManager
        if (Build.VERSION.SDK_INT  >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities !=null){
                when{
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ->{
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ->{
                        return true
                    }
                }
            }
        } else{
            val activeNetworkInfo =connectivityManager.activeNetworkInfo
            if (activeNetworkInfo !=null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false

    }


}