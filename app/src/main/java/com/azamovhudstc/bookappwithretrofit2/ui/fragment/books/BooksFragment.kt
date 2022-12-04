package com.azamovhudstc.bookappwithretrofit2.ui.fragment.books

import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.azamovhudstc.bookappwithretrofit2.R
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.BooksResponse
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.BooksResponseItem
import com.azamovhudstc.bookappwithretrofit2.ui.adapter.BooksAdapter
import com.azamovhudstc.bookappwithretrofit2.utils.myAddTextChangedListener
import com.azamovhudstc.bookappwithretrofit2.utils.showToast
import com.azamovhudstc.bookappwithretrofit2.viewmodel.BooksScreenViewModel
import com.azamovhudstc.bookappwithretrofit2.viewmodel.viewmodelImpl.BookScreenVewModelImp
import kotlinx.android.synthetic.main.fragment_add_book.view.*
import kotlinx.android.synthetic.main.fragment_books.*
import kotlinx.android.synthetic.main.fragment_books.view.*
import java.util.*
import kotlin.collections.ArrayList

class BooksFragment : Fragment(R.layout.fragment_books),
    BooksAdapter.ContactItemCallBack.SetLongClickListener {
    lateinit var root: View
    lateinit var handler: Handler
    lateinit var list: ArrayList<BooksResponseItem>
    private val viewModel: BooksScreenViewModel by viewModels<BookScreenVewModelImp>()
    private val adapter by lazy { BooksAdapter(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.deleteBookLiveData.observe(this, deleteBookObserver)
        viewModel.getBooksLiveData.observe(this, getBooksObserver)
        viewModel.progressLiveData.observe(this, progressObserver)
        viewModel.addBookLiveData.observe(this, addObserver)
        viewModel.editBookLiveData.observe(this, editBookObserver)
        viewModel.showBookLiveData.observe(this, showBookObserver)
        viewModel.noInternet.observe(this, noInternetObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        root = view
        books_rv.adapter = adapter
        viewModel.getBooks()
        buttonAdd.setOnClickListener {
            viewModel.addBook()
        }
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

    private val showBookObserver = Observer<BooksResponseItem> {
        var bundle = Bundle()
        bundle.putSerializable("data", it)
        findNavController().navigate(R.id.showBookFragment, bundle)
    }
    private val editBookObserver = Observer<BooksResponseItem> {
        var bundle = Bundle()
        bundle.putSerializable("data", it)
        findNavController().navigate(R.id.editFragment, bundle)
    }
    private val addObserver = Observer<Unit> {
        findNavController().navigate(R.id.addBookFragment)
    }
    private val progressObserver = Observer<Boolean> {
        if (it) progress.show()
        else progress.hide()

    }
    private val getBooksObserver = Observer<BooksResponse> {
        list = it
        placeHolder(root, list)
        adapter.submitList(it)

    }
    private val deleteBookObserver = Observer<String> {
        showToast(it, Toast.LENGTH_SHORT)
    }
    private val noInternetObserver = Observer<Boolean> {
        noInternet(root, it)
    }


    override fun onResume() {
        viewModel.getBooks()
        super.onResume()
    }

    override fun deleteClick(contact: BooksResponseItem) {
        var alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("O`chirish")
        alertDialog.setNegativeButton("Yo`q", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                dialog?.dismiss()
                Toast.makeText(requireContext(), "Bekor Qilindi", Toast.LENGTH_SHORT).show()
            }
        })
        alertDialog.setMessage("Haqiqatdan ham kitobni o`chirasizmi ?")
        alertDialog.setPositiveButton("O`chirish", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                viewModel.deleteBook(contact)
                viewModel.getBooks()


            }

        })
        alertDialog.show()
    }

    private fun placeHolder(view: View, list: ArrayList<BooksResponseItem>) {
        if (list.size == 0) {
            view.books_rv.visibility = View.GONE
            view.placeHolder.visibility = View.VISIBLE
        } else {
            view.books_rv.visibility = View.VISIBLE
            view.placeHolder.visibility = View.GONE

        }
    }

    private fun noInternet(view: View, boolean: Boolean) {
        if (boolean) {
            view.books_rv.visibility = View.GONE
            view.noInternet.visibility = View.VISIBLE
        } else {
            view.books_rv.visibility = View.VISIBLE
            view.noInternet.visibility = View.GONE

        }

    }

    override fun showClick(contact: BooksResponseItem) {
        viewModel.showBook(contact)
    }

    override fun editItemClick(contact: BooksResponseItem) {
        viewModel.editBook(contact)
    }

    override fun likedClick(contact: BooksResponseItem) {
        viewModel.likeBook(contact)
    }

    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist = ArrayList<BooksResponseItem>()

        // running a for loop to compare elements.
        for (item in list) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.title.toLowerCase().contains(text.lowercase(Locale.getDefault()))) {
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

}