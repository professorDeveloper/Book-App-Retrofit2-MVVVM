package com.azamovhudstc.bookappwithretrofit2.viewmodel.viewmodelImpl

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.azamovhudstc.bookappwithretrofit2.model.local.AppReference
import com.azamovhudstc.bookappwithretrofit2.retrofit2.cilent.ApiClient
import com.azamovhudstc.bookappwithretrofit2.retrofit2.request.DeleteBookRequest
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.BooksResponse
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.BooksResponseItem
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.ErrorResponse
import com.azamovhudstc.bookappwithretrofit2.retrofit2.service.AuthService
import com.azamovhudstc.bookappwithretrofit2.retrofit2.service.BookService
import com.azamovhudstc.bookappwithretrofit2.viewmodel.BooksScreenViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookScreenVewModelImp : BooksScreenViewModel, ViewModel() {
    override val getBooksLiveData: MutableLiveData<BooksResponse> = MutableLiveData()
    override val addBookLiveData: MutableLiveData<Unit> = MutableLiveData()
    override var editBookLiveData: MutableLiveData<BooksResponseItem> = MutableLiveData()

    override val deleteBookLiveData: MutableLiveData<String> = MutableLiveData()
    override val showBookLiveData: MutableLiveData<BooksResponseItem> = MutableLiveData()
    override var progressLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val authApi = ApiClient.retrofit.create(BookService::class.java)

    override fun getBooks() {
        progressLiveData.value = true
        var token = AppReference.getInstance().token
        authApi.getAllBooks("Bearer $token").enqueue(object : Callback<BooksResponse> {
            override fun onResponse(call: Call<BooksResponse>, response: Response<BooksResponse>) {
                if (response.isSuccessful) {
                    progressLiveData.value = false
                    getBooksLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<BooksResponse>, t: Throwable) {
                Log.d("!@#", "onFailure: ${t.message}")
            }

        })
    }

    override fun deleteBook(booksResponseItem: BooksResponseItem) {
        var token = AppReference.getInstance().token
        var delete =DeleteBookRequest(bookId = booksResponseItem.id)
        authApi.deleteBook("Bearer $token", delete)
            .enqueue(object : Callback<ErrorResponse> {
                override fun onResponse(
                    call: Call<ErrorResponse>,
                    response: Response<ErrorResponse>
                ) {
                    if (response.isSuccessful) {
                        deleteBookLiveData.value = response.body()?.message.toString()

                        getBooks()
                    }

                }

                override fun onFailure(call: Call<ErrorResponse>, t: Throwable) {

                }

            })
    }

    override fun showBook(booksResponse: BooksResponseItem) {
        showBookLiveData.value=booksResponse
    }

    override fun addBook() {
        addBookLiveData.value = Unit
    }

    override fun editBook(contact: BooksResponseItem) {
        editBookLiveData.value=contact
    }
}