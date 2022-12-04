package com.azamovhudstc.bookappwithretrofit2.viewmodel

import androidx.lifecycle.MutableLiveData
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.GetSocialUserResponse
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.GetSocialUserResponseItem

interface SocialBooksScreenViewModel {
    val progressLiveData:MutableLiveData<Boolean>
    val getSocialUsersLiveData: MutableLiveData<GetSocialUserResponse>
    val clickUserLiveData:MutableLiveData<GetSocialUserResponseItem>
    fun getUsers()
    fun clickUser(getSocialUserResponseItem: GetSocialUserResponseItem)

}