package com.azamovhudstc.bookappwithretrofit2.viewmodel

import androidx.lifecycle.MutableLiveData

interface IntroViewModel {
    fun click()
    val clickLiveData:MutableLiveData<Unit>
}