package technolifestyle.com.wingify.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import technolifestyle.com.wingify.NetworkChangeReceiver

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val application: Application,
    private val networkChangeReceiver: NetworkChangeReceiver
) :
    ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application, networkChangeReceiver) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}