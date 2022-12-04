package com.azamovhudstc.bookappwithretrofit2.viewmodel.viewmodelImpl

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.azamovhudstc.bookappwithretrofit2.model.local.AppReference
import com.azamovhudstc.bookappwithretrofit2.retrofit2.cilent.ApiClient
import com.azamovhudstc.bookappwithretrofit2.retrofit2.request.FavouriteBookRequest
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.BooksResponse
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.BooksResponseItem
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.ErrorResponse
import com.azamovhudstc.bookappwithretrofit2.retrofit2.service.BookService
import com.azamovhudstc.bookappwithretrofit2.utils.isConnected
import com.azamovhudstc.bookappwithretrofit2.viewmodel.FavouritesScreenViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavouritesScreenViewModelImp : FavouritesScreenViewModel, ViewModel() {
    override val progressLiveData: MutableLiveData<Boolean> = MutableLiveData()
    override val noInternet: MutableLiveData<Boolean> = MutableLiveData()
    override val isFavouriteLiveData: MutableLiveData<String> = MutableLiveData()
    override val getFavouriteBooksLiveData: MutableLiveData<BooksResponse> = MutableLiveData()
    override fun likeBook(booksResponseItem: BooksResponseItem) {
        val token = AppReference.getInstance().token
        if (!isConnected()){
            noInternet.value=true
            return
        }
        noInternet.value=false
        progressLiveData.value=true
        bookApi.addFavouriteBook("Bearer $token", FavouriteBookRequest(booksResponseItem.id))
            .enqueue(object : Callback<ErrorResponse> {
                override fun onResponse(
                    call: Call<ErrorResponse>,
                    response: Response<ErrorResponse>
                ) {
                    if (response.isSuccessful){
                        progressLiveData.value=false
                        isFavouriteLiveData.value=response.body()?.message
                        getFavouriteBooks()

                    }
                    getFavouriteBooks()
                }

                override fun onFailure(call: Call<ErrorResponse>, t: Throwable) {
                    Log.d("!@#", "onFailure: ${t.message}")

                }


            })
    }

    private val bookApi = ApiClient.retrofit.create(BookService::class.java)

    override fun getFavouriteBooks() {
        if (!isConnected()){
            noInternet.value=true
            return
        }
        noInternet.value=false
        progressLiveData.value = true
        val token = AppReference.getInstance().token
        var arrayList = BooksResponse()
        bookApi.getAllBooks("Bearer $token").enqueue(object : Callback<BooksResponse> {
            override fun onResponse(call: Call<BooksResponse>, response: Response<BooksResponse>) {
                if (response.isSuccessful) {
                    for (i in 0 until response.body()?.size!!) {
                        if (response.body()!![i].fav) {
                            arrayList.add(response.body()!![i])
                        }
                    }
                    getFavouriteBooksLiveData.value = arrayList
                    progressLiveData.value = false
                }
            }

            override fun onFailure(call: Call<BooksResponse>, t: Throwable) {
                Log.d("!@#", "onFailure: ${t.message}")

            }

        })
    }
}