package com.azamovhudstc.bookappwithretrofit2.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.azamovhudstc.bookappwithretrofit2.R
import com.azamovhudstc.bookappwithretrofit2.retrofit2.request.AddBookRequest
import com.azamovhudstc.bookappwithretrofit2.utils.showToast
import com.azamovhudstc.bookappwithretrofit2.viewmodel.AddBookViewModel
import com.azamovhudstc.bookappwithretrofit2.viewmodel.BooksScreenViewModel
import com.azamovhudstc.bookappwithretrofit2.viewmodel.viewmodelImpl.AddBookViewModelImp
import com.azamovhudstc.bookappwithretrofit2.viewmodel.viewmodelImpl.BookScreenVewModelImp
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_add_book.*
import kotlinx.android.synthetic.main.fragment_books.*


class AddBookFragment : Fragment(R.layout.fragment_add_book) {
    private val viewModel: AddBookViewModel by viewModels<AddBookViewModelImp>()
    private var errorMessage: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.successBackLiveData.observe(this, addBookSuccessObserver)
        viewModel.progressStatusLiveData.observe(this, progressObserver)
        viewModel.errorLiveData.observe(this, errorObserver)
    }

    private val errorObserver = Observer<String> {
        errorMessage = it
        showToast(it, Toast.LENGTH_SHORT)
    }
    private val addBookSuccessObserver = Observer<Unit> {
        findNavController().popBackStack()
        Snackbar.make(requireView(), "Muffaqyatli qo`shildi !", Toast.LENGTH_SHORT).show()
    }
    private val progressObserver = Observer<Boolean> {
        if (it) addBookProgress.show()
        else addBookProgress.hide()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        back_add?.setOnClickListener {
            findNavController().popBackStack()
        }
        addBook.setOnClickListener {
            if (addBookAuthor.text.trim().toString().isEmpty() || addBookDes.text.toString().trim()
                    .isEmpty() || addBookName.text.trim().toString().isEmpty() ||
                addBookPage.text.toString().trim().isEmpty()
            ) {
                Toast.makeText(requireContext(), "Maydonlar bo`sh", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.addBook(
                    AddBookRequest(
                        author = addBookAuthor.text.toString(),
                        description = addBookDes.text.trim().toString().trim(),
                        pageCount = addBookPage.text.toString().toInt(),
                        title = addBookName.text.toString()
                    )
                )

            }
        }
    }

}