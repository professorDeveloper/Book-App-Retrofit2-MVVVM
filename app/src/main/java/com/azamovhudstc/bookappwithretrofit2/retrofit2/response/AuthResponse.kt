package com.azamovhudstc.bookappwithretrofit2.retrofit2.response


import com.google.gson.annotations.SerializedName

sealed class AuthResponse{
    data class RegisterResponse(
        @SerializedName("token")
        val token: String
    )

    data class LoginResponse(
        @SerializedName("token")
        val token: String
    )

}