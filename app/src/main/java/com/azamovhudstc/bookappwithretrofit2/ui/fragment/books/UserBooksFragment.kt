package com.azamovhudstc.bookappwithretrofit2.ui.fragment.books

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.azamovhudstc.bookappwithretrofit2.R
import com.azamovhudstc.bookappwithretrofit2.retrofit2.request.GetUserBooksResponse
import com.azamovhudstc.bookappwithretrofit2.retrofit2.request.GetUserBooksResponseItem
import com.azamovhudstc.bookappwithretrofit2.retrofit2.request.RateBookRequest
import com.azamovhudstc.bookappwithretrofit2.ui.adapter.UserBooksAdapter
import com.azamovhudstc.bookappwithretrofit2.utils.myAddTextChangedListener
import com.azamovhudstc.bookappwithretrofit2.utils.showToast
import com.azamovhudstc.bookappwithretrofit2.viewmodel.SocialUserBooksScreenViewModel
import com.azamovhudstc.bookappwithretrofit2.viewmodel.viewmodelImpl.SocialUserBooksScreenViewModelImp
import kotlinx.android.synthetic.main.fragment_social_books.*
import kotlinx.android.synthetic.main.fragment_user_books.*
import kotlinx.android.synthetic.main.fragment_user_books.search_edit_text
import kotlinx.android.synthetic.main.fragment_user_books.view.*
import kotlinx.android.synthetic.main.fragment_user_books.view.placeHolder
import java.util.*
import kotlin.collections.ArrayList

class UserBooksFragment : Fragment(R.layout.fragment_user_books),
    UserBooksAdapter.ContactItemCallBack.SetLongClickListener {
    private val viewModel: SocialUserBooksScreenViewModel by viewModels<SocialUserBooksScreenViewModelImp>()
    private val adapter by lazy { UserBooksAdapter(this) }
    var userId: Int? = null
    lateinit var handler: Handler

    var userList: GetUserBooksResponse? = null
    lateinit var root: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getAllBooksLiveData.observe(this) {
            userList = it
            adapter.submitList(it)
            placeHolder(root, it)
        }
        viewModel.progressLiveData.observe(this) {
        }
        viewModel.noInternet.observe(this) {
            noInternet(root, it)
        }
        viewModel.isFavouriteLiveData.observe(this) {
            showToast(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userId = arguments?.getInt("userId")
        viewModel.getAllBooks(userId!!)
        books_rv_social.adapter = adapter
        root = view

        handler = Handler(Looper.getMainLooper())
        search_edit_text.myAddTextChangedListener {
            val query = it.toString().lowercase()
            handler.postDelayed({
                query?.let {
                    filter(query)
                }
            }, 1000)

        }

    }

    override fun liked(data: GetUserBooksResponseItem, type: String) {
//
        if (type == "like") {
            viewModel.bookRate(RateBookRequest(data.id, true), userId!!)
            viewModel.getAllBooks(userId!!)
        } else {
            viewModel.bookRate(RateBookRequest(data.id, false), userId!!)
            viewModel.getAllBooks(userId!!)

        }

    }

    fun noInternet(view: View, boolean: Boolean) {
        if (boolean) {
            view.books_rv_social.visibility = View.GONE
            view.noInternet.visibility = View.VISIBLE
        } else {
            view.books_rv_social.visibility = View.VISIBLE
            view.noInternet.visibility = View.GONE

        }

    }

    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist = ArrayList<GetUserBooksResponseItem>()

        // running a for loop to compare elements.
        for (item in userList!!) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.title.toLowerCase()
                    .contains(text.lowercase(Locale.getDefault()))
            ) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            placeHolder(root,filteredlist)
            // displaying a toast message as no data found.
        } else {

            // at last we are passing that filtered
            // list to our adapter class.
            adapter.submitList(filteredlist)
            placeHolder(root,filteredlist)

        }
    }

    override fun show(data: GetUserBooksResponseItem) {
        var bundle = Bundle()
        bundle.putSerializable("keys", data)
        findNavController().navigate(R.id.showUserBookFragment3, bundle)
    }

    private fun placeHolder(view: View, list: ArrayList<GetUserBooksResponseItem>) {
        if (list.size == 0) {
            view.books_rv_social.visibility = View.GONE
            view.placeHolder.visibility = View.VISIBLE
        } else {
            view.books_rv_social.visibility = View.VISIBLE
            view.placeHolder.visibility = View.GONE

        }
    }

}