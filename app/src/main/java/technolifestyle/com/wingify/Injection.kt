package technolifestyle.com.wingify

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import technolifestyle.com.wingify.ui.ViewModelFactory

object Injection {

    fun provideViewModel(application: Application, networkChangeReceiver: NetworkChangeReceiver):
            ViewModelProvider.Factory {
        return ViewModelFactory(application, networkChangeReceiver)
    }
}