package com.azamovhudstc.bookappwithretrofit2.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.azamovhudstc.bookappwithretrofit2.R
import com.azamovhudstc.bookappwithretrofit2.ui.adapter.OnboardingViewPagerAdapter2
import com.azamovhudstc.bookappwithretrofit2.viewmodel.viewmodelImpl.IntroViewModelImpl
import kotlinx.android.synthetic.main.fragment_intro.*
import kotlinx.android.synthetic.main.fragment_intro.view.*


class IntroFragment : Fragment(R.layout.fragment_intro) {
    private val introViewModel by viewModels<IntroViewModelImpl>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        introViewModel.clickLiveData.observe(viewLifecycleOwner, clickObserver)
        initViewPager(view)

        btn_create_account.setOnClickListener {
            introViewModel.click()
        }

    }

    private fun initViewPager(view: View) {
        var adapter = OnboardingViewPagerAdapter2(requireActivity(), requireContext())
        view.viewPager.adapter = adapter
        pageIndicator.attachTo(viewPager)

    }

    private val clickObserver = Observer<Unit> {
        findNavController().navigate(
            R.id.loginFragment,
            null,
            NavOptions.Builder().setPopUpTo(R.id.introFragment, true).build()
        )
    }

}