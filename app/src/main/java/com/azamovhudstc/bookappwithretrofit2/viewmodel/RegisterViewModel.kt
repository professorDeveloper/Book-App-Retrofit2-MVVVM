package com.azamovhudstc.bookappwithretrofit2.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.azamovhudstc.bookappwithretrofit2.model.local.AppReference
import com.azamovhudstc.bookappwithretrofit2.retrofit2.cilent.ApiClient
import com.azamovhudstc.bookappwithretrofit2.retrofit2.request.AuthRequest
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.AuthResponse
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.ErrorResponse
import com.azamovhudstc.bookappwithretrofit2.retrofit2.service.AuthService
import com.azamovhudstc.bookappwithretrofit2.utils.isConnected
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {
    private val authApi = ApiClient.retrofit.create(AuthService::class.java)
    private val pref = AppReference.getInstance()
    private var registerCall: Call<AuthResponse.RegisterResponse>? = null
    val openVerifyScreenLiveData = MutableLiveData<Unit>()
    val notConnectionLiveData = MutableLiveData<Unit>()
    val errorLiveData: MutableLiveData<String> = MutableLiveData()

    val changeButtonStatusLiveData = MutableLiveData<Boolean>()
    val progressLiveData = MutableLiveData<Boolean>()
    fun register(name: String, password: String, phone: String, lastName: String) {
        if (!isConnected()) {
            notConnectionLiveData.value = Unit
            return
        }
        progressLiveData.value = true    // progress show
        changeButtonStatusLiveData.value = false  // disable
        registerCall =
            authApi.register(AuthRequest.RegisterRequest(name, lastName, password, phone))
        registerCall?.enqueue(object : Callback<AuthResponse.RegisterResponse> {
            override fun onResponse(
                call: Call<AuthResponse.RegisterResponse>,
                response: Response<AuthResponse.RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        pref.verifyToken = it.token
                        pref.name = name
                        openVerifyScreenLiveData.value = Unit
                    }
                } else {
                    val gson = Gson()
                    response.errorBody()?.let {
                        val error =
                            gson.fromJson<ErrorResponse>(it.string(), ErrorResponse::class.java)
                        Log.d("TTT", error.message)
                        errorLiveData.value = error.message
                    }
                }
                progressLiveData.value = false
                changeButtonStatusLiveData.value = true
            }

            override fun onFailure(call: Call<AuthResponse.RegisterResponse>, t: Throwable) {
                progressLiveData.value = false         //// progress hide
                changeButtonStatusLiveData.value = true   // enable
                Log.d("!@#", "onFailure: ${t.message}")
            }
        })
    }

}