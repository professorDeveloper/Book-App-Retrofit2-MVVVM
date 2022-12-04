package com.azamovhudstc.bookappwithretrofit2.viewmodel.viewmodelImpl

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.azamovhudstc.bookappwithretrofit2.model.local.AppReference
import com.azamovhudstc.bookappwithretrofit2.retrofit2.cilent.ApiClient
import com.azamovhudstc.bookappwithretrofit2.retrofit2.request.AddBookRequest
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.AddBookResponse
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.ErrorResponse
import com.azamovhudstc.bookappwithretrofit2.retrofit2.service.BookService
import com.azamovhudstc.bookappwithretrofit2.viewmodel.AddBookViewModel
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddBookViewModelImp : AddBookViewModel, ViewModel() {
    override val successBackLiveData: MutableLiveData<Unit> = MutableLiveData()
    override val errorLiveData: MutableLiveData<String> = MutableLiveData()
    override val progressStatusLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val bookApi = ApiClient.retrofit.create(BookService::class.java)

    override fun successBack() {
        successBackLiveData.value = Unit
    }

    override fun addBook(bookRequest: AddBookRequest) {
        progressStatusLiveData.value = true
        val token = AppReference.getInstance().token
        bookApi.addBook("Bearer $token", bookRequest).enqueue(object : Callback<AddBookResponse> {
            override fun onResponse(
                call: Call<AddBookResponse>,
                response: Response<AddBookResponse>
            ) {
                if (response.isSuccessful) {
                    progressStatusLiveData.value = false
                    successBack()
                } else {
                    val gson = Gson()
                    response.errorBody()?.let {
                        val error = gson.fromJson(it.string(), ErrorResponse::class.java)
                        if (error != null) {
                            errorLiveData.value = error.message
                            progressStatusLiveData.value = false
                        }

                    }
                }
            }

            override fun onFailure(call: Call<AddBookResponse>, t: Throwable) {
                Log.d("!@#", "onFailure: ${t.message}")
            }

        })
    }
}