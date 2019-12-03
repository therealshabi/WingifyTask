package technolifestyle.com.wingify.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.net.NetworkInfo
import android.os.Build
import android.provider.ContactsContract.CommonDataKinds.Email.TYPE_MOBILE

object InternetManager {

    fun isConnectedtoInternet(context: Context?): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        return if (Build.VERSION.SDK_INT < 23) {
            isConnected(cm.activeNetworkInfo)
        } else {
            isConnected(cm.getNetworkCapabilities(cm.activeNetwork))
        }
    }

    private fun isConnected(activeNetworkInfo: NetworkInfo?): Boolean {
        return when (activeNetworkInfo) {
            null -> false
            else -> with(activeNetworkInfo) { isConnected && (type == TYPE_WIFI || type == TYPE_MOBILE) }
        }
    }

    private fun isConnected(networkCapabilities: NetworkCapabilities?): Boolean {
        return when (networkCapabilities) {
            null -> false
            else -> with(networkCapabilities) { hasTransport(TRANSPORT_CELLULAR) || hasTransport(TRANSPORT_WIFI) }
        }
    }
}