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
import com.azamovhudstc.bookappwithretrofit2.retrofit2.request.EditBookRequest
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.BooksResponseItem
import com.azamovhudstc.bookappwithretrofit2.utils.showToast
import com.azamovhudstc.bookappwithretrofit2.viewmodel.BooksScreenViewModel
import com.azamovhudstc.bookappwithretrofit2.viewmodel.EditBookScreenViewModel
import com.azamovhudstc.bookappwithretrofit2.viewmodel.viewmodelImpl.BookScreenVewModelImp
import com.azamovhudstc.bookappwithretrofit2.viewmodel.viewmodelImpl.EditBookScreenViewModelImp
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_add_book.*
import kotlinx.android.synthetic.main.fragment_books.*
import kotlinx.android.synthetic.main.fragment_edit.*


class EditFragment : Fragment(R.layout.fragment_edit) {
    private val viewModel: EditBookScreenViewModel by viewModels<EditBookScreenViewModelImp>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.progressLiveData.observe(this, progressObserver)
        viewModel.errorLiveData.observe(this, errorObserver)
        viewModel.successBackLiveData.observe(this, successBackObserver)
    }

    private val successBackObserver = Observer<Unit> {
        findNavController().popBackStack()
        Snackbar.make(requireView(), "Muffaqyatli tahrirlandi !", Toast.LENGTH_SHORT).show()
    }
    private val errorObserver = Observer<String> {
        showToast(it, Toast.LENGTH_SHORT)
    }
    private val progressObserver = Observer<Boolean> {
        if (it) editBookProgress.show()
        else editBookProgress.hide()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val serializable = arguments?.getSerializable("data") as BooksResponseItem
        editBookDes.setText(serializable.description)
        editBookAuthor.setText(serializable.author)
        editBookName.setText(serializable.title)
        editBookPage.setText(serializable.pageCount.toString())
        edited.setOnClickListener {
            if (editBookAuthor.text.trim().toString().isEmpty() || editBookDes.text.toString()
                    .trim()
                    .isEmpty() || editBookName.text.trim().toString().isEmpty() ||
                editBookPage.text.toString().trim().isEmpty()
            ) {
                Toast.makeText(requireContext(), "Maydonlar bo`sh", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.editBook(
                    EditBookRequest(
                        id = serializable.id,
                        author = editBookAuthor.text.toString(),
                        description = editBookDes.text.toString(),
                        pageCount = editBookPage.text.toString().toInt(),
                        title = editBookName.text.toString()
                    )

                )
            }
        }
    }
}