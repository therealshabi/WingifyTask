package technolifestyle.com.wingify.model

import com.google.gson.annotations.SerializedName

data class RequestModel(
    @SerializedName("element_name")
    val element: String,
    @SerializedName("count")
    val count: Int = 1
)