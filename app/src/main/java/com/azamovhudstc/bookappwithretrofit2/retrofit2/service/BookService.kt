package com.azamovhudstc.bookappwithretrofit2.retrofit2.service

import com.azamovhudstc.bookappwithretrofit2.retrofit2.request.AddBookRequest
import com.azamovhudstc.bookappwithretrofit2.retrofit2.request.DeleteBookRequest
import com.azamovhudstc.bookappwithretrofit2.retrofit2.request.EditBookRequest
import com.azamovhudstc.bookappwithretrofit2.retrofit2.request.FavouriteBookRequest
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.AddBookResponse
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.BooksResponse
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.BooksResponseItem
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.ErrorResponse
import okhttp3.internal.http.hasBody
import retrofit2.Call
import retrofit2.http.*

interface BookService {

    @GET("/books")
    fun getAllBooks(@Header("Authorization") token: String): Call<BooksResponse>

    @POST("/book")
    fun addBook(
        @Header("Authorization") token: String,
        @Body addBookRequest: AddBookRequest
    ): Call<AddBookResponse>

    @PUT("/book")
    fun editBook(
        @Header("Authorization") token: String,
        @Body data: EditBookRequest
    ): Call<BooksResponseItem>

    @HTTP(method = "DELETE", path = "/book", hasBody = true)
    fun deleteBook(
        @Header("Authorization") token: String,
        @Body body: DeleteBookRequest
    ): Call<ErrorResponse>

    @POST("book/change-fav")
    fun addFavouriteBook(
        @Header("Authorization") token: String,
        @Body favouriteBookRequest: FavouriteBookRequest
    ): Call<ErrorResponse>
}