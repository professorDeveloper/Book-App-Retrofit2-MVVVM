package com.azamovhudstc.bookappwithretrofit2.viewmodel

import androidx.lifecycle.MutableLiveData
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.BooksResponseItem

interface HomeScreenViewModel {
    val viewPagerLiveData:MutableLiveData<Unit>
    fun viewPagerSetup()
}