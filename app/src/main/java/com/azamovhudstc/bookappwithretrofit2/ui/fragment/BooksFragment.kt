package com.azamovhudstc.bookappwithretrofit2.ui.fragment

import android.content.DialogInterface
import android.os.Bundle
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
import com.azamovhudstc.bookappwithretrofit2.utils.showToast
import com.azamovhudstc.bookappwithretrofit2.viewmodel.BooksScreenViewModel
import com.azamovhudstc.bookappwithretrofit2.viewmodel.viewmodelImpl.BookScreenVewModelImp
import kotlinx.android.synthetic.main.fragment_books.*
import kotlinx.android.synthetic.main.fragment_books.view.*

class BooksFragment : Fragment(R.layout.fragment_books),
    BooksAdapter.ContactItemCallBack.SetLongClickListener {
    lateinit var root: View
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
        viewModel.showBookLiveData.observe(this,showBookObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        root = view
        books_rv.adapter = adapter
        viewModel.getBooks()
        buttonAdd.setOnClickListener {
            viewModel.addBook()
        }
    }

    private val showBookObserver= Observer<BooksResponseItem> {
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

    override fun showClick(contact: BooksResponseItem) {
        viewModel.showBook(contact)
    }

    override fun editItemClick(contact: BooksResponseItem) {
        viewModel.editBook(contact)
    }
}