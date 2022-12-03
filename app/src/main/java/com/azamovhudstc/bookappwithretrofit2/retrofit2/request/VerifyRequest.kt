package com.azamovhudstc.bookappwithretrofit2.retrofit2.request


import com.google.gson.annotations.SerializedName

data class VerifyRequest(
    @SerializedName("code")
    val code: String
)