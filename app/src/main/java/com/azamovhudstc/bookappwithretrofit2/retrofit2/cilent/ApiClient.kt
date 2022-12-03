package com.azamovhudstc.bookappwithretrofit2.retrofit2.cilent

import com.azamovhudstc.bookappwithretrofit2.app.App
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {


    private val myClient = OkHttpClient.Builder()
        .addInterceptor(ChuckInterceptor(App.instance))
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://143.198.48.222:82")
        .client(myClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

