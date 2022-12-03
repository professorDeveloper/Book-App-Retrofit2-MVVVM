package com.azamovhudstc.bookappwithretrofit2.retrofit2.request


import com.google.gson.annotations.SerializedName

data class FavouriteBookRequest(
    @SerializedName("bookId")
    val bookId: Int
)