package com.azamovhudstc.bookappwithretrofit2.viewmodel

import androidx.lifecycle.MutableLiveData
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.BooksResponse
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.BooksResponseItem

interface BooksScreenViewModel {
    val getBooksLiveData: MutableLiveData<BooksResponse>
    val addBookLiveData: MutableLiveData<Unit>
    val isFavouriteLiveData:MutableLiveData<String>
    var editBookLiveData:MutableLiveData<BooksResponseItem>
    val deleteBookLiveData: MutableLiveData<String>
    val showBookLiveData:MutableLiveData<BooksResponseItem>
    var progressLiveData: MutableLiveData<Boolean>
    var noInternet: MutableLiveData<Boolean>

    fun getBooks()
    fun deleteBook(booksResponseItem: BooksResponseItem)
    fun showBook(booksResponse: BooksResponseItem)
    fun addBook()
    fun likeBook(booksResponseItem: BooksResponseItem)
    fun editBook(contact: BooksResponseItem)
}
