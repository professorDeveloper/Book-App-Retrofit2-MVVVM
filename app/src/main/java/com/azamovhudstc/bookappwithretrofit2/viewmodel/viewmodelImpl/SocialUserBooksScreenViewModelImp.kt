package com.azamovhudstc.bookappwithretrofit2.viewmodel.viewmodelImpl

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.azamovhudstc.bookappwithretrofit2.model.local.AppReference
import com.azamovhudstc.bookappwithretrofit2.retrofit2.cilent.ApiClient
import com.azamovhudstc.bookappwithretrofit2.retrofit2.request.GetUserBooksResponse
import com.azamovhudstc.bookappwithretrofit2.retrofit2.request.RateBookRequest
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.ErrorResponse
import com.azamovhudstc.bookappwithretrofit2.retrofit2.service.BookService
import com.azamovhudstc.bookappwithretrofit2.utils.isConnected
import com.azamovhudstc.bookappwithretrofit2.viewmodel.SocialUserBooksScreenViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SocialUserBooksScreenViewModelImp : SocialUserBooksScreenViewModel, ViewModel() {
    override val progressLiveData: MutableLiveData<Boolean> = MutableLiveData()
    override val getAllBooksLiveData: MutableLiveData<GetUserBooksResponse> = MutableLiveData()
    private val bookApi = ApiClient.retrofit.create(BookService::class.java)
    override var noInternet = MutableLiveData<Boolean>()
    override val isFavouriteLiveData: MutableLiveData<String> = MutableLiveData()

    override fun getAllBooks(userId: Int) {
        progressLiveData.value = true
        if (!isConnected()){
            noInternet.value=true
            return
        }
        noInternet.value=false
        val token = AppReference.getInstance().token
        bookApi.getBooksByUserId("Bearer $token", userId)
            .enqueue(object : Callback<GetUserBooksResponse> {
                override fun onResponse(
                    call: Call<GetUserBooksResponse>,
                    response: Response<GetUserBooksResponse>
                ) {
                    if (response.isSuccessful) {
                        progressLiveData.value = false
                        getAllBooksLiveData.value = response.body()
                        Log.d("!@#$%", "onResponse: ${response.body()}")

                    }
                }

                override fun onFailure(call: Call<GetUserBooksResponse>, t: Throwable) {
                    Log.d("!@#AAA", "onResponse: ${t.message.toString()}")
                }
            })
    }

    override fun bookRate( request: RateBookRequest,id:Int) {
        if (!isConnected()){
            noInternet.value=true
            return
        }
        noInternet.value=false

        progressLiveData.value = true
        val token = AppReference.getInstance().token
        Log.d("TAG", "bookRate:$token ")
        bookApi.rate("Bearer $token",request).enqueue(object :Callback<ErrorResponse>{
            override fun onResponse(call: Call<ErrorResponse>, response: Response<ErrorResponse>) {
                if (response.isSuccessful){
                    progressLiveData.value=false
                    isFavouriteLiveData.value=response.body()?.message
                    getAllBooks(id)
                }
            }

            override fun onFailure(call: Call<ErrorResponse>, t: Throwable) {
                Log.d("!@#$", "onFailure: ${t.message.toString()}")
            }

        })
    }
}