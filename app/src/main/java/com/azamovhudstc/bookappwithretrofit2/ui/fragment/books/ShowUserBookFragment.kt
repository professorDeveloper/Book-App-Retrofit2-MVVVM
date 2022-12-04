package com.azamovhudstc.bookappwithretrofit2.ui.fragment.books

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.azamovhudstc.bookappwithretrofit2.R
import com.azamovhudstc.bookappwithretrofit2.retrofit2.request.GetUserBooksResponseItem
import com.azamovhudstc.bookappwithretrofit2.viewmodel.ShowBookViewModel
import com.azamovhudstc.bookappwithretrofit2.viewmodel.viewmodelImpl.ShowBookViewModelImp
import kotlinx.android.synthetic.main.fragment_show_book.show_author
import kotlinx.android.synthetic.main.fragment_show_book.show_des
import kotlinx.android.synthetic.main.fragment_show_book.show_page
import kotlinx.android.synthetic.main.fragment_show_book.show_title
import kotlinx.android.synthetic.main.fragment_show_user_book.*

// TODO: Rename parameter arguments, choose names that match

class ShowUserBookFragment : Fragment(R.layout.fragment_show_user_book) {




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       var data = arguments?.getSerializable("keys") as GetUserBooksResponseItem

        show_title.title = data.title
        show_author.text = "Muallif: ${data.author.toString()}"
        like_count.text ="Like Count:${data.likeCount.toString()}"
        dislike_count.text ="DisLike Count:${data.disLikeCount.toString()}"
        show_des.text = data.description
        show_page.text = "Hajmi: ${data.pageCount.toString()} Bet"
    }

}