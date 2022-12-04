package com.azamovhudstc.bookappwithretrofit2.viewmodel

import androidx.lifecycle.MutableLiveData
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.BooksResponse
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.BooksResponseItem

interface FavouritesScreenViewModel {
    val progressLiveData: MutableLiveData<Boolean>
    val noInternet:MutableLiveData<Boolean>
    val isFavouriteLiveData:MutableLiveData<String>
    val getFavouriteBooksLiveData: MutableLiveData<BooksResponse>
    fun likeBook(booksResponseItem: BooksResponseItem)
    fun getFavouriteBooks()

}