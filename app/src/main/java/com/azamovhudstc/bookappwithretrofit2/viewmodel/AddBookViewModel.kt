package com.azamovhudstc.bookappwithretrofit2.viewmodel

import androidx.lifecycle.MutableLiveData
import com.azamovhudstc.bookappwithretrofit2.retrofit2.request.AddBookRequest

interface AddBookViewModel {
    val successBackLiveData:MutableLiveData<Unit>
    val errorLiveData:MutableLiveData<String>
    val progressStatusLiveData:MutableLiveData<Boolean>
    fun successBack()
    fun addBook(bookRequest: AddBookRequest)
}