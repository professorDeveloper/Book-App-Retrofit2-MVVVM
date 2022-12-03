package com.azamovhudstc.bookappwithretrofit2.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.azamovhudstc.bookappwithretrofit2.R
import com.azamovhudstc.bookappwithretrofit2.retrofit2.request.AuthRequest
import com.azamovhudstc.bookappwithretrofit2.utils.myAddTextChangedListener
import com.azamovhudstc.bookappwithretrofit2.utils.showToast
import com.azamovhudstc.bookappwithretrofit2.utils.state
import com.azamovhudstc.bookappwithretrofit2.viewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : Fragment(R.layout.fragment_register) {
    private var boolPassword = false
    private var boolName = false
    private var boolLastName = false
    lateinit var bundle: Bundle

    private val registerViewModel by viewModels<RegisterViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerViewModel.openVerifyScreenLiveData.observe(this, openVerifyObserver)
        registerViewModel.notConnectionLiveData.observe(this, notConnectionObserver)
        registerViewModel.errorLiveData.observe(this, errorLiveData)

        bundle = Bundle()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        registerViewModel.progressLiveData.observe(viewLifecycleOwner, progressObserver)

        inputFirstName.myAddTextChangedListener {
            boolName = it.length > 3
            check()

        }
        lastName.myAddTextChangedListener {
            boolLastName = it.length > 3
            check()
        }
        phone.myAddTextChangedListener {
            boolPassword =
                it.length > 3
            check()

        }

        inputConfirmPassword.myAddTextChangedListener {
            boolPassword =
                it.length > 3

        }

        buttonRegister.setOnClickListener {
            if (inputFirstName.text.toString().isEmpty() || lastName.text?.trim().toString()
                    .isEmpty() || lastName.text?.trim().toString().isEmpty()
            ) {
                showToast("Maydonlarni To`ldiring")
                boolLastName = false
            } else if (phone.text.toString() != inputConfirmPassword.text.toString()) {
                showToast("Parollar Mos Emas")
                boolPassword = false

            } else {
                registerViewModel.register(
                    inputFirstName.text.toString(),
                    phone.text.toString(),
                    "+998" + inputPhone.unMaskedText.toString(),
                    lastName.text.toString()
                )
                bundle.putString("login", "register")
                bundle.putSerializable(
                    "data",
                    AuthRequest.RegisterRequest(
                        inputFirstName.text.toString(),
                        lastName.text.toString(),
                        phone.text.toString(),
                        "+998${inputPhone.unMaskedText.toString()}"
                    )
                )

            }
        }
        registerViewModel.changeButtonStatusLiveData.observe(
            viewLifecycleOwner,
            changeButtonStatusObserver
        )
        registerViewModel.progressLiveData.observe(viewLifecycleOwner, progressObserver)


    }

    private fun check() {
        buttonRegister.isEnabled = boolName && boolPassword && boolLastName

    }

    private val changeButtonStatusObserver = Observer<Boolean> { buttonRegister.isEnabled = it }
    private val progressObserver = Observer<Boolean> { progress.state(it) }
    private val errorLiveData = Observer<String> {
        showToast(it)
    }
    private val openVerifyObserver = Observer<Unit> {
        findNavController().navigate(
            R.id.veriyFragment,
            bundle,
            NavOptions.Builder().setPopUpTo(R.id.registerFragment, true).build()
        )
    }
    private val notConnectionObserver = Observer<Unit> {
        showToast("No internet Connected")
    }
}