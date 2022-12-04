package com.azamovhudstc.bookappwithretrofit2.viewmodel.viewmodelImpl

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.azamovhudstc.bookappwithretrofit2.model.local.AppReference
import com.azamovhudstc.bookappwithretrofit2.retrofit2.cilent.ApiClient
import com.azamovhudstc.bookappwithretrofit2.retrofit2.request.EditBookRequest
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.BooksResponseItem
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.ErrorResponse
import com.azamovhudstc.bookappwithretrofit2.retrofit2.service.BookService
import com.azamovhudstc.bookappwithretrofit2.viewmodel.EditBookScreenViewModel
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditBookScreenViewModelImp : EditBookScreenViewModel, ViewModel() {
    override val progressLiveData: MutableLiveData<Boolean> = MutableLiveData()
    override val successBackLiveData: MutableLiveData<Unit> = MutableLiveData()
    override val errorLiveData: MutableLiveData<String> = MutableLiveData()
    private val bookApi = ApiClient.retrofit.create(BookService::class.java)

    override fun successBack() {
        successBackLiveData.value = Unit
    }

    override fun editBook(editBookRequest: EditBookRequest) {
        progressLiveData.value=true
        val token = AppReference.getInstance().token
        bookApi.editBook("Bearer $token", editBookRequest).enqueue(object : Callback<BooksResponseItem> {
            override fun onResponse(call: Call<BooksResponseItem>, response: Response<BooksResponseItem>) {
                if (response.isSuccessful){
                    progressLiveData.value=false
                    successBack()
                }
                else {
                    val gson = Gson()
                    response.errorBody()?.let {
                        val error = gson.fromJson(it.string(), ErrorResponse::class.java)
                        if (error != null) {
                            errorLiveData.value = error.message
                            progressLiveData.value = false
                        }

                    }
                }
            }

            override fun onFailure(call: Call<BooksResponseItem>, t: Throwable) {
                Log.d("!@#", "onFailure: ${t.message}")

            }

        })
    }
}