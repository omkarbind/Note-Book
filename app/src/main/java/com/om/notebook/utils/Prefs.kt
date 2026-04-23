package com.om.notebook.utils

import android.content.Context

object Prefs {

    fun saveUser(context: Context, email: String, password: String, remember: Boolean) {
        val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        prefs.edit()
            .putString("email", email)
            .putString("password", password)
            .putBoolean("remember", remember)
            .apply()
    }

    fun getEmail(context: Context): String {
        return context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            .getString("email", "") ?: ""
    }

    fun getPassword(context: Context): String {
        return context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            .getString("password", "") ?: ""
    }

    fun isRemember(context: Context): Boolean {
        return context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            .getBoolean("remember", false)
    }

    fun clear(context: Context) {
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            .edit().clear().apply()
    }
}