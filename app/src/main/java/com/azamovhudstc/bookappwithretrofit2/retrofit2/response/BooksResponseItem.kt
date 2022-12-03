package com.azamovhudstc.bookappwithretrofit2.retrofit2.response


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BooksResponseItem(
    @SerializedName("author")
    val author: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("fav")
    val fav: Boolean,
    @SerializedName("id")
    val id: Int,
    @SerializedName("pageCount")
    val pageCount: Int,
    @SerializedName("title")
    val title: String
):Serializable