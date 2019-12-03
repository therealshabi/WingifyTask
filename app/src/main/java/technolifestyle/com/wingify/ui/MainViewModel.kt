package technolifestyle.com.wingify.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import technolifestyle.com.wingify.AppConfig
import technolifestyle.com.wingify.NetworkChangeReceiver
import technolifestyle.com.wingify.api.MockApiService
import technolifestyle.com.wingify.db.ElementDatabase
import technolifestyle.com.wingify.db.ElementsDao
import technolifestyle.com.wingify.model.ApiResponseModel
import technolifestyle.com.wingify.model.ElementModel
import technolifestyle.com.wingify.model.RequestModel
import technolifestyle.com.wingify.utils.InternetManager
import technolifestyle.com.wingify.utils.NetworkUtil
import timber.log.Timber
import java.util.concurrent.Executors

class MainViewModel(
    application: Application,
    private val networkChangeReceiver: NetworkChangeReceiver
) : AndroidViewModel(application), Callback<ApiResponseModel> {

    private val elementsDao: ElementsDao?
    private val database: ElementDatabase?

    private val apiService: MockApiService

    private val context: Context
    private val executorService = Executors.newSingleThreadExecutor()

    init {
        database = ElementDatabase.getDatabase(application)
        elementsDao = database?.getElementDao()
        context = application.applicationContext

        apiService = NetworkUtil.getApiImplementation(MockApiService::class.java)

        sendUnsentElementData()
    }

    fun updateCounter(element: AppConfig.Elements, isConnected: Boolean) {
        if (isConnected) {
            Timber.i("Connected")
            apiService.sendElementDetails(RequestModel(element.toString(), 1)).enqueue(this)
        } else {
            Timber.i("Disconnected")
            storeUnsentElement(element)
        }
    }

    private fun storeUnsentElement(element: AppConfig.Elements) {
        executorService.execute {
            val id = elementsDao?.getElement(element.toString())

            if (id == null) {
                Timber.d("Element stored")
                elementsDao?.addElement(
                    ElementModel(
                        element = element.toString(),
                        count = 1,
                        unsentFlag = true
                    )
                )
            } else {
                Timber.d("Updating element count")
                elementsDao?.updateElementCount(element.toString())
            }
        }
    }

    private fun sendUnsentElementData() {
        if (!InternetManager.isConnectedtoInternet(context)) {
            Timber.i("No connected to internet")
            return
        }
        executorService.execute {
            elementsDao?.getUnsentElements()?.forEach {
                it.apply {
                    val requestModel = RequestModel(it.element, it.count)
                    apiService.sendElementDetails(requestModel).enqueue(this@MainViewModel)
                }
            }
        }
    }

    override fun onFailure(call: Call<ApiResponseModel>, t: Throwable) {
        Timber.e("Api request failed: $t")
    }

    override fun onResponse(call: Call<ApiResponseModel>, response: Response<ApiResponseModel>) {
        if (response.isSuccessful) {
            Timber.i("Request successful: ${response.body()}")
            executorService.execute {
                elementsDao?.resetElementCount(response.body()?.element!!)
            }
            return
        }
    }
}