package com.azamovhudstc.bookappwithretrofit2.viewmodel

import androidx.lifecycle.MutableLiveData
import com.azamovhudstc.bookappwithretrofit2.retrofit2.request.GetUserBooksResponse
import com.azamovhudstc.bookappwithretrofit2.retrofit2.request.RateBookRequest
import java.sql.RowId

interface SocialUserBooksScreenViewModel {
    val progressLiveData:MutableLiveData<Boolean>
    val isFavouriteLiveData: MutableLiveData<String>
    val  getAllBooksLiveData:MutableLiveData<GetUserBooksResponse>
    var noInternet : MutableLiveData<Boolean>
    fun getAllBooks(userId: Int)
    fun bookRate(request: RateBookRequest,id: Int)
}