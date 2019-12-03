package technolifestyle.com.wingify.model

import com.google.gson.annotations.SerializedName

data class ApiResponseModel(
    @SerializedName("status")
    val status: String,
    @SerializedName("element_name")
    val element: String?
)