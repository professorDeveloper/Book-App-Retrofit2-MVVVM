package com.azamovhudstc.bookappwithretrofit2.retrofit2.request


import com.google.gson.annotations.SerializedName

data class RateBookRequest(
    @SerializedName("bookId")
    val bookId: Int,
    @SerializedName("isLike")
    val isLike: Boolean
)