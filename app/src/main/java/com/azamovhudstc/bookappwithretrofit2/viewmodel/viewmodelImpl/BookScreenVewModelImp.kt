package com.azamovhudstc.bookappwithretrofit2.viewmodel.viewmodelImpl

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.azamovhudstc.bookappwithretrofit2.model.local.AppReference
import com.azamovhudstc.bookappwithretrofit2.retrofit2.cilent.ApiClient
import com.azamovhudstc.bookappwithretrofit2.retrofit2.request.DeleteBookRequest
import com.azamovhudstc.bookappwithretrofit2.retrofit2.request.FavouriteBookRequest
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.BooksResponse
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.BooksResponseItem
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.ErrorResponse
import com.azamovhudstc.bookappwithretrofit2.retrofit2.service.BookService
import com.azamovhudstc.bookappwithretrofit2.utils.isConnected
import com.azamovhudstc.bookappwithretrofit2.viewmodel.BooksScreenViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookScreenVewModelImp : BooksScreenViewModel, ViewModel() {
    override val getBooksLiveData: MutableLiveData<BooksResponse> = MutableLiveData()
    override val addBookLiveData: MutableLiveData<Unit> = MutableLiveData()
    override val isFavouriteLiveData: MutableLiveData<String> = MutableLiveData()
    override var editBookLiveData: MutableLiveData<BooksResponseItem> = MutableLiveData()
    override var noInternet = MutableLiveData<Boolean>()
    override val deleteBookLiveData: MutableLiveData<String> = MutableLiveData()
    override val showBookLiveData: MutableLiveData<BooksResponseItem> = MutableLiveData()
    override var progressLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val bookApi = ApiClient.retrofit.create(BookService::class.java)

    override fun getBooks() {
        if (!isConnected()){
            noInternet.value=true
            return
        }
        noInternet.value=false
        progressLiveData.value = true
        var token = AppReference.getInstance().token
        bookApi.getAllBooks("Bearer $token").enqueue(object : Callback<BooksResponse> {
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
        if (!isConnected()){
            noInternet.value=true
            return
        }
        noInternet.value=false
        var token = AppReference.getInstance().token
        var delete = DeleteBookRequest(bookId = booksResponseItem.id)
        bookApi.deleteBook("Bearer $token", delete)
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
                    Log.d("!@#", "onFailure: ${t.message}")

                }

            })
    }

    override fun showBook(booksResponse: BooksResponseItem) {
        showBookLiveData.value = booksResponse
    }

    override fun addBook() {
        if (!isConnected()){
            noInternet.value=true
            return
        }
        noInternet.value=false
        addBookLiveData.value = Unit
    }

    override fun likeBook(booksResponseItem: BooksResponseItem) {
        if (!isConnected()){
            noInternet.value=true
            return
        }
        noInternet.value=false
        val token = AppReference.getInstance().token
        progressLiveData.value=true
        bookApi.addFavouriteBook("Bearer $token", FavouriteBookRequest(booksResponseItem.id))
            .enqueue(object : Callback<ErrorResponse> {
                override fun onResponse(
                    call: Call<ErrorResponse>,
                    response: Response<ErrorResponse>
                ) {
                    if (response.isSuccessful){
                        progressLiveData.value=false
                        isFavouriteLiveData.value=response.body()?.message
                        getBooks()
                    }
                }

                override fun onFailure(call: Call<ErrorResponse>, t: Throwable) {
                    Log.d("!@#", "onFailure: ${t.message}")

                }


            })
    }

    override fun editBook(contact: BooksResponseItem) {
        if (!isConnected()){
            noInternet.value=true
            return
        }
        noInternet.value=false
        editBookLiveData.value = contact
    }
}