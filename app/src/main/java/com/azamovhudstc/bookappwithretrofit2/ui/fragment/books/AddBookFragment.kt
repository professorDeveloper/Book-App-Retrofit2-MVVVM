package com.azamovhudstc.bookappwithretrofit2.ui.fragment.books

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.azamovhudstc.bookappwithretrofit2.R
import com.azamovhudstc.bookappwithretrofit2.retrofit2.request.AddBookRequest
import com.azamovhudstc.bookappwithretrofit2.utils.showToast
import com.azamovhudstc.bookappwithretrofit2.viewmodel.AddBookViewModel
import com.azamovhudstc.bookappwithretrofit2.viewmodel.viewmodelImpl.AddBookViewModelImp
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_add_book.*


class AddBookFragment : Fragment(R.layout.fragment_add_book) {
    private val viewModel: AddBookViewModel by viewModels<AddBookViewModelImp>()
    private var errorMessage: String? = null
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.setStatusBarColor(ContextCompat.getColor(requireActivity(), R.color.color_blue))

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
        toolbar2_.setNavigationOnClickListener {
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