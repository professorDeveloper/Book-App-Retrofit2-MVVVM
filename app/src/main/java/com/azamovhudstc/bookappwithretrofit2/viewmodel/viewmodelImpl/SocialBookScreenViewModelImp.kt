package com.azamovhudstc.bookappwithretrofit2.viewmodel.viewmodelImpl

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.azamovhudstc.bookappwithretrofit2.model.local.AppReference
import com.azamovhudstc.bookappwithretrofit2.retrofit2.cilent.ApiClient
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.ErrorResponse
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.GetSocialUserResponse
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.GetSocialUserResponseItem
import com.azamovhudstc.bookappwithretrofit2.retrofit2.service.BookService
import com.azamovhudstc.bookappwithretrofit2.viewmodel.SocialBooksScreenViewModel
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SocialBookScreenViewModelImp : SocialBooksScreenViewModel, ViewModel() {
    override val progressLiveData: MutableLiveData<Boolean> = MutableLiveData()
    override val getSocialUsersLiveData: MutableLiveData<GetSocialUserResponse> = MutableLiveData()
    override val clickUserLiveData: MutableLiveData<GetSocialUserResponseItem> = MutableLiveData()
    private val bookApi = ApiClient.retrofit.create(BookService::class.java)

    override fun getUsers() {
        progressLiveData.value = true
        var token = AppReference.getInstance().token
        bookApi.getAllUsers("Bearer $token").enqueue(object : Callback<GetSocialUserResponse> {
            override fun onResponse(
                call: Call<GetSocialUserResponse>,
                response: Response<GetSocialUserResponse>
            ) {
                if (response.isSuccessful) {
                    progressLiveData.value = false
                    getSocialUsersLiveData.value = response.body()
                    Log.d("TTT", "onResponse: ${response.body()?.toString()}")
                } else {
                    val gson = Gson()
                    response.errorBody()?.let {
                        val error = gson.fromJson(it.string(), ErrorResponse::class.java)
                        if (error != null) {
                            Log.d("!@#", "onResponse: ${error.message}")
                        }

                    }

                }
            }

            override fun onFailure(call: Call<GetSocialUserResponse>, t: Throwable) {
                Log.d("!@#", "onResponse: ${t.message}")

            }

        })
    }

    override fun clickUser(getSocialUserResponseItem: GetSocialUserResponseItem) {
        clickUserLiveData.value = getSocialUserResponseItem

    }
}