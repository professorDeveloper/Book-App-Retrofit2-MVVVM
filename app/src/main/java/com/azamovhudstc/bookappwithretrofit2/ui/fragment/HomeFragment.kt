package com.azamovhudstc.bookappwithretrofit2.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.azamovhudstc.bookappwithretrofit2.R
import com.azamovhudstc.bookappwithretrofit2.model.local.AppReference
import com.azamovhudstc.bookappwithretrofit2.ui.adapter.ViewPager2Adapter
import com.azamovhudstc.bookappwithretrofit2.viewmodel.HomeScreenViewModel
import com.azamovhudstc.bookappwithretrofit2.viewmodel.viewmodelImpl.HomeScreenViewModelImp
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val viewModel: HomeScreenViewModel by viewModels<HomeScreenViewModelImp>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.viewPagerLiveData.observe(this,viewPagerObserver)
    }

    private val viewPagerObserver=Observer<Unit>{
        println("access token ${AppReference.getInstance().token}")
        var adapter = ViewPager2Adapter(requireActivity())
        containerv.adapter = adapter
        bottom_menu.setItemSelected(R.id.home)
        bottom_menu.setOnItemSelectedListener {
            when (it) {
                R.id.home -> {
                    containerv.currentItem = 0
                }
                R.id.favourite -> {
                    containerv.currentItem = 1
                }
            }
        }
        containerv.setOnTouchListener(null);
        containerv.isUserInputEnabled = false
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewPagerSetup()
    }

    override fun onResume() {
        viewModel.viewPagerSetup()
        super.onResume()

    }
}