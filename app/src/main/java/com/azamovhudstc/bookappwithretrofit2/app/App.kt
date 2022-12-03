package com.azamovhudstc.bookappwithretrofit2.app

import android.app.Application
import com.azamovhudstc.bookappwithretrofit2.model.local.AppReference

class App:Application() {
    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        AppReference.init(this)
    }
}