package com.azamovhudstc.bookappwithretrofit2.retrofit2.service

import com.azamovhudstc.bookappwithretrofit2.model.VerifyResponse
import com.azamovhudstc.bookappwithretrofit2.retrofit2.request.AuthRequest
import com.azamovhudstc.bookappwithretrofit2.retrofit2.request.VerifyRequest
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.AuthResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {

    @POST("/auth/sign-up")
    fun register(@Body data: AuthRequest.RegisterRequest): Call<AuthResponse.RegisterResponse>

    @POST("/auth/sign-in")
    fun login(@Body data: AuthRequest.LoginRequest): Call<AuthResponse.LoginResponse>

    @POST("/auth/sign-up/verify")
    fun verify(@Header("Authorization") authorization: String, @Body code: VerifyRequest ): Call<VerifyResponse>

    @POST("/auth/sign-in/verify")
    fun verifySign(@Header("Authorization") authorization: String, @Body code:VerifyRequest): Call<VerifyResponse>



}