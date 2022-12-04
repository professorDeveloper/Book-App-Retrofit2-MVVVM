package com.azamovhudstc.bookappwithretrofit2.viewmodel.viewmodelImpl

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.azamovhudstc.bookappwithretrofit2.model.local.AppReference
import com.azamovhudstc.bookappwithretrofit2.retrofit2.cilent.ApiClient
import com.azamovhudstc.bookappwithretrofit2.retrofit2.request.FavouriteBookRequest
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.BooksResponseItem
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.ErrorResponse
import com.azamovhudstc.bookappwithretrofit2.retrofit2.service.BookService
import com.azamovhudstc.bookappwithretrofit2.utils.isConnected
import com.azamovhudstc.bookappwithretrofit2.viewmodel.ShowBookViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowBookViewModelImp : ShowBookViewModel, ViewModel() {
    override val backLiveData: MutableLiveData<Unit> = MutableLiveData()
    override val isFavouriteLiveData: MutableLiveData<String> = MutableLiveData()
    override val showBookLiveData: MutableLiveData<BooksResponseItem> = MutableLiveData()
    private val bookApi = ApiClient.retrofit.create(BookService::class.java)

    override fun back() {
        backLiveData.value = Unit
    }

    override fun likeBook(booksResponseItem: BooksResponseItem) {
        if (!isConnected()) {
            return
        }
        val token = AppReference.getInstance().token
        bookApi.addFavouriteBook("Bearer $token", FavouriteBookRequest(booksResponseItem.id))
            .enqueue(object : Callback<ErrorResponse> {
                override fun onResponse(
                    call: Call<ErrorResponse>,
                    response: Response<ErrorResponse>
                ) {
                    if (response.isSuccessful) {
                        isFavouriteLiveData.value = response.body()?.message
                    }
                }

                override fun onFailure(call: Call<ErrorResponse>, t: Throwable) {
                    Log.d("!@#", "onFailure: ${t.message}")

                }


            })
    }

    override fun showBook(booksResponseItem: BooksResponseItem) {
        showBookLiveData.value = booksResponseItem
    }
}