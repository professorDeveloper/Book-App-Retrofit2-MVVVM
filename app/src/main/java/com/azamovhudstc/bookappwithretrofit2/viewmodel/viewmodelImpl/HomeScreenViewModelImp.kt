package com.azamovhudstc.bookappwithretrofit2.viewmodel.viewmodelImpl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.azamovhudstc.bookappwithretrofit2.viewmodel.HomeScreenViewModel

class HomeScreenViewModelImp:HomeScreenViewModel,ViewModel() {
    override val viewPagerLiveData: MutableLiveData<Unit> =MutableLiveData()

    override fun viewPagerSetup() {
        viewPagerLiveData.value=Unit
    }
}