package com.azamovhudstc.bookappwithretrofit2.model


import com.google.gson.annotations.SerializedName

data class VerifyResponse(
    @SerializedName("token")
    val token: String
)