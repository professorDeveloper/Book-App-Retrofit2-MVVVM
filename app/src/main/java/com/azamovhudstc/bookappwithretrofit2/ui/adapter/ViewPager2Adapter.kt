package com.azamovhudstc.bookappwithretrofit2.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.azamovhudstc.bookappwithretrofit2.ui.fragment.books.BooksFragment
import com.azamovhudstc.bookappwithretrofit2.ui.fragment.books.FavouritesFragment
import com.azamovhudstc.bookappwithretrofit2.ui.fragment.books.SocialBooksFragment

class ViewPager2Adapter(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{
                BooksFragment()
            }
            1 ->{
                FavouritesFragment()
            }
            else -> SocialBooksFragment()

        }
    }
}