package com.azamovhudstc.bookappwithretrofit2.retrofit2.response


import com.google.gson.annotations.SerializedName

data class GetSocialUserResponseItem(
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("lastName")
    val lastName: String
)