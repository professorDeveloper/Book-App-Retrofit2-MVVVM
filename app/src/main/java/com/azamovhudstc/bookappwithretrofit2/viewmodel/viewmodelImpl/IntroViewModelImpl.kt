package com.azamovhudstc.bookappwithretrofit2.viewmodel.viewmodelImpl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.azamovhudstc.bookappwithretrofit2.viewmodel.IntroViewModel

class IntroViewModelImpl:IntroViewModel,ViewModel() {
    override val clickLiveData: MutableLiveData<Unit> =MutableLiveData()

    override fun click() {
        clickLiveData.value=Unit
    }

}