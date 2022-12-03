package com.azamovhudstc.bookappwithretrofit2.viewmodel.viewmodelImpl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.BooksResponseItem
import com.azamovhudstc.bookappwithretrofit2.viewmodel.ShowBookViewModel

class ShowBookViewModelImp:ShowBookViewModel,ViewModel() {
    override val backLiveData: MutableLiveData<Unit> = MutableLiveData()
    override val showBookLiveData: MutableLiveData<BooksResponseItem> =MutableLiveData()

    override fun back() {
        backLiveData.value=Unit
    }

    override fun showBook(booksResponseItem: BooksResponseItem) {
        showBookLiveData.value=booksResponseItem
    }
}