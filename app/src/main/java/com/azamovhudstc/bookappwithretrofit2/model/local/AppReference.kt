package com.azamovhudstc.bookappwithretrofit2.model.local

import android.content.Context
import android.content.SharedPreferences

class AppReference private constructor() {
    companion object {
        private lateinit var pref: SharedPreferences
        private lateinit var instance: AppReference

        fun init(context: Context) {
            pref = context.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE)
            instance = AppReference()
        }

        fun getInstance(): AppReference = instance
    }

    var startScreen: String
        set(value) = pref.edit().putString("START_SCREEN", value).apply()
        get() = pref.getString("START_SCREEN", "INTRO")!!

    var verifyToken: String
        set(value) = pref.edit().putString("TOKEN", value).apply()
        get() = pref.getString("TOKEN", "")!!
    var token: String
        set(value) = pref.edit().putString("hometoken", value).apply()
        get() = pref.getString("hometoken", "")!!
    var name: String
        set(value) = pref.edit().putString("name", value).apply()
        get() = pref.getString("name", "")!!

    var password: String
        set(value) = pref.edit().putString("password", value).apply()
        get() = pref.getString("password", "")!!
}