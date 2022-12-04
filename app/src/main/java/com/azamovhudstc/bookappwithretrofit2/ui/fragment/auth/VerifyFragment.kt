package com.azamovhudstc.bookappwithretrofit2.ui.fragment.auth

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.azamovhudstc.bookappwithretrofit2.R
import com.azamovhudstc.bookappwithretrofit2.model.local.AppReference
import com.azamovhudstc.bookappwithretrofit2.retrofit2.request.AuthRequest
import com.azamovhudstc.bookappwithretrofit2.utils.showToast
import com.azamovhudstc.bookappwithretrofit2.utils.state
import com.azamovhudstc.bookappwithretrofit2.viewmodel.viewmodelImpl.VerifyViewModel
import kotlinx.android.synthetic.main.fragment_veriy.*
import java.io.Serializable
import java.text.DecimalFormat


class VerifyFragment : Fragment(R.layout.fragment_veriy) {
    private val viewModel by viewModels<VerifyViewModel>()
    private var boolPassword = false
    private var boolPhone = false
    lateinit var timer: CountDownTimer
    lateinit var data: Serializable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.openHomeScreen.observe(this, openHomeObserver)
        viewModel.notConnectionLiveData.observe(this, notConnectionObserver)
        viewModel.errorLiveData.observe(this, errorLiveData)

    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val isChecker = arguments?.getString("login")
        if (isChecker == "login") {

            data = arguments?.getSerializable("data") as AuthRequest.LoginRequest

            var phone = (data as AuthRequest.LoginRequest).phone
            val phoneNumber = phone!!.substring(phone.length - 4, phone.length - 2)
            val phoneNumberTwo = phone.substring(phone.length - 2, phone.length)
            getPhones.text =
                "+998(##)-###-$phoneNumber-$phoneNumberTwo telefon raqamga Tasdiqlash Kodi Yubordik"
        } else {
            data = arguments?.getSerializable("data") as AuthRequest.RegisterRequest

            var phone = (data as AuthRequest.RegisterRequest).phone
            val phoneNumber = phone!!.substring(phone.length - 4, phone.length - 2)
            val phoneNumberTwo = phone.substring(phone.length - 2, phone.length)
            getPhones.text =
                "+998(##)-###-$phoneNumber-$phoneNumberTwo telefon raqamga Tasdiqlash Kodi Yubordik"


        }
        timer = object : CountDownTimer(60000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                var f = DecimalFormat("00");
                val hour = millisUntilFinished / 3600000 % 24
                val min = millisUntilFinished / 60000 % 60
                val sec = millisUntilFinished / 1000 % 60
                countDown.text = f.format(hour) + ":" + f.format(min) + ":" + f.format(sec)

            }

            override fun onFinish() {

                resent_code.visibility = View.VISIBLE
                countDown.text = "00:00:00"


            }
        }
        timer.start()

        resent_code.setOnClickListener {
            if (isChecker == "login") {
                viewModel.login(
                    (data as AuthRequest.LoginRequest).password,
                    (data as AuthRequest.LoginRequest).phone
                )
                timer.start()
                resent_code.visibility = View.INVISIBLE

            } else {
                viewModel.register(
                    (data as AuthRequest.RegisterRequest).firstName,
                    (data as AuthRequest.RegisterRequest).password,
                    (data as AuthRequest.RegisterRequest).phone,
                    (data as AuthRequest.RegisterRequest).lastName,
                )
                timer.start()
                resent_code.visibility = View.INVISIBLE


            }

        }





        button_send.setOnClickListener {
            if (isChecker == "login") {
                if (getOtp.text.toString().isEmpty() || getOtp.text?.length!! > 6) {
                    showToast("Iltimos kod 6 bo`lishi kerak ")
                } else {

                    viewModel.verifySign(
                        getOtp.text.toString(),
                        AppReference.getInstance().verifyToken
                    )
                    resent_code.visibility = View.INVISIBLE

                }
            } else {
                if (getOtp.text.toString().isEmpty() || getOtp.text?.length!! > 6) {
                    showToast("Iltimos kod 6 bo`lishi kerak ")
                } else {
                    viewModel.verify(AppReference.getInstance().verifyToken, getOtp.text.toString())

                }
            }
        }
        resent_code.visibility = View.GONE
        viewModel.progressLiveData.observe(viewLifecycleOwner, progressObserver)

    }


    private val openHomeObserver = Observer<Unit> {

        findNavController().navigate(
            R.id.homeFragment,
            null,
            NavOptions.Builder().setPopUpTo(R.id.veriyFragment, true).build()
        )
    }
    private val errorLiveData = Observer<String> {
        showToast(it)
    }
    private val notConnectionObserver = Observer<Unit> { showToast("Not connection!") }
    private val progressObserver = Observer<Boolean> { verfied_progress.state(it) }

    override fun onDestroyView() {
        timer.cancel()
        super.onDestroyView()

    }
}