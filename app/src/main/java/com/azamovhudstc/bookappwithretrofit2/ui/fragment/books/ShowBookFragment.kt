package com.azamovhudstc.bookappwithretrofit2.ui.fragment.books

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.azamovhudstc.bookappwithretrofit2.R
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.BooksResponseItem
import com.azamovhudstc.bookappwithretrofit2.utils.showToast
import com.azamovhudstc.bookappwithretrofit2.viewmodel.HomeScreenViewModel
import com.azamovhudstc.bookappwithretrofit2.viewmodel.ShowBookViewModel
import com.azamovhudstc.bookappwithretrofit2.viewmodel.viewmodelImpl.HomeScreenViewModelImp
import com.azamovhudstc.bookappwithretrofit2.viewmodel.viewmodelImpl.ShowBookViewModelImp
import kotlinx.android.synthetic.main.fragment_show_book.*

class ShowBookFragment : Fragment(R.layout.fragment_show_book) {
    lateinit var data: BooksResponseItem
    private val viewModel: ShowBookViewModel by viewModels<ShowBookViewModelImp>()
    override fun onCreate(savedInstanceState: Bundle?) {
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        data = arguments?.getSerializable("data") as BooksResponseItem

        super.onCreate(savedInstanceState)
        activity?.getWindow()?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        viewModel.showBookLiveData.observe(this, showBookObserver)
        viewModel.backLiveData.observe(this) { findNavController().popBackStack() }
    }

    private val showBookObserver = Observer<BooksResponseItem> {
        if (data.fav) {
            favLiked.setImageResource(R.drawable.ic_baseline_favorite_24);
        } else {
            favLiked.setImageResource(R.drawable.ic_baseline_favorite_border_24);

        }
        show_title.title = it.title
        show_author.text = "Muallif: ${it.author}"
        show_des.text = it.description
        show_page.text = "Hajmi: ${it.pageCount} Bet"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        data = arguments?.getSerializable("data") as BooksResponseItem
        viewModel.showBook(data)

        favLiked.setOnClickListener {
            if (data.fav) {
                favLiked.setImageResource(R.drawable.ic_baseline_favorite_24);
                data.fav = false;
                viewModel.likeBook(data)
                viewModel.showBook(data)
                return@setOnClickListener
            } else {
                data.fav = true;
                favLiked.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                viewModel.likeBook(data)

                viewModel.showBook(data)
                return@setOnClickListener

            }


        }
    }

}