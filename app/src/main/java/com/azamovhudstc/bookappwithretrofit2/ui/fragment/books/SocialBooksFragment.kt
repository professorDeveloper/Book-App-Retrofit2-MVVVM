package com.azamovhudstc.bookappwithretrofit2.ui.fragment.books

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.azamovhudstc.bookappwithretrofit2.R
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.GetSocialUserResponse
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.GetSocialUserResponseItem
import com.azamovhudstc.bookappwithretrofit2.ui.adapter.GetSocialItemsAdapter
import com.azamovhudstc.bookappwithretrofit2.utils.myAddTextChangedListener
import com.azamovhudstc.bookappwithretrofit2.viewmodel.SocialBooksScreenViewModel
import com.azamovhudstc.bookappwithretrofit2.viewmodel.viewmodelImpl.SocialBookScreenViewModelImp
import kotlinx.android.synthetic.main.fragment_books.*
import kotlinx.android.synthetic.main.fragment_social_books.*
import kotlinx.android.synthetic.main.fragment_social_books.search_edit_text
import kotlinx.android.synthetic.main.fragment_social_books.view.*

import java.util.*


class SocialBooksFragment : Fragment(R.layout.fragment_social_books),

    GetSocialItemsAdapter.ContactItemCallBack.SetLongClickListener {
    lateinit var root: View
    lateinit var handler: Handler

    private val viewModel: SocialBooksScreenViewModel by viewModels<SocialBookScreenViewModelImp>()
    private val adapter by lazy { GetSocialItemsAdapter(this) }
    var list: ArrayList<GetSocialUserResponseItem>? = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getSocialUsersLiveData.observe(this, getUsersObserver)
        viewModel.progressLiveData.observe(this) {
            if (it) progress_social.show() else progress_social.hide()
        }
        viewModel.getUsers()


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_user.adapter = adapter
        root = view
        viewModel.getUsers()
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

    private val getUsersObserver = Observer<GetSocialUserResponse> {
        adapter.submitList(it)
        placeHolder(root, it)

        list = it
    }

    override fun userClick(contact: GetSocialUserResponseItem) {
        var bundle = Bundle()
        bundle.putInt("userId", contact.id)
        findNavController().navigate(R.id.userBooksFragment, bundle)
    }

    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist = ArrayList<GetSocialUserResponseItem>()

        // running a for loop to compare elements.
        for (item in list!!) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.firstName.toLowerCase()
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

    private fun placeHolder(view: View, list: ArrayList<GetSocialUserResponseItem>) {
        if (list.size == 0) {
            view.rv_user.visibility = View.GONE
            view.placeHolder.visibility = View.VISIBLE
        } else {
            view.rv_user.visibility = View.VISIBLE
            view.placeHolder.visibility = View.GONE

        }
    }

}