package technolifestyle.com.wingify

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.lifecycle.MutableLiveData
import technolifestyle.com.wingify.utils.InternetManager.isConnectedtoInternet
import timber.log.Timber

class NetworkChangeReceiver : BroadcastReceiver() {
    var isConnected: MutableLiveData<Boolean> = MutableLiveData()

    override fun onReceive(context: Context?, intent: Intent?) {
        if (ConnectivityManager.CONNECTIVITY_ACTION != intent?.action) {
            return
        }
        Timber.e("Connectivity Action received")
        isConnected.value = isConnectedtoInternet(context)
    }
}