package com.azamovhudstc.bookappwithretrofit2.utils

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.ContentLoadingProgressBar
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.pdrozz.view.PinView
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText

fun <T> T.myApply(block: T.() -> Unit) {
    block(this)
}

fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message, duration).show()
}

fun AppCompatActivity.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun AppCompatEditText.myAddTextChangedListener(block: (String) -> Unit) {
    this.addTextChangedListener {
        it?.let {
            block.invoke(it.toString())
        }
    }
}

fun ShowHidePasswordEditText.myAddTextChangedListener(block: (String) -> Unit) {
    this.addTextChangedListener {
        it?.let {
            block.invoke(it.toString())
        }
    }
}


fun ContentLoadingProgressBar.state(bool: Boolean) {
    if (bool) this.show()
    else this.hide()
}


fun AppCompatEditText.amount(): String = this.text.toString()