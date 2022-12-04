package com.azamovhudstc.bookappwithretrofit2.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.azamovhudstc.bookappwithretrofit2.ui.fragment.books.BooksFragment
import com.azamovhudstc.bookappwithretrofit2.ui.fragment.books.FavouritesFragment

class TabAdapter(frm: FragmentManager) : FragmentPagerAdapter(
    frm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                BooksFragment()
            }

            else -> {
                FavouritesFragment()
            }
        }
    }
}