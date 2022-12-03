package com.azamovhudstc.bookappwithretrofit2.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.azamovhudstc.bookappwithretrofit2.model.ErorrVerify
import com.azamovhudstc.bookappwithretrofit2.model.VerifyResponse
import com.azamovhudstc.bookappwithretrofit2.model.local.AppReference
import com.azamovhudstc.bookappwithretrofit2.retrofit2.cilent.ApiClient
import com.azamovhudstc.bookappwithretrofit2.retrofit2.request.AuthRequest
import com.azamovhudstc.bookappwithretrofit2.retrofit2.request.VerifyRequest
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.AuthResponse
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.ErrorResponse
import com.azamovhudstc.bookappwithretrofit2.retrofit2.service.AuthService
import com.azamovhudstc.bookappwithretrofit2.utils.isConnected
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerifyViewModel : ViewModel() {
    private val authApi = ApiClient.retrofit.create(AuthService::class.java)
    private val pref = AppReference.getInstance()
    val errorLiveData: MutableLiveData<String> = MutableLiveData()

    val openHomeScreen = MutableLiveData<Unit>()
    val notConnectionLiveData = MutableLiveData<Unit>()
    val changeButtonStatusLiveData = MutableLiveData<Boolean>()
    val progressLiveData = MutableLiveData<Boolean>()
    val openVerifyScreen = MutableLiveData<Unit>()
    val openVerifyScreenLiveData = MutableLiveData<Unit>()

    fun verify(token: String, phone: String) {
        if (!isConnected()) {
            notConnectionLiveData.value = Unit
            return
        }
        progressLiveData.value = true    // progress show
        changeButtonStatusLiveData.value = false  // disable
        authApi.verify("Bearer $token", VerifyRequest(phone))
            .enqueue(object : Callback<VerifyResponse> {
                override fun onResponse(
                    call: Call<VerifyResponse>,
                    response: Response<VerifyResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            pref.startScreen = "BOOK"
                            pref.token = it.token
                            openHomeScreen.value = Unit

                        }
                    } else {

                        val gson = Gson()
                        response.errorBody()?.let {
                            val error =
                                gson.fromJson(it.string(), ErorrVerify::class.java)

                            errorLiveData.value = error.message

                        }
                    }
                    progressLiveData.value = false
                    changeButtonStatusLiveData.value = true
                }

                override fun onFailure(call: Call<VerifyResponse>, t: Throwable) {
                    progressLiveData.value = false
                    Log.d("!@#", "onFailure:${t.message} ")//// progress hide
                    changeButtonStatusLiveData.value = true   // enable
                }
            })
    }

    fun verifySign(code: String, token: String) {
        if (!isConnected()) {
            notConnectionLiveData.value = Unit
            return
        }
        progressLiveData.value = true    // progress show
        changeButtonStatusLiveData.value = false  // disable
        authApi.verifySign("Bearer $token", VerifyRequest(code))
            .enqueue(object : Callback<VerifyResponse> {
                override fun onResponse(
                    call: Call<VerifyResponse>,
                    response: Response<VerifyResponse>
                ) {

                    if (response.isSuccessful) {
                        response.body()?.let {
                            pref.startScreen = "BOOK"
                            pref.token =
                                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxNSwiaXNzIjoiaHR0cDovLzEyNy4wLjAuMTo4MDgyLyIsImV4cCI6MTY5NTg4MDY0N30.t7SaMnFjwTagh8D5YbFfMr6yWI4fcQcHCnPO43oo2ZE"
                            pref.verifyToken = ""
                            openHomeScreen.value = Unit

                        }
                    } else {
                        val gson = Gson()
                        response.errorBody()?.let {
                            val error =
                                gson.fromJson(it.string(), ErorrVerify::class.java)

                            errorLiveData.value = error.message

                        }
                    }


                    progressLiveData.value = false
                    changeButtonStatusLiveData.value = true
                }

                override fun onFailure(call: Call<VerifyResponse>, t: Throwable) {
                    progressLiveData.value = false         //// progress hide
                    changeButtonStatusLiveData.value = true   // enable
                    Log.d("!@#sssaas", "onFailure:${t.message} ")//// progress hide
                }
            })
    }

    fun login(password: String, phone: String) {
        if (!isConnected()) {
            notConnectionLiveData.value = Unit
            return
        }
        progressLiveData.value = true    // progress show
        changeButtonStatusLiveData.value = false  // disable
        authApi.login(AuthRequest.LoginRequest(password, phone))
            ?.enqueue(object : Callback<AuthResponse.LoginResponse> {
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

    fun register(name: String, password: String, phone: String, lastName: String) {
        if (!isConnected()) {
            notConnectionLiveData.value = Unit
            return
        }
        progressLiveData.value = true    // progress show
        changeButtonStatusLiveData.value = false  // disable

        authApi.register(AuthRequest.RegisterRequest(name, lastName, password, phone))
            ?.enqueue(object : Callback<AuthResponse.RegisterResponse> {
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