package com.azamovhudstc.bookappwithretrofit2.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.azamovhudstc.bookappwithretrofit2.R
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.BooksResponseItem
import com.azamovhudstc.bookappwithretrofit2.viewmodel.HomeScreenViewModel
import com.azamovhudstc.bookappwithretrofit2.viewmodel.ShowBookViewModel
import com.azamovhudstc.bookappwithretrofit2.viewmodel.viewmodelImpl.HomeScreenViewModelImp
import com.azamovhudstc.bookappwithretrofit2.viewmodel.viewmodelImpl.ShowBookViewModelImp
import kotlinx.android.synthetic.main.fragment_show_book.*

class ShowBookFragment : Fragment(R.layout.fragment_show_book) {
    private val viewModel: ShowBookViewModel by viewModels<ShowBookViewModelImp>()
    override fun onCreate(savedInstanceState: Bundle?) {
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        super.onCreate(savedInstanceState)
        activity?.getWindow()?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        viewModel.showBookLiveData.observe(this, showBookObserver)
        viewModel.backLiveData.observe(this) { findNavController().popBackStack() }
    }

    private val showBookObserver = Observer<BooksResponseItem> {
        show_title.text = it.title
        show_author.text = "Muallif: ${it.author}"
        show_des.text = it.description
        show_page.text = "Hajmi: ${it.pageCount}"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var data = arguments?.getSerializable("data") as BooksResponseItem
        back_show.setOnClickListener {
            viewModel.back()
        }
        viewModel.showBook(data)
    }

}