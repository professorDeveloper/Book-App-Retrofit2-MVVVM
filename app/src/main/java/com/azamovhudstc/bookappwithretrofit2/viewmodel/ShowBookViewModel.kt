package com.azamovhudstc.bookappwithretrofit2.viewmodel

import androidx.lifecycle.MutableLiveData
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.BooksResponseItem

interface ShowBookViewModel {
    val backLiveData:MutableLiveData<Unit>
    val showBookLiveData:MutableLiveData<BooksResponseItem>
    fun back()
    fun showBook(booksResponseItem: BooksResponseItem)
}