package com.azamovhudstc.bookappwithretrofit2.ui.fragment.books

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.azamovhudstc.bookappwithretrofit2.R
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.BooksResponse
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.BooksResponseItem
import com.azamovhudstc.bookappwithretrofit2.ui.adapter.FavouriteAdapter
import com.azamovhudstc.bookappwithretrofit2.viewmodel.FavouritesScreenViewModel
import com.azamovhudstc.bookappwithretrofit2.viewmodel.viewmodelImpl.FavouritesScreenViewModelImp
import kotlinx.android.synthetic.main.fragment_favourites.*
import kotlinx.android.synthetic.main.fragment_favourites.view.*

class FavouritesFragment : Fragment(R.layout.fragment_favourites),
    FavouriteAdapter.ContactItemCallBack.SetLongClickListener {
    private val viewModel: FavouritesScreenViewModel by viewModels<FavouritesScreenViewModelImp>()
    private val adapter by lazy { FavouriteAdapter(this) }
    lateinit var root: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getFavouriteBooks()
        viewModel.progressLiveData.observe(this) {
            if (it) progress_Fav.show()
            else progress_Fav.hide()
        }
        viewModel.getFavouriteBooksLiveData.observe(this,getFavObserver )
        viewModel.noInternet.observe(this, noInternetObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        root = view
        books_rv_fav.adapter = adapter
        viewModel.getFavouriteBooks()
    }

    override fun showClick(contact: BooksResponseItem) {
        var bundle = Bundle()
        bundle.putSerializable("data", contact)
        findNavController().navigate(R.id.showBookFragment, bundle)

    }


    override fun onResume() {
        viewModel.getFavouriteBooks()
        super.onResume()

    }

    private fun placeHolder(view: View, list: ArrayList<BooksResponseItem>) {
        if (list.size == 0) {
            view.books_rv_fav.visibility = View.GONE
            view.placeHolder.visibility = View.VISIBLE
        } else {
            view.books_rv_fav.visibility = View.VISIBLE
            view.placeHolder.visibility = View.GONE

        }
    }

    private fun noInternet(view: View, boolean: Boolean) {
        if (boolean) {
            view.books_rv_fav.visibility = View.GONE
            view.noInternet.visibility = View.VISIBLE
        } else {
            view.books_rv_fav.visibility = View.VISIBLE
            view.noInternet.visibility = View.GONE

        }

    }

    private val noInternetObserver = Observer<Boolean> {
        noInternet(root, it)
    }
    private val getFavObserver = Observer<BooksResponse> {
        adapter.submitList(it)
        placeHolder(root, it)

    }

}