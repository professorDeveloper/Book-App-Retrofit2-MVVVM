package com.azamovhudstc.bookappwithretrofit2.viewmodel

import androidx.lifecycle.MutableLiveData
import com.azamovhudstc.bookappwithretrofit2.retrofit2.request.EditBookRequest

interface EditBookScreenViewModel {
    val progressLiveData:MutableLiveData<Boolean>
    val successBackLiveData:MutableLiveData<Unit>
    val errorLiveData:MutableLiveData<String>
    fun successBack()
    fun editBook(editBookRequest: EditBookRequest)
}