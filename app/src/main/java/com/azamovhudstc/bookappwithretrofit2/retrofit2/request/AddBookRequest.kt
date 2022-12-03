package com.azamovhudstc.bookappwithretrofit2.retrofit2.request


import com.google.gson.annotations.SerializedName

data class AddBookRequest(
    @SerializedName("author")
    val author: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("pageCount")
    val pageCount: Int,
    @SerializedName("title")
    val title: String
)