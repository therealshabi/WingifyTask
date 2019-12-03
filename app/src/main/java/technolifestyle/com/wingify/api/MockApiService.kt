package technolifestyle.com.wingify.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import technolifestyle.com.wingify.model.ApiResponseModel
import technolifestyle.com.wingify.model.RequestModel

interface MockApiService {

    @POST("/")
    fun sendElementDetails(@Body requestModel: RequestModel): Call<ApiResponseModel>
}