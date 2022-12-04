package com.azamovhudstc.bookappwithretrofit2.retrofit2.service

import com.azamovhudstc.bookappwithretrofit2.retrofit2.request.*
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.*
import okhttp3.internal.http.hasBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.*

interface BookService {

    @GET("/books")
    fun getAllBooks(@Header("Authorization") token: String): Call<BooksResponse>

    @POST("/book")
    fun addBook(
        @Header("Authorization") token: String,
        @Body addBookRequest: AddBookRequest
    ): Call<AddBookResponse>

    @GET("/books/users")
    fun getAllUsers(@Header("Authorization") token: String): Call<GetSocialUserResponse>

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

    @GET("books/{userId}")
    fun getBooksByUserId(
        @Header("Authorization") token: String,
        @Path("userId") userId: Int
    ): Call<GetUserBooksResponse>

    @POST("book/rate")
    fun rate(
        @Header("Authorization") token: String, @Body data: RateBookRequest
    ): Call<ErrorResponse>

}