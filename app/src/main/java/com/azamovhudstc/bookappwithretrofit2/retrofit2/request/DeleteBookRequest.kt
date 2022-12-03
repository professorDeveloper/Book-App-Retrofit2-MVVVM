package com.azamovhudstc.bookappwithretrofit2.retrofit2.request


import com.google.gson.annotations.SerializedName

data class DeleteBookRequest(
    @SerializedName("bookId")
    val bookId: Int
)