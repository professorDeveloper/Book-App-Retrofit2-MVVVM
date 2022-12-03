package com.azamovhudstc.bookappwithretrofit2.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.azamovhudstc.bookappwithretrofit2.R
import com.azamovhudstc.bookappwithretrofit2.retrofit2.request.AuthRequest
import com.azamovhudstc.bookappwithretrofit2.utils.amount
import com.azamovhudstc.bookappwithretrofit2.utils.myAddTextChangedListener
import com.azamovhudstc.bookappwithretrofit2.utils.showToast
import com.azamovhudstc.bookappwithretrofit2.utils.state
import com.azamovhudstc.bookappwithretrofit2.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.buttonRegister
import kotlinx.android.synthetic.main.fragment_login.phone
import kotlinx.android.synthetic.main.fragment_login.progress
import kotlinx.android.synthetic.main.fragment_register.*

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel by viewModels<LoginViewModel>()
    private var boolPassword = false
    private var boolPhone = false

    lateinit var bundle: Bundle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.openRegisterScreenLiveData.observe(this, openRegisterScreenObserver)
        viewModel.openVerifyScreen.observe(this, openVerifyScreenObserver)
        viewModel.notConnectionLiveData.observe(this, notConnectionObserver)
        viewModel.errorLiveData.observe(this, errorLiveData)
        bundle = Bundle()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonRegister.setOnClickListener { viewModel.openRegisterScreen() }

        passwordLogin.myAddTextChangedListener {
            boolPassword = it.length > 3
            check()
        }

        buttonLogin.setOnClickListener {
            if (phone.unMaskedText.toString().isEmpty()) {
                showToast("Telefonni kiriting")
                return@setOnClickListener
            } else {
                viewModel.login(
                    passwordLogin.text.toString(),
                    "+998" + phone.unMaskedText.toString()
                )
                bundle.putString("login", "login")
                bundle.putSerializable(
                    "data",
                    AuthRequest.LoginRequest(
                        passwordLogin.text.toString(), "+998${phone.unMaskedText.toString()}"
                    )
                )
            }
        }
        viewModel.changeButtonStatusLiveData.observe(viewLifecycleOwner, changeButtonStatusObserver)
        viewModel.progressLiveData.observe(viewLifecycleOwner, progressObserver)
    }

    private fun check() {
        buttonLogin.isEnabled = boolPassword
    }

    private val openRegisterScreenObserver =
        Observer<Unit> { findNavController().navigate(R.id.registerFragment) }
    private val openVerifyScreenObserver =
        Observer<Unit> {
            findNavController().navigate(
                R.id.veriyFragment,
                bundle,
                NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build()
            )
        }
    private val errorLiveData = Observer<String> {
        showToast(it)
    }
    private val notConnectionObserver = Observer<Unit> { showToast("Not connection!") }
    private val changeButtonStatusObserver = Observer<Boolean> { buttonLogin.isEnabled = it }
    private val progressObserver = Observer<Boolean> { progress.state(it) }
}