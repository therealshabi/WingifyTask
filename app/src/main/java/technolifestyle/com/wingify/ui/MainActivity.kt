package technolifestyle.com.wingify.ui

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*
import technolifestyle.com.wingify.AppConfig
import technolifestyle.com.wingify.Injection
import technolifestyle.com.wingify.NetworkChangeReceiver
import technolifestyle.com.wingify.R
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private lateinit var networkChangeReceiver: NetworkChangeReceiver

    private var isConnected: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.plant(Timber.DebugTree())

        networkChangeReceiver = NetworkChangeReceiver()

        networkChangeReceiver.isConnected.observe(this, Observer {
            isConnected = it
        })

        viewModel = ViewModelProviders.of(
            this,
            Injection.provideViewModel(application)
        )
            .get(MainViewModel::class.java)

        setClickListeners()
    }

    private fun setClickListeners() {
        banner.setOnClickListener {
            viewModel.updateCounter(AppConfig.Elements.BANNER, isConnected)
        }

        titleTextView.setOnClickListener {
            viewModel.updateCounter(AppConfig.Elements.TITILE, isConnected)
        }

        contentTextView.setOnClickListener {
            viewModel.updateCounter(AppConfig.Elements.CONTENT, isConnected)
        }

        button.setOnClickListener {
            viewModel.updateCounter(AppConfig.Elements.BUTTON, isConnected)
        }
    }

    override fun onStart() {
        super.onStart()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkChangeReceiver, filter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(networkChangeReceiver)
    }
}
