package com.azamovhudstc.bookappwithretrofit2.viewmodel.viewmodelImpl

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

class LoginViewModel : ViewModel() {
    private val authApi = ApiClient.retrofit.create(AuthService::class.java)
    private val pref = AppReference.getInstance()
    private var loginCall: Call<AuthResponse.LoginResponse>? = null
    val errorLiveData: MutableLiveData<String> = MutableLiveData()

    val openRegisterScreenLiveData = MutableLiveData<Unit>()
    val openVerifyScreen = MutableLiveData<Unit>()
    val notConnectionLiveData = MutableLiveData<Unit>()
    val changeButtonStatusLiveData = MutableLiveData<Boolean>()
    val progressLiveData = MutableLiveData<Boolean>()

    fun openRegisterScreen() {
        loginCall?.let {
            if (it.isExecuted) it.cancel()
        }
        openRegisterScreenLiveData.value = Unit
    }

    fun login(password: String, phone: String) {
        if (!isConnected()) {
            notConnectionLiveData.value = Unit
            return
        }
        progressLiveData.value = true    // progress show
        changeButtonStatusLiveData.value = false  // disable
        loginCall = authApi.login(AuthRequest.LoginRequest(password, phone))
        loginCall?.enqueue(object : Callback<AuthResponse.LoginResponse> {
            override fun onResponse(
                call: Call<AuthResponse.LoginResponse>,
                response: Response<AuthResponse.LoginResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        pref.verifyToken = it.token
                        openVerifyScreen.value = Unit

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

            override fun onFailure(call: Call<AuthResponse.LoginResponse>, t: Throwable) {
                progressLiveData.value = false         //// progress hide
                changeButtonStatusLiveData.value = true   // enable
            }
        })
    }
}

